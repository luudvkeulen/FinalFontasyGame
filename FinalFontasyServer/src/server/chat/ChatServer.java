/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.chat;

import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author Acer
 */
public class ChatServer implements Runnable{
    
    private ArrayList<Socket> allClients;
    private final static int LISTENPORT = 1336;
    private boolean listening = true;
    private Server gameServer;
    private ChatSender chatSender;

    public ChatServer(Server gameServer) {
        this.gameServer = gameServer;
        this.chatSender = new ChatSender(this);
        this.allClients = new ArrayList<>();
    }
    
    @Override
    public void run() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(LISTENPORT);
            while (listening) {
                System.out.println("CHATMASTER: Server is now waiting at port: " + LISTENPORT);
                Socket s = welcomeSocket.accept();
                allClients.add(s);
                ChatListenerThread clt = new ChatListenerThread(s, chatSender);
                Thread t = new Thread(clt);
                t.start();
                System.out.println("CHATMASTER: Server Made new connection!");
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
    
    public ArrayList<Socket> getAllSockets() {
        return allClients;
    }
    
}
