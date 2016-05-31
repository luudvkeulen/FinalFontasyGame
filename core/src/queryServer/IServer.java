package queryServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface which allows to get the address information of a server.
 */
public interface IServer extends Remote {

    /**
     * Gets the IP address of the this server.
     *
     * @return The IP address as a String.
     * @throws RemoteException when an (un)expected RMI error occurs.
     */
    String getAddress() throws RemoteException;

    /**
     * Gets the port number of this server.
     *
     * @return the port number as an int.
     * @throws RemoteException when an (un)expected RMI error occurs.
     */
    int getPort() throws RemoteException;

    /**
     * Gets the full address (IPaddress:Portnumber) of this server.
     *
     * @return the full address of the server as a String.
     * @throws RemoteException when an (un)expected RMI error occurs.
     */
    String getFullAddress() throws RemoteException;
}
