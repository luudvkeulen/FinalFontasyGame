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
 * This class represents the ChatSender.
 * It has the responsibility of making sure 
 * the messages are send to the gameserver.
 * 
 * @author Guido
 */
public class ChatSender {
    
    /**
     * The socket where to connect to(Gameserver).
     * May not be null.
     */
    Socket socket;

    /**
     * The printwriter which sends the message to the socket's
     * output stream. May not be null.
     */
    PrintWriter out;
    
    /**
     * The constructor of this class. Initializes all the
     * variables which are located in this class.
     * @param serverIP The IP of the server, this may not be null
     * or empty.
     * 
     * @param serverPort The server's port which this class can connect to.
     * It may not be null.
     * 
     * @throws IOException Throws IOException when this method couldn't connect
     * to the server.
     */
    public ChatSender(String serverIP, int serverPort) throws IOException {
        if (serverIP == null || serverIP.trim().equals("")) {
            throw new IllegalArgumentException();
        }
        
        socket = new Socket (serverIP, serverPort);
        out = new PrintWriter (socket.getOutputStream(), true);
        
        sendTextMessage(new ChatTextMessage("pleeyer", "beright"));
    }
    
    /**
     * This method sends a message to the server via PrintWriter.
     * If the printwriter couldn't be initialized this method does nothing.
     * 
     * @param textMessage The textmessage which needs to be send. This may
     * not be null.
     * 
     * @throws IllegalArgumentException Throws exception when textmessage is null.
     */
    public void sendTextMessage(ChatTextMessage textMessage) {
        if (textMessage == null) {
            throw new IllegalArgumentException();
        }
        
        if (out != null) {
            out.println(textMessage.toString());
        }
        
        else {
            System.out.println("There is no connection with the server");
        }
    }
}
