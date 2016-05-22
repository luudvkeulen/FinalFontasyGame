/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.client;

import com.ffxvi.game.entities.SimplePlayer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Collection;
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
	
	/**
	 * Initiate this runnable
	 * @param listenOnPort the port that the client will use for listening
	 */
	public ClientListener(int listenOnPort){
		listening = true;
		listenerPort = listenOnPort;
	}
	
	/**
	 * Returns the port number that is used by the client for listening
	 * @return listening port number
	 */
	public int getPort(){
		return listenerPort;
	}

	/**
	 * Start this listening thread
	 */
	@Override
	public void run() {
		// Declare the socket that is to be used by the client for listening
		listenerSocket = null;
		try {
			listenerSocket = new DatagramSocket(listenerPort);
		} catch (SocketException ex) {
			Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		byte[] receiveData;
		
		while(listening){
			// Keep waiting until a packet is received or the socket gets closed
			receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException ioe) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, "Socket closed while listening.");
				break;
			}
			
			// Deserialize the received object
			byte[] data = receivePacket.getData();
			Object object = null;
			try {
				object = deserialize(data);
			} catch (IOException ex) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			// Check the type of the received object and treat it accordingly
			if (object instanceof String){
				receiveString(receivePacket, (String)object);
			} else if (object instanceof Collection){
				// Check what type of collection the received object is
				for (Object o : (Collection)object){
					if (o.getClass().equals(SimplePlayer.class)){
						receivePlayers(receivePacket, (Collection<SimplePlayer>)object);
					}
					break;
				}
//			} else if (object instanceof SimpleProjectile){
				// TODO need SimpleProjectile class
			}
		}
	}
	
	/**
	 * Close the socket that the client is listening on, only use this to disconnect!
	 */
	public void stopListening(){
		listening = false;
		listenerSocket.close();
	}
	
	/**
	 * De-serializes a received byte array
	 * @param bytes the received byte array
	 * @return the Object created from the byte array
	 * @throws IOException when the InputStream gets disrupted
	 * @throws ClassNotFoundException when the class that the byte array is supposed to become, cannot be found
	 */
	private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
		Object ret = o.readObject();
        return ret;
    }
	
	/**
	 * Treats a received String the way it is supposed to be treated by starting
	 * the game on this client when receiving CONNECTED and stopping the game on
	 * this client when receiving DISCONNECTED
	 * @param packet the received DatagramPacket
	 * @param data the received String
	 */
	private void receiveString(DatagramPacket packet, String data){
		String message = data.trim();
		System.out.println(String.format("SERVER AT %1$s SENT: %2$s", packet.getSocketAddress().toString(), message));
		
		if (message.equals("CONNECTED")){
			// TODO start the game clientside
		} else if (message.equals("DISCONNECTED")){
			// TODO stop the game clientside
		}
	}
	
	/**
	 * Treats received player data the way it is supposed to be treated
	 * by using it in the game
	 * @param packet the received DatagramPacket
	 * @param data the received SimplePlayer
	 */
	private void receivePlayers(DatagramPacket packet, Collection<SimplePlayer> data){
		System.out.println(String.format("RECEIVED DATA FROM %1$s PLAYERS", data.size()));
		// TODO actually use the received data
	}
}
