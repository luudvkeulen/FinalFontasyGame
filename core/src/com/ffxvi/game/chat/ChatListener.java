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
package com.ffxvi.game.chat;

import com.ffxvi.game.logics.ChatManager;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A runnable which checks if chat messages have come in.
 */
public class ChatListener implements Runnable {
	private final static int PORT = 1336;
	
    /**
     * The listener's socket.
     */
    private final Socket socket;

    /**
     * The bufferedreader who reads the input.
     */
    private final BufferedReader in;
	
	/**
	 * This is to check if the chat needs to be listening or not.
	 */
	private boolean listening;
	
	/**
	 * The chatsender of the session.
	 */
	private ChatSender chatSender;
	
	/**
	 * The ChatManager which is responsible for GUI.
	 */
	private ChatManager chatManager;
	
    /**
     * The constructor of this class. Initializes all the variables in this
     * class.
     *
     * @throws IOException throws exception when the listener couldn't
     * initialize itself.
     */
    public ChatListener(String hostIP, ChatManager manager) throws IOException {
		this.socket = new Socket(hostIP, PORT);
		this.chatSender = new ChatSender(socket);
		this.chatManager = manager;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		listening = true;
    }

    /**
     * This method runs on a different thread. It is active as long as this
     * class is. It reads the messages and is responsible for sending it to the
     * GUI.
     */
    @Override
    public void run() {
        while (listening) {
            try {
				while (!this.in.ready()) {} //wait for message
                String receivedMessage = this.in.readLine();
                chatManager.receiveMessage(receivedMessage);
                System.out.println("Received message: " + receivedMessage);
            } catch (IOException ex) {
                Logger.getLogger(ChatListener.class.getName()).log(Level.SEVERE, null, "Got error while retrieving message:" + ex.getMessage());
            }
        }
    }
	
	public void stopListening() {
		listening = false;
	}
	
	public ChatSender getChatSender() {
		return this.chatSender;
	}
}
