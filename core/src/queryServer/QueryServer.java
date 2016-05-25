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
package queryServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server which maintains a list of game servers.
 */
public class QueryServer {

    /**
     * The port used for querying.
     */
    private static final int QUERYPORT = 420;

    /**
     * The binding name used for RMI.
     */
    private static final String BINDINGNAME = "serverList";

    /**
     * A controller of the servers.
     */
    private ServerList serverList;

    /**
     * *
     * A registry used for RMI.
     */
    private Registry registry;

    /**
     * Initializes a new QueryServer object.
     */
    public QueryServer() {
        //Try creating the serverlist
        try {
            this.serverList = new ServerList();
            System.out.println("Succesfully created serverlist");
        } catch (RemoteException re) {
            System.out.println("queryServer: RemoteException: " + re.getMessage());
            serverList = null;
        }

        //Try setting up the registery
        try {
            this.registry = LocateRegistry.createRegistry(QUERYPORT);
            System.out.println("Succesfully created registry");
        } catch (RemoteException re) {
            System.out.println("queryServer: RemoteException: " + re.getMessage());
            this.registry = null;
        }

        if (this.registry != null) {
            //Try binding the server
            try {
                this.registry.rebind(BINDINGNAME, this.serverList);
                System.out.println("Succesfully binded server list");
            } catch (RemoteException re) {
                System.out.println("queryServer: RemoteException: " + re.getMessage());
            }
        }
    }

    /**
     * Starts a new QueryServer.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new QueryServer();
    }
}
