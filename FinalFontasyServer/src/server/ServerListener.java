/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.entities.SimplePlayer;

/**
 *
 * @author Robin
 */
public class ServerListener implements Runnable {
	
	private boolean listening;
	private int listenerPort;
	private DatagramSocket listenerSocket;
	
	private Server server;
	
	/**
	 * Initiate this runnable
	 * @param listenOnPort the port that the server will use for listening
	 */
	public ServerListener(int listenOnPort){
		listening = true;
		listenerPort = listenOnPort;
	}
	
	/**
	 * Returns the port number that is used by the server for listening
	 * @return listening port number
	 */
	public int getPort(){
		return listenerPort;
	}
	
	/**
	 * Start this listening thread
	 */
	@Override
	public void run(){
		server = FinalFontasyServer.server;
		
		// Declare the socket that is to be used by the server for listening
		listenerSocket = null;
		try {
			listenerSocket = new DatagramSocket(listenerPort);
		} catch (SocketException ex) {
			Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		byte[] receiveData;
		
		while(listening){
			// Keep waiting until a packet is received or the socket gets closed
			receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException ioe) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, "Socket closed while listening.");
				break;
			}
			
			// Deserialize the received object
			byte[] data = receivePacket.getData();
			Object object = null;
			try {
				object = deserialize(data);
			} catch (IOException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			// Check the type of the received object and treat it accordinly
			if (object instanceof String){
				receiveString((String)object, receivePacket);
			} else if (object instanceof SimplePlayer){
				receivePlayer((SimplePlayer)object, receivePacket);
			}
		}
	}
	
	/**
	 * Close the socket, only use this to stop the server!
	 */
	public void stopListening(){
		listening = false;
		listenerSocket.close();
	}
	
	/**
	 * Deserializes a received byte array
	 * @param bytes the received byte array
	 * @return the Object created from the byte array
	 * @throws IOException when the input stream gets disrupted
	 * @throws ClassNotFoundException when the class that the byte array is supposed to become, cannot be found
	 */
	private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
	
	/**
	 * Treats a received String the way it is supposed to be treated
	 * by adding a client to the connected clients list when receiving CONNECTING
	 * and isn't already connected. Removes a client from the connected clients
	 * list when receiving DISCONNECTING. Otherwise a capitalized version of the
	 * received String will be replied to the sender. 
	 * @param data the received String
	 * @param packet the received DatagramPacket
	 */
	private void receiveString(String data, DatagramPacket packet){
		String message = data.trim();
		System.out.println(String.format("CLIENT AT %1$s SENT: %2$s", packet.getSocketAddress().toString(), message));
		InetSocketAddress playerAddress = new InetSocketAddress(packet.getAddress(), 1337);
		
		if (message.equals("CONNECTING")){
		// Add the client to the connected clients list
			for (int player = 0; player < server.getPlayerAddresses().length; player++){
				InetSocketAddress address = server.getPlayerAddresses()[player];
				if (address != null){
					if (address.equals(playerAddress)){
						break;
					}
				} else {
					address = playerAddress;
					break;
				}
			}
		} else if (message.equals("DISCONNECTING")){
			// Remove the client from the connected clients list
			server.disconnectPlayer(packet.getAddress());
		} else {
			// Reply the capitalized received String
			server.send(message.toUpperCase());
		}
	}
	
	/**
	 * Treats received player data the way it is supposed to be treated
	 * by sending the client's player data to all the other clients
	 * @param data the received SimplePlayer
	 * @param packet the received DatagramPacket
	 */
	private void receivePlayer(SimplePlayer data, DatagramPacket packet){
		
	}
}