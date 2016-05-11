/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.client;

import java.io.IOException;
import static java.lang.Thread.interrupted;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class ClientListener implements Runnable {
	
	private boolean listening;
	private int listenerPort;
	private DatagramSocket listenerSocket;
	
	public ClientListener(int listenOnPort){
		listening = true;
		listenerPort = listenOnPort;
	}
	
	public int getPort(){
		return listenerPort;
	}

	@Override
	public void run() {
		listenerSocket = null;
		try {
			listenerSocket = new DatagramSocket(listenerPort);
		} catch (SocketException ex) {
			Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		byte[] receiveData = new byte[1024];
		
		while(listening){
			receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException ioe) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, "Socket closed while listening.");
				break;
			}
			
			String sentence = new String(receivePacket.getData());
			System.out.println(String.format("SERVER AT %1$s SENT: %2$s", receivePacket.getSocketAddress().toString(), sentence.trim()));
		}
	}
	
	public void stopListening(){
		listening = false;
		listenerSocket.close();
	}
}
