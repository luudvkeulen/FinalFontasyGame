/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Guido
 */
public class ChatSender {

    private final ChatServer chatServer;
    private ArrayList<Socket> toDelete;
    
    public ChatSender(ChatServer chatServer) {
        this.chatServer = chatServer;
        this.toDelete = new ArrayList<>();
    }
    
    public void sendMessage(String message) throws IOException {
        for (Socket s : chatServer.getAllSockets()) {
            try {
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                out.writeBytes(message + "\n");
                System.out.println("CHAT: Have written to " + s.toString());
            }
            
            catch (Exception ex) {
                System.out.println("CHAT: error sending back to client!");
                System.out.println("CHAT: will delete socket from list");
                toDelete.add(s);
            }
        }
        
        for (Socket s : toDelete) {
            chatServer.getAllSockets().remove(s);
        }
        
        toDelete.clear();
    }
    
}
