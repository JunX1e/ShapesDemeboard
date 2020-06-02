import java.awt.*;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 *
 *提供覆盖的draw（Graphics graphics）方法并实现为IShape
 */
public class Circle extends BaseShape implements IShape
{
    public Circle(Color inputColour, int inputSize, Point inputPosition,int inputxspeed,int inputyspeed)
    {
        super(inputColour, inputSize, inputPosition,inputxspeed,inputyspeed);
    }

    @Override
    public void draw(Graphics graphics)
    {
        Point pos = getPosition();
        graphics.setColor(getColour());
        graphics.fillOval(pos.x, pos.y, getSize(), getSize());
    }


}
