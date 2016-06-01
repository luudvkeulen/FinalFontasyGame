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
package server.chat;

import java.io.*;
import java.net.*;
import server.Server;

/**
 * This class represents the ChatSender. It has the responsibility of making
 * sure the messages are send to the clients.
 *
 * @author Guido
 */
public class ChatSender {

	/**
	 * The server which contains all the players.
	 */
	Server server;

	/**
	 * This is the constructor of the class ChatSender. It initializes the
	 * fields.
	 *
	 * @param server The server which contains all the players.
	 *
	 * @throws IllegalArgumentException when server is null.
	 */
	public ChatSender(Server server) {
		if (server == null) {
			throw new IllegalArgumentException();
		} else {
			this.server = server;
		}
	}

	/**
	 * This method sends a message to the server via PrintWriter. If the
	 * printwriter couldn't be initialized this method does nothing.
	 *
	 * @param textMessage The textmessage which needs to be send. This may not
	 * be null.
	 * @throws java.io.IOException when socket can't get initialized.
	 *
	 * @throws IllegalArgumentException Throws exception when textmessage is
	 * null or empty.
	 */
	public void sendTextMessage(String textMessage) throws IOException {
		if (textMessage == null || textMessage.trim().equals("")) {
			throw new IllegalArgumentException();
		}

		for (InetSocketAddress i : server.getPlayerAddresses()) {
			String clientIP = i.getAddress().toString();
			int port = i.getPort();

			Socket clientSocket = new Socket(clientIP, port);
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

			out.writeBytes(textMessage);
			out.close();
			clientSocket.close();
		}
	}
}
