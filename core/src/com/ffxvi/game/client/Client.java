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
package com.ffxvi.game.client;

import com.ffxvi.game.entities.SimplePlayer;
import com.ffxvi.game.entities.SimpleProjectile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class responsible for the client side of the system.
 */
public final class Client {

    /**
     * The address (IP address + port number) of the host.
     */
    private final InetSocketAddress hostAddress;

    /**
     * A class which acts as a listener towards the server.
     */
    private final ClientListener clientListener;

    /**
     * A bytearray with the data which is sent.
     */
    private byte[] sendData;

    /**
     * Initialize the client. The client is listening in a thread, this is
     * because the listening code keeps waiting until a packet is received
     *
     * @param hostIP the IP address that the client will connect with
     * @param hostPort the port on the host that the client will connect with
     * @param listenerPort the port that the client will be listening on
     */
    public Client(String hostIP, int hostPort, int listenerPort) {

        // Set the host to send data to
        this.hostAddress = new InetSocketAddress(hostIP, hostPort);
        this.send("CONNECTING");
        // Set the port to receive data on
        this.clientListener = new ClientListener(listenerPort);
        Thread listenerThread = new Thread(this.clientListener);
        listenerThread.start();
    }

    /**
     * The host's address (IP address + port number)
     *
     * @return the host's address (IP address + port number)
     */
    public String getHostAddress() {
        return this.hostAddress.toString();
    }

    /**
     * The host's IP address
     *
     * @return the host's IP address
     */
    public String getHostIP() {
        return this.hostAddress.getAddress().toString();
    }

    /**
     * The host's port number
     *
     * @return the host's port number
     */
    public int getHostPort() {
        return this.hostAddress.getPort();
    }

    /**
     * The port that this client is listening on
     *
     * @return the port that this client is listening on
     */
    public int getListeningPort() {
        return this.clientListener.getPort();
    }

    /**
     * Stop listening for new data and stop sending data to the host, use this
     * for disconnecting or stopping the application
     */
    public void stop() {
        this.send("DISCONNECTING");
        this.clientListener.stopListening();
    }

    /**
     * Send a SimplePlayer to the host, only intended to use with mainPlayer
     *
     * @param player the SimplePlayer data to be sent
     */
    public void sendPlayer(SimplePlayer player) {
        if (player != null) {
            send(player);
            return;
        }
        throw new IllegalArgumentException("SimplePlayer can't be a null value");
    }

    /**
     * Send a SimpleProjectile to the host, only use this when the projectile is
     * created
     *
     * @param projectile the projectile to be sent
     */
    public void sendProjectile(SimpleProjectile projectile) {
        if (projectile != null) {
            send(projectile);
            return;
        }
        throw new IllegalArgumentException("SimpleProjectile can't be a null value");
    }

    /**
     * Send an Object to the host
     *
     * @param message the Object to send
     */
    private void send(Object message) {
        DatagramSocket sendingSocket = null;
        try {
            sendingSocket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Empty the byte array to prevent messy data
        this.sendData = new byte[1024];

        try {
            // Convert the object to bytes and put it in the byte array
            this.sendData = this.serialize(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create the DatagramPacket to send
        DatagramPacket sendPacket = new DatagramPacket(this.sendData, this.sendData.length, this.hostAddress);

        if (sendingSocket != null) {
            // Send the DatagramPacket through the DatagramSocket
            try {
                sendingSocket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Close the socket
            sendingSocket.close();
        }
    }

    /**
     * Turns the given Object into a byte array
     *
     * @param obj the Object to serialize
     * @return the serialized Object turned into a byte array
     * @throws IOException when the OutputStream gets disrupted
     */
    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }
}
