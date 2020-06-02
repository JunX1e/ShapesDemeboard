import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**

 *
 *公开可以从客户端到服务器远程调用的方法。
 *
 */

public interface IDemoboard extends Remote
{
    void addShape(ShapeType shapeType, Color colour, int size, Point position,int xspeed,int yspeed) throws RemoteException;
    void clearWhiteboard() throws RemoteException;
    int noOfRegisteredClients() throws RemoteException;
    ArrayList<IShape> getCurrentShapes() throws RemoteException;
    ArrayList<Integer> getCurrentList() throws RemoteException;

    //Callback Operations
    int registerClient(IClientCallback callback) throws RemoteException;
    void deregisterClient(IClientCallback iClientCallback) throws RemoteException;
}
