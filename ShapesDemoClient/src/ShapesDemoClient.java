import java.awt.*;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 初始化后，setupClient（）将对远程白板服务器执行查找。
 *然后，客户端将自己注册到服务器，并返回一个客户端编号。 *
 * * deregisterClient（）从服务器上已注册客户端的列表中删除客户端。 *
 * * updateClient（）从服务器getCurrentShapes（）中检索形状列表，并更新连接到服务器getNumberOfClients（）的客户端数量。 *
 * * clearShapes（）调用服务器以清除要存储的形状列表。 *
 * * addNewShape（Point point）本身会在服务器上调用rmi方法addShape（getCurrentShape（），getColour（），getSize（），point）
 * *“客户端应将图形对象添加到白色*指定其类型（圆形，三角形或正方形），颜色，大小和位置。
 */
public class ShapesDemoClient implements Serializable
{
    //Private Fields
    private int clientNumber = 0;
    private int connectedClients = 0;
    private int size = 30;
    private IDemoboard whiteboard = null;
    private static ShapesDemoView shapesDemoView = null;
    private ClientCallback callBackClient = null;
    private ArrayList<IShape> shapes;
    private HashMap<IShape,String> viewshapes;
    private Color colour = Color.red ;
    private ShapeType currentShape;
    private int xspeed = 1;
    private int yspeed = 1;

    //Public Properties
    public int getClientNumber() {return clientNumber;}
    public int getConnectedClients(){return connectedClients;}
    public ArrayList<IShape> getShapes(){return shapes;}
    //返回显示的hash
    public HashMap<IShape, String> getViewshapes() {
        return viewshapes;
    }
    public ArrayList<Integer> currentlist = new ArrayList<Integer>();


    public int getSize(){return size;}
    public void setSize(int inputSize){size = inputSize;}
    public Color getColour(){return colour;}
    public void setColour(Color inputColour){colour = inputColour;}
    public  int getXspeed(){return  xspeed;}
    public void setXspeed(int inputXspeed){xspeed = inputXspeed;}
    public int getYspeed() { return yspeed; }
    public void setYspeed(int inputYspeed) { yspeed = inputYspeed; }

    public void setCurrentlist(ArrayList<Integer> currentlist) {

            this.currentlist = currentlist;

    }
    private ArrayList<Integer> getCurrentlist(){return currentlist;}

    public ShapeType getCurrentShape()
    {
    return currentShape;
    }
    public void setSelectedShape(ShapeType newSelectedShape)
    {
        if(newSelectedShape != currentShape)
            currentShape = newSelectedShape;
    }

    private void setupClient()
    {
        try
        {
            whiteboard = (IDemoboard) Naming.lookup("Shapesdemoboard");
            System.out.println("Shapesdemoboard initialised....");

            //register client for callbacks
            IClientCallback callbackClients = new ClientCallback();
            clientNumber = whiteboard.registerClient(callbackClients);

            callBackClient = (ClientCallback)callbackClients;
            callBackClient.setShapesDemoClient(this);

            System.out.println("Retrieving current Shapesdemoboard shapes");
            shapes = whiteboard.getCurrentShapes();
            connectedClients = getNumberOfClients();
        }
        catch (Exception ex)
        {
            System.out.println("Error setting up white board client: " + ex.getMessage());
            ex.getStackTrace();
        }
    }
    private int getNumberOfClients()
    {
        int result = 0;
        try
        {
            result = whiteboard.noOfRegisteredClients();
        }
        catch(Exception ex)
        {
            System.out.println("Error adding new shape: " + ex.getMessage());
            ex.getStackTrace();
        }
        return result;
    }
//    public HashMap<IShape, String> getCurrentviewShapes(){
//        HashMap<IShape, String> currentviewShapes = null ;
//        ArrayList<IShape> currentShapes = null;
//        try {
//            currentShapes = whiteboard.getCurrentShapes();
//            System.out.println("操作前"+currentShapes);
//            System.out.println("操作前"+currentviewShapes);
//            String view = "unsub";
//            for (int i = 0 ; i < currentShapes.size(); i++){
//
////                currentviewShapes.put(currentShapes.get(i), view);	//map集合添加
//                System.out.println("操作后"+currentviewShapes);
//
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//
//        return currentviewShapes;
//    }
    public ArrayList<Integer> getCurrentList(){
        return currentlist;
    }
    private ArrayList<IShape> getCurrentShapes()
    {
        ArrayList<IShape> currentShapes = null;
        try
        {
            currentShapes = whiteboard.getCurrentShapes();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return currentShapes;
    }
    public void updateClient()
    {
        shapes = getCurrentShapes();
//        viewshapes = getCurrentviewShapes();
        connectedClients = getNumberOfClients();
        shapesDemoView.invokeRepaint();
    }
    public void clearShapes()
    {
        try
        {
            whiteboard.clearWhiteboard();
            shapesDemoView.invokeRepaint();
        }
        catch(Exception ex)
        {
            System.out.println("Error Clearing Whiteboard: " + ex.getMessage());
            ex.getStackTrace();
        }
    }
    public void addNewShape(Point point)
    {
//        System.out.println("客户端："+this.getCurrentList());
        try
        {
            whiteboard.addShape(getCurrentShape(), getColour(), getSize(), point,getXspeed(),getYspeed());

            //invoke from call back
            shapes = whiteboard.getCurrentShapes();
            shapesDemoView.invokeRepaint();
        }
        catch(Exception ex)
        {
            System.out.println("Error adding new shape: " + ex.getMessage());
            ex.getStackTrace();
        }
    }
    public void deregisterClient()
    {
        IClientCallback iClientCallBack = callBackClient;
        try
        {
            whiteboard.deregisterClient(iClientCallBack);
        }
        catch (Exception ex)
        {
            System.out.println("Error setting up white board client: " + ex.getMessage());
            ex.getStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ShapesDemoClient ds = new ShapesDemoClient();
        ds.setupClient();

        shapesDemoView = new ShapesDemoView();
        shapesDemoView.setController(ds);



        //init GUI
        shapesDemoView.showForm();
    }
}
