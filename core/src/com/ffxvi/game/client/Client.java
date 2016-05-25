/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.client;

import com.ffxvi.game.screens.GameScreen;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class Client {
	
	private InetSocketAddress hostAddress;
	private ClientListener clientListener;
	private GameScreen screen;
	private byte[] sendData;
	
	/**
	 * The host's address (IP address + port number)
	 * @return the host's address (IP address + port number)
	 */
	public String getHostAddress(){
		return hostAddress.toString();
	}
	
	/**
	 * The host's IP address
	 * @return the host's IP address
	 */
	public String getHostIP(){
		return hostAddress.getAddress().toString();
	}
	
	/**
	 * The host's port number
	 * @return the host's port number
	 */
	public int getHostPort(){
		return hostAddress.getPort();
	}
	
	/**
	 * The port that this client is listening on
	 * @return the port that this client is listening on
	 */
	public int getListeningPort(){
		return clientListener.getPort();
	}
	
	/**
	 * Initialize the client. The client is listening in a thread, this is
	 * because the listening code keeps waiting until a packet is received
	 * @param hostIP the IP address that the client will connect with
	 * @param hostPort the port on the host that the client will connect with
	 * @param listenerPort the port that the client will be listening on
	 * @param screen the GameScreen that will handle the rendering
	 */
	public Client(String hostIP, int hostPort, int listenerPort, GameScreen screen){
		this.screen = screen;
		
		// Set the host to send data to
		hostAddress = new InetSocketAddress(hostIP, hostPort);
		send("CONNECTING");
		// Set the port to receive data on
		clientListener = new ClientListener(listenerPort, screen);
		Thread listenerThread = new Thread(clientListener);
		listenerThread.start();
	}
	
	/**
	 * Stop listening for new data and stop sending data to the host, use this for
	 * disconnecting or stopping the application
	 */
	public void stop(){
		send("DISCONNECTING");
		clientListener.stopListening();
	}
	
	/**
	 * Send an Object to the host
	 * @param message the Object to send
	 */
	public void send(Object message){
		DatagramSocket sendingSocket = null;
		try {
			sendingSocket = new DatagramSocket();
		} catch (SocketException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// Empty the byte array to prevent messy data
		sendData = new byte[1024];
		
		try {
			// Convert the object to bytes and put it in the byte array
			sendData = serialize(message);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// Create the DatagramPacket to send
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, hostAddress);
		
		// Send the DatagramPacket through the DatagramSocket
		try {
			sendingSocket.send(sendPacket);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// Close the socket
		sendingSocket.close();
	}
	
	/**
	 * Turns the given Object into a byte array
	 * @param obj the Object to serialize
	 * @return the serialized Object turned into a byte array
	 * @throws IOException when the OutputStream gets disrupted
	 */
	private byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
        byte[] ret = b.toByteArray();
		return ret;
    }
}
