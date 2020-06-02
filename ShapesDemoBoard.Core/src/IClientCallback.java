import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * Interface to facilitate updating individual clients via the notifyUpdate() method.
 */
public interface IClientCallback extends Remote
{
    void notifyUpdate() throws RemoteException;

}
