/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.ffxvi.game.entities.PlayerCharacter;
import com.ffxvi.game.entities.SimplePlayer;
import com.ffxvi.game.entities.SimpleProjectile;
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
import java.util.List;
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
	private List<InetSocketAddress> spectators;
	private HashMap<InetSocketAddress, SimplePlayer> playerData;
	private ServerListener serverListener;
	private Timer updateTimer;
	
	private int playerLimit;
	private int spectatorLimit;
	private byte[] sendData;

	/**
	 * Return the addresses of the connected clients
	 *
	 * @return an array of the connected clients
	 */
	public InetSocketAddress[] getPlayerAddresses() {
		return players;
	}
	
	/**
	 * Return the addresses + names of the connected Players
	 * @return a List of the connected Player's info
	 */
	public List<String> getPlayerInfo() {
		List<String> ret = new ArrayList();
		for (InetSocketAddress player : players) {
			if (player != null) {
				String address = player.toString();
				String name = playerData.get(player).getName();
				ret.add(address + " " + name);
			}
		}
		return ret;
	}

	/**
	 * Return the port that the server is listening on
	 *
	 * @return the port that the server is listening on
	 */
	public int getListeningPort() {
		return serverListener.getPort();
	}
	
	/**
	 * Adds the given InetAddres (IP) to the list of spectators
	 * @param ip the IP that's to be added to the list of spectators
	 */
	public void addSpectator(InetAddress ip) {
		if (spectators.size() < spectatorLimit) {
			spectators.add(new InetSocketAddress(ip, 1337));
		}
	}
	
	/**
	 * Remove the given InetAddress (IP) from the list of spectators
	 * @param ip the IP that's to be removed from the list of spectators
	 */
	public void removeSpectator(InetAddress ip) {
		List<InetSocketAddress> localSpectators = spectators;
		for (InetSocketAddress socket : localSpectators) {
			if (socket.getAddress().equals(ip.getAddress())) {
				spectators.remove(socket);
			}
		}
	}

	/**
	 * initialise the server. The server is listening in a thread, this is
	 * because the listening code keeps waiting until a packet is received
	 *
	 * @param listenerPort the port that the server will be listening on
	 */
	public Server(int listenerPort) {
		playerLimit = 16;
		// Initialize the players array
		players = new InetSocketAddress[playerLimit];
		// Initialize the playerData HashMap
		playerData = new HashMap(playerLimit);
		spectatorLimit = 1000;
		spectators = new ArrayList();
		// Set the port to receive data on
		serverListener = new ServerListener(this, listenerPort);
		Thread listenerThread = new Thread(serverListener);
		listenerThread.start();

		// Start the updateTimer
		startTimer();
	}

	/**
	 * Starts the updateTimer to send data updates to all clients every 0.02
	 * seconds (20 milliseconds)
	 */
	private void startTimer() {
		updateTimer = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				InetSocketAddress[] localPlayers = players;
				// Send each connected player the data of all other players
				for (InetSocketAddress address : localPlayers) {
					if (address != null) {
						if (playerData.get(address) != null) {
							HashMap<InetSocketAddress, SimplePlayer> dataMapWithoutSender = new HashMap(playerData);
							int clientRoomId = dataMapWithoutSender.get(address).getRoomId();
							dataMapWithoutSender.remove(address);
							Collection<SimplePlayer> dataListWithoutSender = new ArrayList();
							// Remove the data from players that are not in the client's room from the Collection
//							for (SimplePlayer sp : dataListWithoutSender){
//								if (sp.getRoomId() != clientRoomId){
//									dataListWithoutSender.remove(sp);
//								}
//							}

							for (InetSocketAddress key : dataMapWithoutSender.keySet()) {
								SimplePlayer sp = dataMapWithoutSender.get(key);
								//if (sp.getRoomId() == clientRoomId) {
									dataListWithoutSender.add(sp);
								//}
							}
							sendSingle(dataListWithoutSender, address);
						}
					}
				}
				List<InetSocketAddress> localSpectators = spectators;
				Collection<SimplePlayer> localPlayerData = playerData.values();
				for (InetSocketAddress socket : localSpectators) {
					sendSingle(localPlayerData, socket);
				}
			}
		};
		// Execute the TimerTask once every 0.02 seconds (20 milliseconds)
		updateTimer.scheduleAtFixedRate(tt, 0, 20);
	}

	/**
	 * Add the given IP Address to the connected clients list
	 *
	 * @param ipAddress the IP Address that's to be added to the connected
	 * clients list
	 * @return true if the player was successfully connected, false if the
	 * player is already connected or if the server is full
	 */
	public boolean connectPlayer(InetSocketAddress ipAddress) {
		int canConnect = -1;
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				canConnect = i;
			} else if (players[i].getAddress().equals(ipAddress.getAddress())) {
				return false;
			}
		}
		if (canConnect > -1) {
			players[canConnect] = ipAddress;
			return true;
		}
		return false;
	}

	/**
	 * Remove the given IP Address from the connected clients list and remove
	 * the player's data from the playerData HashMap
	 *
	 * @param ipAddress the IP Address that's to be removed from the connected
	 * clients list
	 * @return true if the player was successfully disconnected, false if the
	 * player is not connected
	 */
	public boolean disconnectPlayer(InetSocketAddress ipAddress) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i].equals(ipAddress)) {
					playerData.remove(players[i]);
					players[i] = null;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Stop listening for new data and stop sending data to connected clients.
	 */
	public void stop() {
		serverListener.stopListening();
		updateTimer.cancel();
		sendAll("SERVER CLOSING", null);
		for (int i = 0; i < players.length; i++) {
			players[i] = null;
		}
		playerData.clear();
	}

	/**
	 * Send a message to all connected players except for the sender
	 *
	 * @param object the Object to send
	 * @param sender the IP address of the sender. This is used to prevent
	 * sending a client's data back to itself
	 */
	public void sendAll(Object object, InetAddress sender) {
		for (InetSocketAddress targetIP : players) {
			if (targetIP != null) {
				if (!targetIP.getAddress().equals(sender)) {
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, targetIP);
					sendSingle(object, targetIP);
				}
			}
		}
		if (object instanceof String) {
			System.out.println(String.format("SENT \"%1$s\" TO CONNECTED CLIENTS", (String) object));
		}
	}

	/**
	 * Send a message to a certain InetSocketAddress
	 *
	 * @param message the message to send
	 * @param targetIP the address to send the message to
	 */
	public void sendSingle(Object message, InetSocketAddress targetIP) {
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
//			System.out.println(sendData.length);
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
	 *
	 * @param obj the Object to serialize
	 * @return the serialized Object turned into a byte array
	 * @throws IOException when the OutputStream gets disrupted
	 */
	public byte[] serialize(Object obj) throws IOException {
		try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
			try (ObjectOutputStream o = new ObjectOutputStream(b)) {
				o.writeObject(obj);
			}
			return b.toByteArray();
		}
	}

	/**
	 * Treats received SimplePlayer the way it is supposed to be treated by
	 * adding the client's data to the playerData HashMap
	 *
	 * @param playerAddress the senders InetSocketAddress (IP + Port)
	 * @param simplePlayer the received SimplePlayer data
	 */
	public void receivePlayer(InetSocketAddress playerAddress, SimplePlayer simplePlayer) {
		// If the senders SimplePlayer data's name isn't the same as the received
		// data's name, then the received data will be sent to the address that
		// matches the received SimplePlayer data's name. This is only supposed
		// to happen when the received SimplePlayer's data's score has been increased. 
		SimplePlayer localPlayer = playerData.get(playerAddress);
		if (localPlayer != null) {
			if (!playerData.get(playerAddress).getName().equals(simplePlayer.getName())) {
				Collection<InetSocketAddress> localPlayers = playerData.keySet();
				for (InetSocketAddress localAddress : localPlayers) {
					SimplePlayer splayer = playerData.get(localAddress);
					if (splayer.getName().equals(simplePlayer.getName())) {
						sendSingle(simplePlayer, localAddress);
						break;
					}
				}
				return;
			} else {
				playerData.put(playerAddress, simplePlayer);
//				System.out.println(String.format("DATA ADDED FROM %1$s", playerAddress.toString()));

				if (simplePlayer.getScore() >= 10) {
					sendSingle("VICTORY", playerAddress);
					sendAll("DEFEAT", playerAddress.getAddress());
					stop();
				}
			}
		} else {
			Collection<SimplePlayer> localPlayers = playerData.values();
			for (SimplePlayer splayer : localPlayers) {
				if (splayer.getName().equals(simplePlayer.getName())) {
					sendSingle("That name is already taken", playerAddress);
					sendSingle("DISCONNECTED", playerAddress);
					return;
				}
			}
			playerData.put(playerAddress, simplePlayer);
		}
	}

	/**
	 * Treats received SimpleProjectile the way it is supposed to be treated by sending
	 * the client's data to all the other clients
	 *
	 * @param playerAddress the senders InetSocketAddress (IP + Port)
	 * @param simpleProjectile the received SimpleProjectile data
	 */
	public void receiveProjectile(InetSocketAddress playerAddress, SimpleProjectile simpleProjectile) {
		for (InetSocketAddress address : players) {
			if (address != null) {
				if (!address.equals(playerAddress)){
					SimplePlayer sPlayer = playerData.get(address);
					if (sPlayer != null) {
						if (sPlayer.getRoomId() == simpleProjectile.getRoomID()){
							sendSingle(simpleProjectile, address);
						}
					}
				}
			}
		}
//		sendAll(simpleProjectile, validSender.getAddress());
	}
}
