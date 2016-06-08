/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author Guido
 */
public class ChatSender {

    private final ChatServer chatServer;
    
    public ChatSender(ChatServer chatServer) {
        this.chatServer = chatServer;
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
            }

        }
    }
    
}
