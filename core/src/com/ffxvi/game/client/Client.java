/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.client;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class Client {
	
	private InetSocketAddress targetIP;
	private ClientListener clientListener;
	private byte[] sendData;
	
	public String getHostAddress(){
		return targetIP.toString();
	}
	
	public String getHostIP(){
		return targetIP.getAddress().toString();
	}
	
	public int getHostPort(){
		return targetIP.getPort();
	}
	
	public int getListeningPort(){
		return clientListener.getPort();
	}
	
	public Client(String hostIP, int hostPort, int listenerPort){
		// Set the host to send data to
		targetIP = new InetSocketAddress(hostIP, hostPort);
		send("CONNECTING");
		// Set the port to receive data on
		clientListener = new ClientListener(listenerPort);
		Thread listenerThread = new Thread(clientListener);
		listenerThread.start();
	}
	
	public void stopListening(){
		clientListener.stopListening();
	}
	
	public void send(String message){
		DatagramSocket sendingSocket = null;
		try {
			sendingSocket = new DatagramSocket();
		} catch (SocketException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		sendData = new byte[1024];
		sendData = message.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, targetIP);
		try {
			sendingSocket.send(sendPacket);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		System.out.println(String.format("SENT \"%1$s\" TO SERVER AT %2$s", message, targetIP.toString()));
		
		sendingSocket.close();
	}
}
