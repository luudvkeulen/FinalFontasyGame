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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * An interface used by RMI which provides methods to get and add servers.
 *
 */
public interface IServerList extends Remote {

    /**
     * Gets a List of available servers.
     *
     * @return A list of available servers.
     * @throws RemoteException when an (un)expected error regarding RMI
     * occurred.
     */
    public List<IServer> getServers() throws RemoteException;

    /**
     * Adds a server to the list of available servers.
     *
     * @param address The ip address of the server. When an empty String
     * (excluding spaces), throw an IllegalArgumentException.
     * @param port The port number of thee address. When negative, throw an
     * IllegalArgumentException.
     * @throws RemoteException whenan (un)expected error regarding RMI occurred.
     */
    public void addServer(String address, int port) throws RemoteException;
}
