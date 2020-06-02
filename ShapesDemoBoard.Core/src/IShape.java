import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.rmi.Remote;

/**
 *
 *
 * 启用对形状对象的通用处理，如IShape，因此所有形状都作为单个对象列表持久存在*
 * *公开了应用程序其他方面所需的必要方法。
 */
public interface IShape extends Remote

{

    Point getPosition();
    void setPosition(Point point);

    Color getColour();
    void setColour(Color colour);

    int getSize();
    void setSize(int s);

    int getXspeed();
    void setXspeed(int xs);

    int getYspeed();
    void setYspeed(int ys);


    void draw(Graphics graphics);
}
