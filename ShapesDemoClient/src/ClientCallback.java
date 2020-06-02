import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**

* ClientCallback定义了一个可用于在服务器上注册客户端的类。
 * *实现IClientCallback有助于notifyUpdate（），它允许服务器
 * *在应该更新客户端时通知客户端，而不是通过轮询来查看是否发生了更改。
 */
public class ClientCallback extends UnicastRemoteObject implements IClientCallback, Serializable
{
    private ShapesDemoClient shapesDemoClient;

    protected ClientCallback() throws RemoteException
    {
    }

    public void setShapesDemoClient(ShapesDemoClient inputShapesDemoClient)
    {
        shapesDemoClient = inputShapesDemoClient;
    }
    public void notifyUpdate() throws RemoteException
    {
        if(shapesDemoClient != null)
            shapesDemoClient.updateClient();
    }
}
