/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class Server {
	
	private InetSocketAddress[] players;
	private ServerListener serverListener;
	private byte[] sendData;
	
	/**
	 * Return the addresses of the connected clients
	 * @return an array of the connected clients
	 */
	public InetSocketAddress[] getPlayerAddresses(){
		return players;
	}
	
	/**
	 * Return the port that the server is listening on
	 * @return the port that the server is listening on
	 */
	public int getListeningPort(){
		return serverListener.getPort();
	}
	
	/**
	 * Initialize the server. The server is listening in a thread, this is
	 * because the listening code keeps waiting until a packet is received
	 * @param listenerPort the port that the server will be listening on
	 */
	public Server(int listenerPort){
		// Initialize the players array
		players = new InetSocketAddress[16];
		// Set the port to receive data on
		serverListener = new ServerListener(listenerPort);
		Thread listenerThread = new Thread(serverListener);
		listenerThread.start();
	}
	
	/**
	 * Remove the given IP Address from the connected clients list
	 * @param ipAddress the IP Address that's to be removed from the connected clients list
	 */
	public void disconnectPlayer(InetAddress ipAddress){
		for (int i = 0; i < players.length; i++){
			if (players[i] == null){
				break;
			} else {
				if (players[i].getAddress().equals(ipAddress)){
					players[i] = null;
				}
			}
		}
	}
	
	/**
	 * Close the socket that the server is listening on, only use this to stop the server!
	 */
	public void stopListening(){
		serverListener.stopListening();
	}
	
	/**
	 * Send a message to all connected players
	 * @param message the message to send
	 */
	public void sendAll(String message){
		for (InetSocketAddress targetIP : players){
			if (targetIP != null){
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, targetIP);
				sendSingle(message, targetIP);
			}
		}
		System.out.println(String.format("SENT \"%1$s\" TO CLIENTS", message));
	}
	
	/**
	 * Send a message to a certain InetSocketAddress
	 * @param message the message to send
	 * @param targetIP the address to send the message to
	 */
	public void sendSingle(String message, InetSocketAddress targetIP){
		// Pick a random open DatagramSocket to send the DatagramPacket
		DatagramSocket sendingSocket = null;
		try {
			sendingSocket = new DatagramSocket();
		} catch (SocketException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// Empty the byte array to prevent messy data
		sendData = new byte[1024];
		// Convert the object to bytes and put it in the byte array
		sendData = message.getBytes();
		// Create the DatagramPacket to send
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, targetIP);
		
		// Send the DatagramPacket through the DatagramSocket
		try {
			sendingSocket.send(sendPacket);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// Close the socket
		sendingSocket.close();
	}
}
