/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.ffxvi.game.entities.SimplePlayer;
import com.ffxvi.game.entities.SimpleProjectile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 *
	 * @param server the server that created this runnable, used for sending
	 * data
	 * @param listenOnPort the port that the server will use for listening
	 */
	public ServerListener(Server server, int listenOnPort) {
		this.server = server;
		listening = true;
		listenerPort = listenOnPort;
	}

	/**
	 * Returns the port number that is used by the server for listening
	 *
	 * @return listening port number
	 */
	public int getPort() {
		return listenerPort;
	}

	/**
	 * Start this listening thread
	 */
	@Override
	public void run() {
		// Declare the socket that is to be used by the server for listening
		listenerSocket = null;
		try {
			listenerSocket = new DatagramSocket(listenerPort);
		} catch (SocketException ex) {
			Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
		}

		byte[] receiveData;

		while (listening) {
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

			// Check the type of the received object and treat it accordingly
			receiveObject(receivePacket, object);
		}
	}

	/**
	 * Close the socket that the server is listening on, only use this to stop
	 * the server!
	 */
	public void stopListening() {
		listening = false;
		listenerSocket.close();
	}

	/**
	 * De-serializes a received byte array
	 *
	 * @param bytes the received byte array
	 * @return the Object created from the byte array
	 * @throws IOException when the InputStream gets disrupted
	 * @throws ClassNotFoundException when the class that the byte array is
	 * supposed to become, cannot be found
	 */
	private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream o = new ObjectInputStream(b)) {
				return o.readObject();
			}
		}
	}

	/**
	 * Treats a received String the way it is supposed to be treated by adding a
	 * client to the connected clients list when receiving CONNECTING and isn't
	 * already connected. Removes a client from the connected clients list when
	 * receiving DISCONNECTING. Otherwise a capitalized version of the received
	 * String will be replied to the sender.
	 *
	 * @param packet the received DatagramPacket
	 * @param data the received String
	 */
	private void receiveString(DatagramPacket packet, String data) {
		String message = data.trim();
		System.out.println(String.format("CLIENT AT %1$s SENT: %2$s", packet.getSocketAddress().toString(), message));
		InetSocketAddress playerAddress = new InetSocketAddress(packet.getAddress(), 1337);

		if (message.equals("CONNECTING")) {
			// Add the client to the connected clients list
			if (server.connectPlayer(playerAddress)) {
				server.sendSingle("CONNECTED", playerAddress);
			}
		} else if (message.equals("DISCONNECTING")) {
			// Remove the client from the connected clients list
			if (server.disconnectPlayer(playerAddress)) {
				server.sendSingle("DISCONNECTED", playerAddress);
			}
		} else if (message.equals("SPECTATING")) {
			server.addSpectator(packet.getAddress());
		} else if (message.equals("STOPSPECTATING")) {
			server.removeSpectator(packet.getAddress());
		} else {
			// Reply the capitalized received String
			server.sendAll(message.toUpperCase(), packet.getAddress());
		}
	}

	/**
	 * Treats received object the way it is supposed to be treated by sending
	 * the client's data to all the other clients
	 *
	 * @param packet the received DatagramPacket
	 * @param data the received Object
	 */
	private void receiveObject(DatagramPacket packet, Object data) {
		// If the received data is a String, this might be the client connecting
		// or disconnecting. If a client is trying to connect, it will obviously
		// not yet be in the list of connected clients. This is why we handle
		// received Strings differently from other received Objects. 
		if (data instanceof String) {
			receiveString(packet, (String) data);
			return;
		}
		
		InetSocketAddress validSender = null;
		for (InetSocketAddress address : server.getPlayerAddresses()) {
			if (address != null) {
				if (address.getAddress().equals(packet.getAddress())) {
					validSender = address;
					break;
				}
			}
		}
		if (validSender == null) {
			System.out.println("Received data was not sent by a connected client");
			return;
		}

		if (data instanceof SimplePlayer) {
			server.receivePlayer(validSender, (SimplePlayer) data);
			return;
		} else if (data instanceof SimpleProjectile) {
			System.out.println("Projectile received from " + packet.getSocketAddress().toString());
			server.receiveProjectile(validSender, (SimpleProjectile) data);
			return;
		} else {
			server.sendSingle("Invalid data received", new InetSocketAddress(packet.getAddress(), 1337));
			return;
		}
	}
}
