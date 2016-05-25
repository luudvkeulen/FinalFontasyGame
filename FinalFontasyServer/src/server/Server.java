/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.ffxvi.game.entities.SimplePlayer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class Server {
	
	private InetSocketAddress[] players;
	private HashMap<InetSocketAddress, SimplePlayer> playerData;
	private ServerListener serverListener;
	private Timer updateTimer;
	private int serverSize;
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
		serverSize = 16;
		// Initialize the players array
		players = new InetSocketAddress[serverSize];
		// Initialize the playerData HashMap
		playerData = new HashMap(serverSize);
		// Set the port to receive data on
		serverListener = new ServerListener(this, listenerPort);
		Thread listenerThread = new Thread(serverListener);
		listenerThread.start();	
		
		// Start the updateTimer
		startTimer();
	}
	
	/**
	 * Starts the updateTimer to send data updates to all clients every 0.02 seconds (20 milliseconds)
	 */
	private void startTimer(){
		updateTimer = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				// Send each connected player the data of all other players
				for (InetSocketAddress address : players){
					if (address != null){
						if (playerData.get(address) != null){
							HashMap<InetSocketAddress, SimplePlayer> dataMapWithoutSender = new HashMap(playerData);
							int clientRoomId = dataMapWithoutSender.get(address).getRoomId();
							//dataMapWithoutSender.remove(address);
							Collection<SimplePlayer> dataListWithoutSender = new ArrayList(dataMapWithoutSender.values());
							// Remove the data from players that are not in the client's room from the Collection
							for (SimplePlayer sp : dataListWithoutSender){
								if (sp.getRoomId() != clientRoomId){
									dataListWithoutSender.remove(sp);
								}
							}
							sendSingle(dataListWithoutSender, address);
						}
					}
				}
			}
		};
		// Execute the TimerTask once every 0.02 seconds (20 milliseconds)
		updateTimer.scheduleAtFixedRate(tt, 0, 20);
	}
	
	/**
	 * Add the given IP Address to the connected clients list
	 * @param ipAddress the IP Address that's to be added to the connected clients list
	 * @return true if the player was successfully connected, false if the player is already connected or if the server is full
	 */
	public boolean connectPlayer(InetSocketAddress ipAddress){
		int canConnect = -1;
		for (int i = 0; i < players.length; i++){
			if (players[i] == null){
				canConnect = i;
			} else if (players[i].getAddress().equals(ipAddress.getAddress())){
				return false;
			}
		}
		if (canConnect > -1){
			players[canConnect] = ipAddress;
			return true;
		}
		return false;
	}
	
	/**
	 * Remove the given IP Address from the connected clients list and remove the player's data
	 * from the playerData HashMap
	 * @param ipAddress the IP Address that's to be removed from the connected clients list
	 * @return true if the player was successfully disconnected, false if the player is not connected
	 */
	public boolean disconnectPlayer(InetSocketAddress ipAddress){
		for (int i = 0; i < players.length; i++){
			if (players[i].getAddress().equals(ipAddress)){
				playerData.remove(players[i]);
				players[i] = null;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Stop listening for new data and stop sending data to connected clients.
	 */
	public void stop(){
		serverListener.stopListening();
		updateTimer.cancel();
		sendAll("SERVER CLOSING", null);
		for (int i = 0; i < players.length; i++){
			players[i] = null;
		}
		playerData.clear();
	}
	
	/**
	 * Send a message to all connected players except for the sender
	 * @param message the message to send
	 */
	public void sendAll(Object object, InetAddress sender){
		for (InetSocketAddress targetIP : players){
			if (targetIP != null){
				if (!targetIP.getAddress().equals(sender)){
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, targetIP);
					sendSingle(object, targetIP);
				}
			}
		}
		if (object instanceof String){
			System.out.println(String.format("SENT \"%1$s\" TO CONNECTED CLIENTS", (String)object));
		}
	}
	
	/**
	 * Send a message to a certain InetSocketAddress
	 * @param message the message to send
	 * @param targetIP the address to send the message to
	 */
	public void sendSingle(Object message, InetSocketAddress targetIP){
		// Pick a random open DatagramSocket to send the DatagramPacket
		DatagramSocket sendingSocket = null;
		try {
			sendingSocket = new DatagramSocket();
		} catch (SocketException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		// Empty the byte array to prevent messy data
		sendData = new byte[1024];
		
		try {
			// Convert the object to bytes and put it in the byte array
			sendData = serialize(message);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		
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
	
	/**
	 * Turns the given Object into a byte array
	 * @param obj the Object to serialize
	 * @return the serialized Object turned into a byte array
	 * @throws IOException when the OutputStream gets disrupted
	 */
	public byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
	
	public void receivePlayer(InetSocketAddress playerAddress, SimplePlayer player){
		playerData.put(playerAddress, player);
		System.out.println(String.format("DATA ADDED FROM %1$s", playerAddress.toString()));
	}
}
