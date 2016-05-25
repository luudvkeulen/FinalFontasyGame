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

/**
 * Interface used for RMI which provides methods for getting the data of a
 * server.
 */
public interface IServer extends Remote {

    /**
     * Gets the IP address of this server.
     *
     * @return The IP address.
     * @throws RemoteException when an (un)expected error regarding RMI
     * occurred.
     */
    public String getAddress() throws RemoteException;

    /**
     * Gets the port number of this server.
     *
     * @return The port.
     * @throws RemoteException when an (un)expected error regarding RMI
     * occurred.
     */
    public int getPort() throws RemoteException;

    /**
     * Gets the full address (ip adress + port) of this server.
     *
     * @return The IP address + port.
     * @throws RemoteException when an (un)expected error regarding RMI
     * occurred.
     */
    public String getFullAddress() throws RemoteException;
}
