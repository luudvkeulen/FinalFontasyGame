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

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents a game server.
 *
 */
public class Server implements IServer, Serializable {

    /**
     * A String containing the IP address of this server.
     */
    public String address;

    /**
     * A String containing the port number of this server.
     */
    public int port;

    /**
     * Initializes a new Server.
     *
     * @param address The IP address of the server. When an empty String
     * (excluding spaces), throw an IllegalArgumentException.
     * @param port The port number of the server. When negative, throw an
     * IllegalArgumentException.
     */
    public Server(String address, int port) {

        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address can neither be null nor an empty String (excluding spaces).");
        }

        if (port < 0) {
            throw new IllegalArgumentException("Port can not be negative.");
        }

        this.address = address;
        this.port = port;
    }

    @Override
    public String getAddress() throws RemoteException {
        return this.address;
    }

    @Override
    public int getPort() throws RemoteException {
        return this.port;
    }

    @Override
    public String getFullAddress() throws RemoteException {
        return this.address + ":" + this.port;
    }
}
