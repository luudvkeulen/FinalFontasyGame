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
package com.ffxvi.game.serverlist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import queryServer.IServer;
import queryServer.IServerList;

/**
 * Class which is responsible for retrieving a list of servers.
 *
 */
public class ServerRetriever {

    /**
     * The static IP address used for RMI.
     */
    private static final String IP = "192.168.1.2";

    /**
     * The static port used for RMI.
     */
    private static final int PORT = 420;

    /**
     * The static binding name used for RMI.
     */
    private static final String BINDINGNAME = "serverList";
    /**
     * Registry used for RMI.
     */
    private Registry registry;

    /**
     * Interface used for RMI which controls the list of servers.
     */
    private IServerList serverList;

    /**
     * Initializes a new ServerRetriever.
     *
     * @throws IOException when an (un)expected Exception occurs.
     */
    public ServerRetriever() throws IOException {
        //Try to get the registry
        try {
            this.registry = LocateRegistry.getRegistry(IP, PORT);
            System.out.println("Registry lookup succesfull");
        } catch (RemoteException re) {
            System.out.println("GameServer: RemoteException: " + re.getMessage());
            this.registry = null;
        }

        if (this.registry != null) {
            try {
                this.serverList = (IServerList) this.registry.lookup(BINDINGNAME);
            } catch (RemoteException re) {
                System.out.println("GameServer: RemoteException: " + re.getMessage());
                this.serverList = null;
            } catch (NotBoundException nbe) {
                System.out.println("GameServer: NotBoundException: " + nbe.getMessage());
                this.serverList = null;
            }
        }
    }

    /**
     * Gets the adresses from the server list.
     *
     * @return A List of Strings containing the server adresses.
     * @throws RemoteException when an (un)expected Exception regarding RMI
     * occurs.
     */
    public List<String> getAddresses() throws RemoteException {
        List<String> addresses = new ArrayList<String>();
        if (this.registry != null) {
            for (IServer server : this.serverList.getServers()) {
                addresses.add(server.getFullAddress());
            }
        }
        return addresses;
    }

    /**
     * Is used to launch the server.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            new ServerRetriever();
        } catch (IOException ex) {
            Logger.getLogger(ServerRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
