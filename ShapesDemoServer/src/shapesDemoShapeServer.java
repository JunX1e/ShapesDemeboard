import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**

 *
 * ArrayList shapeList：用于保存对添加到白板的形状对象的引用。 *
 * * ShapeFactory shapeFactory：根据信息生成形状，返回IShape，因此客户端*无需知道如何创建仅通过工厂要求的形状。 *
 * *回调：* ArrayList clientCallbacks：保存已注册用于服务器回调的客户端列表。 * registerClient（IClientCallback iClientCallback）用于为回调注册客户端。
 * * deregisterClient（IClientCallback iClientCallback）删除传递的客户端。 * notifyClientsUpdate（）使每个注册的客户端更新其白板连接的客户端信息。
 */

public class shapesDemoShapeServer extends UnicastRemoteObject implements IDemoboard,Runnable
{
    private int clientNo = 1;
    private ShapeFactory shapeFactory = new ShapeFactory();
    private ArrayList<IShape> shapeList = new ArrayList<IShape>();
    private ArrayList<Integer> currentList = new ArrayList<Integer>();
    private ArrayList<IClientCallback> clientCallbacks = new ArrayList<IClientCallback>();
    private ShapeType currenttype;

    //Constructor
    public shapesDemoShapeServer() throws RemoteException
    {
        super();
        System.out.println("Starting whiteboard server....");

    }
    public void addShape(ShapeType shapeType, Color color, int size, Point point,int xspeed,int yspeed) throws RemoteException
    {
        //create and add shape as IShape
        IShape shape = shapeFactory.generateShape(shapeType,color, size, point,xspeed,yspeed);
        shapeList.add(shape);
        System.out.println(shapeList.get(0).getPosition()+"加上了");
        notifyClientsUpdate();
    }
    public void clearWhiteboard()
    {
        shapeList.clear();
        notifyClientsUpdate();
    }
    public int noOfRegisteredClients()
    {
        if(clientCallbacks != null)
            return clientCallbacks.size();

        return 0;
    }
    public ArrayList<IShape> getCurrentShapes()
    {
        return shapeList;
    }
    public ArrayList<Integer> getCurrentList()
    {
        return currentList;
    }


    //Callback Methods
    public void notifyClientsUpdate()
    {
        for(int i = 0; i < clientCallbacks.size(); i++)
        {
            try
            {
                clientCallbacks.get(i).notifyUpdate();
            }
            catch (Exception ex)
            {
//                System.out.println("Error Notifying Clients: " + ex.getMessage());
            }
        }
    }
    public synchronized int registerClient(IClientCallback iClientCallback) throws RemoteException
    {
        clientCallbacks.add(iClientCallback);
        notifyClientsUpdate();
        return clientNo++;
    }
    public synchronized void deregisterClient(IClientCallback iClientCallback) throws RemoteException
    {
        if(clientCallbacks.contains(iClientCallback))
        {
            clientCallbacks.remove(iClientCallback);
            notifyClientsUpdate();
        }
    }

    @Override
    public void run() {

        int x,y,speedY,speedX;
//        System.out.println(shapeList);

        while (true) {
            System.out.print("");
            for (int k = 0; k < shapeList.size(); k++) {
//                System.out.println("PAINT BALL "+k);

                x = shapeList.get(k).getPosition().x;
                y = shapeList.get(k).getPosition().y;
               speedX =shapeList.get(k).getXspeed();
               speedY = shapeList.get(k).getYspeed();
                if (y >= 525)//当y接触到下限时
                    speedY *= -1;//速度反向
                else if (y <= 0)
                    speedY *= -1;
                if (x >= 390)
                    speedX *= -1;
                else if (x <= 0)
                    speedX *= -1;
                x += speedX;//每一次run，就移动一次速度值
                y += speedY;
                shapeList.get(k).setPosition(new Point(x,y));
                shapeList.get(k).setXspeed(speedX);
                shapeList.get(k).setYspeed(speedY);

                try {
                    Thread.sleep(10);       //小睡一下
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(list);

            }
//            if(shapeList.size()>0){notifyClientsUpdate();}
            notifyClientsUpdate();


            }

        }
    }

