import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**

 *
 *服务器应用程序的入口点。 *在应用程序的Jvm中创建一个注册表，然后绑定白板实现。 * WhiteBoardShapeServer绑定到reg中，而WhiteServerApp充当主机
 */
public class ShapesDemoServerApp
{
    public static void main(String[] args)
    {
        try
        {
            //creates registry instance in JavaVm
            Registry reg = LocateRegistry.createRegistry(1099);

            shapesDemoShapeServer server = new shapesDemoShapeServer();
            Thread td = new Thread(server);
            td.start();
            Naming.rebind("Shapesdemoboard", server);
            System.out.println("Shapesdemoboard server bound in Jvm reg...");
        }
        catch(Exception ex)
        {
//            System.out.println("Error creating JVM reg " + ex.getMessage());
            ex.getStackTrace();
        }
    }
}
