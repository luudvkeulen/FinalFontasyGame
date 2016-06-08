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
    
    private final static int SENDPORT = 1335;
    
    private final ChatServer chatServer;
    
    public ChatSender(ChatServer chatServer) {
        this.chatServer = chatServer;
    }
    
    public void sendMessage(String message) throws IOException {
        for (Socket s : chatServer.getAllSockets()) {
            try {
                System.out.println("CHAT: trying to send msg back");
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                System.out.println("CHAT: Got out");
                out.writeBytes(message);
                System.out.println("CHAT: Have written");
            }
            
            catch (Exception ex) {
                System.out.println("CHAT: error sending back to client!");
            }

        }
    }
    
}
