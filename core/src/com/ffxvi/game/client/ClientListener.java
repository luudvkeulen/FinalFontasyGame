/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package com.ffxvi.game.client;

import com.badlogic.gdx.Gdx;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.models.Ending;
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.models.SimplePlayer;
import com.ffxvi.game.models.SimpleProjectile;
import com.ffxvi.game.screens.EndScreen;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.screens.MenuScreen;
import com.ffxvi.game.screens.ServerBrowserScreen;
import com.ffxvi.game.support.Vector;
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
 * A class which acts as a listener towards the server.
 */
public class ClientListener implements Runnable {

	/**
	 * Boolean indicating whether the client is listening.
	 */
	private boolean listening;

	/**
	 * The port number of the listener.
	 */
	private final int listenerPort;

	/**
	 * A datagram socket used for listening.
	 */
	private DatagramSocket listenerSocket;

	/**
	 * The Game Screen.
	 */
	private final GameScreen screen;

	/**
	 * Initiate this runnable.
	 *
	 * @param listenOnPort the port that the client will use for listening
	 * @param screen the GameScreen that uses this client
	 */
	public ClientListener(int listenOnPort, GameScreen screen) {
		this.listening = true;
		this.listenerPort = listenOnPort;
		this.screen = screen;
//        System.out.println(screen.getClass().getName());
	}

	/**
	 * Returns the port number that is used by the client for listening.
	 *
	 * @return listening port number.
	 */
	public int getPort() {
		return this.listenerPort;
	}

	/**
	 * Start this listening thread
	 */
	@Override
	public void run() {
		// Declare the socket that is to be used by the client for listening
		this.listenerSocket = null;
		try {
			this.listenerSocket = new DatagramSocket(this.listenerPort);
		} catch (SocketException ex) {
			Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
		}

		byte[] receiveData;

		while (this.listening) {
			// Keep waiting until a packet is received or the socket gets closed
			receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				this.listenerSocket.receive(receivePacket);
			} catch (IOException ioe) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, "Socket closed while listening:{0}", ioe.getMessage());
				break;
			}

			// Deserialize the received object
			byte[] data = receivePacket.getData();
//            System.out.println(data.length);
			Object object = null;
			try {
				object = deserialize(data);
			} catch (IOException ex) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
			}

			// Check the type of the received object and treat it accordingly
			if (object instanceof String) {
				this.receiveString(receivePacket, (String) object);
			} else if (object instanceof SimplePlayer) {
				System.out.println("Increasing Score");
				this.screen.getMainPlayer().increaseScore();
			} else if (object instanceof Collection) {
				// Check what type of collection the received object is
				for (Object o : (Collection) object) {
					if (o.getClass().equals(SimplePlayer.class)) {
						this.receivePlayers(receivePacket, (Collection<SimplePlayer>) object);
					}
					break;
				}
			} else if (object instanceof SimpleProjectile) {
				this.receiveProjectile(receivePacket, (SimpleProjectile) object);
			}
		}
	}

	/**
	 * Close the socket that the client is listening on, only use this to
	 * disconnect!
	 */
	public void stopListening() {
		this.listening = false;
		this.listenerSocket.close();
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
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}

	/**
	 * Treats a received String the way it is supposed to be treated by starting
	 * the game on this client when receiving CONNECTED and stopping the game on
	 * this client when receiving DISCONNECTED
	 *
	 * @param packet the received DatagramPacket
	 * @param data the received String
	 */
	private void receiveString(DatagramPacket packet, String data) {
		String message = data.trim();
		System.out.println(String.format("SERVER AT %1$s SENT: %2$s", packet.getSocketAddress().toString(), message));

		if (message.equals("CONNECTED")) {
			// TODO start the game clientside
		} else if (message.equals("DISCONNECTED")) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					MainClass game = MainClass.getInstance();
					game.getScreen().dispose();
					game.setScreen(new ServerBrowserScreen());
				}
			});
		} else if (message.equals("DEFEAT") || message.equals("VICTORY")) {
			final Ending e;
			if(message.equals("DEFEAT")) {
				e = Ending.DEFEAT;
			} else {
				e = Ending.VICTORY;
			}
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					MainClass game = MainClass.getInstance();
					game.getScreen().dispose();
					game.setScreen(new EndScreen(e));
				}
			});
		}
	}

	/**
	 * Treats received player data the way it is supposed to be treated by using
	 * it in the game
	 *
	 * @param packet the received DatagramPacket
	 * @param data the received SimplePlayer
	 */
	private void receivePlayers(DatagramPacket packet, Collection<SimplePlayer> data) {
		// System.out.println(String.format("RECEIVED DATA FROM %1$s PLAYERS", data.size()));
		this.screen.addMultiPlayers(data);
	}

	/**
	 * Treats the received projectile data the way it is supposed to be treated
	 * by using it in the game
	 *
	 * @param packet the received DatagramPacket
	 * @param data the received SimpleProjectile
	 */
	private void receiveProjectile(DatagramPacket packet, SimpleProjectile data) {
		// Convert the SimpleProjectile to a Projectile
		Projectile projectile = new Projectile(new Vector(data.getX(), data.getY()),
				data.getSpeed(), data.getRotation(), data.getRoomID(), data.getPlayerName(), this.screen);

		// Add the projectile to the GameScreen
		screen.addProjectile(projectile, true);
	}
}
