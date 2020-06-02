import java.awt.*;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

/**

 *
 *提供应用程序中所有形状所需的通用get，set方法和变量。 *
 * *实现可序列化，因此可以将对象与server.s之间传递
 */
public abstract class BaseShape implements Serializable
{
    //Private Fields
    private int size = 30;
    private Point position;
    private Color colour = Color.BLUE;
    private int xspeed,yspeed;

    //Public Properties
    public Color getColour(){
        return colour;
    }
    public void setColour(Color inputColour){
        colour = inputColour;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int s){
        size = s;
    }
    public Point getPosition(){
        return position;
    }
    public void setPosition(Point p){
        position = p;
    }

    public int getXspeed() {
        return xspeed;
    }

    public void setXspeed(int xs) {
        xspeed = xs;
    }

    public int getYspeed() {
        return yspeed;
    }

    public void setYspeed(int yspeed) {
        this.yspeed = yspeed;
    }

    //Constructor
    public BaseShape(Color inputColour, int inputSize, Point inputPosition,int inputxspeed,int inputyspeed)
    {

        colour = inputColour;
        size = inputSize;
        position = inputPosition;
        xspeed = inputxspeed;
        yspeed = inputyspeed;
    }

}
