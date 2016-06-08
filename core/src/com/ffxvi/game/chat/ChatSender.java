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

import java.io.*;
import java.net.*;

/**
 * This class represents the ChatSender. It has the responsibility of making
 * sure the messages are send to the gameserver.
 *
 */
public class ChatSender {

    /**
     * The socket where to connect to(Gameserver). May not be null.
     */
    private final Socket socket;

    /**
     * The printwriter which sends the message to the socket's output stream.
     * May not be null.
     */
    private final PrintWriter out;

    /**
     * The constructor of this class. Initializes all the variables which are
     * located in this class.
     *
	 * @param socket The socket where to send the message to.
     * @throws IOException Throws IOException when this method couldn't connect
     * to the server.
     */
    public ChatSender(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    /**
     * This method sends a message to the server via PrintWriter. If the
     * printwriter couldn't be initialized this method does nothing.
     *
     * @param textMessage The textmessage which needs to be send. This may not
     * be null.
     *
     * @throws IllegalArgumentException Throws exception when textmessage is
     * null.
     */
    public void sendTextMessage(ChatTextMessage textMessage) {
        if (textMessage == null) {
            throw new IllegalArgumentException();
        }

        if (this.out != null) {
            this.out.println(textMessage.toString() + "\n");
        } else {
            System.out.println("There is no connection with the server");
        }
    }
}
