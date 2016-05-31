package queryServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface which allows to manage the list of available servers.
 */
public interface IServerList extends Remote {

    /**
     * Gets a List of servers which are available.
     *
     * @return A List of Servers.
     * @throws RemoteException when an (un)expected RMI error occurs.
     */
    public List<IServer> getServers() throws RemoteException;

    /**
     * Adds a server to the list of servers.
     *
     * @param address The IP address of the server. When an empty String
     * (excluding spaces), throws an IllegalArgumentException.
     * @param port The port number of the server. When not > 0, throws an
     * IllegalArgumentException.
     * @throws RemoteException when an (un)expected RMI error occurrs.
     */
    public void addServer(String address, int port) throws RemoteException;

    /**
     * Removes a server from the list of servers.
     *
     * @param address The IP address of the server. When an empty String
     * (excluding spaces), throws an IllegalArgumentException.
     * @throws RemoteException when an (un)expected RMI error occurs.
     */
    public void removeServer(String address) throws RemoteException;
}
