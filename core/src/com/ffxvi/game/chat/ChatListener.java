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
 * A runnable which checks if chat messages have come in.
 */
public class ChatListener implements Runnable {

    /**
     * The socket of the listener. Gets initialized in the constructor.
     */
    private final ServerSocket serverSocket;

    /**
     * The listener's socket.
     */
    private final Socket socket;

    /**
     * The bufferedreader who reads the input.
     */
    private final BufferedReader in;

    /**
     * The constructor of this class. Initializes all the variables in this
     * class.
     *
     * @throws IOException throws exception when the listener couldn't
     * initialize itself.
     */
    public ChatListener() throws IOException {
        this.serverSocket = new ServerSocket(1234);
        this.socket = this.serverSocket.accept();
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    /**
     * This method runs on a different thread. It is active as long as this
     * class is. It reads the messages and is responsible for sending it to the
     * GUI.
     */
    @Override
    public void run() {
        while (true) {
            try {
                String receivedMessage = this.in.readLine();
                //TODO: PUT MESSAGE IN GUI
                System.out.println("Received message: " + receivedMessage);
            } catch (IOException ex) {
                System.out.println("Got error while retrieving message:"
                        + ex.getMessage());
            }
        }
    }

}
