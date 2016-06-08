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
    
    ChatServer chatServer;
    
    public ChatSender(ChatServer chatServer) {
        this.chatServer = chatServer;
    }
    
    public void sendMessage(String message) throws IOException {
        for (InetSocketAddress p : chatServer.getGameServer().getPlayerAddresses()) {
            InetAddress adress = p.getAddress();
            int port = p.getPort();
            
            Socket s = new Socket(adress, port);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeBytes(message);
            s.close();
        }
    }
    
}
