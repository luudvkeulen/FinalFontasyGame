/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.chat;

import java.io.*; 
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author Acer
 */
public class ChatServer implements Runnable{
    
    private static int port;
    private boolean listening = true;
    private Server gameServer;
    private ChatSender chatSender;

    public ChatServer(Server gameServer, int port) {
        this.port = port;
        this.gameServer = gameServer;
        this.chatSender = new ChatSender(this);
    }
    
    @Override
    public void run() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(port);
            while (listening) {
                ChatListenerThread clt = new ChatListenerThread(welcomeSocket.accept(), chatSender);
                Thread t = new Thread(clt);
                t.start();
                System.out.println("Server: Made new connection!");
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopChat() {
        listening = false;
    }
    
    public Server getGameServer() {
        return this.gameServer;
    }
    
}
