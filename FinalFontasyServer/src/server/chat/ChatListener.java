/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.chat;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guido
 */
public class ChatListener implements Runnable{
    
    /**
     * The socket of the listener.
     * Gets initialized in the constructor.
     */
    ServerSocket serverSocket;
    
    /**
     * The listener's socket.
     */
    Socket socket;
    
    /**
     * The bufferedreader who reads the input.
     */
    BufferedReader in;
    
    /**
     * The constructor of this class.
     * Initializes all the variables in this class.
     * @throws IOException throws exception when the listener
     * couldn't initialize itself.
     */
    public ChatListener() throws IOException {
        serverSocket = new ServerSocket (1234);
        socket = serverSocket.accept();
        in = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
    }

    /**
     * This method runs on a different thread.
     * It is active as long as this class is.
     * It reads the messages and is responsible for
     * sending it to the GUI.
     */
    @Override
    public void run() {
        while (true) {
            try {
                String receivedMessage = in.readLine();
                //TODO: PUT MESSAGE IN GUI
                System.out.println ("Received message: " + receivedMessage);
            } 
            
            catch (IOException ex) {
                System.out.println("Got error while retrieving message:" 
                    + ex.getMessage());
            }
        }
    }
    
}
