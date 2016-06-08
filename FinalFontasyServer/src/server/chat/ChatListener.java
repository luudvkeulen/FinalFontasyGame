/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.chat;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author Guido
 */
public class ChatListener implements Runnable {

	private boolean listening;
	private int listenerPort;
	ServerSocket serverSocket;
	private Server server;

	/**
	 * Initiate this runnable
	 *
	 * @param server the server that created this runnable, used for sending
	 * data
	 * @param listenOnPort the port that the server will use for listening
	 * @throws java.io.IOException throws exception when serverSocket cannot be
	 * initialized.
	 */
	public ChatListener(Server server, int listenOnPort) throws IOException {
		this.server = server;
		this.listening = true;
		this.listenerPort = listenOnPort;
		this.serverSocket = new ServerSocket(listenOnPort);
	}

	/**
	 * This method runs on a different thread. It is active as long as this
	 * class is. It reads the messages and is responsible for sending it to the
	 * GUI.
	 */
	@Override
	public void run() {
		try {
			ChatSender sender = new ChatSender(server);

			while (listening) {
				Socket connectionSocket = serverSocket.accept();
				BufferedReader inFromClient
						= new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				sender.sendTextMessage(inFromClient.readLine());
			}
		} catch (IOException ex) {
			Logger.getLogger(ChatListener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Call this to stop the thread from running.
	 */
	public void stopListener() {
		listening = false;
	}
}
