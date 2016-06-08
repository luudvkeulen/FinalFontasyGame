/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.chat;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class ChatListenerThread implements Runnable {
    private final Socket clientSocket;
    private ChatSender chatSender;
    private boolean listening = true;
    
    public ChatListenerThread(Socket socket, ChatSender chatSender) {
        this.clientSocket = socket;
        this.chatSender = chatSender;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                        this.clientSocket.getInputStream()));
        }
        
        catch(Exception ex) {
            System.out.println(ex);
        }
        while (listening) {
            try {
                while(!in.ready()) {} //Wait for message to come in.
                String message = in.readLine();
                System.out.println("RECEIVED MESSAGE: " + message);
                chatSender.sendMessage(message);
                
            } catch (IOException ex) {
                System.out.println(ex);
            } 
        }
        
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(ChatListenerThread.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    
    public void stopListening() {
        this.listening = false;
        System.out.println("Server stopped listening to one client");
    }
}
