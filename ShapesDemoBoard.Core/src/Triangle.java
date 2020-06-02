import java.awt.*;

/**
 * Created by BLourence on 17/02/15.
 *
 s* Provides overridden draw(Graphics graphics) method and implements as IShape
 *
 */
public class Triangle extends BaseShape implements IShape
{
    public Triangle(Color inputColour, int inputSize, Point inputPosition,int inputxspeed,int inputyspeed)
    {
        super(inputColour, inputSize, inputPosition,inputxspeed,inputyspeed);
    }

    @Override
    public void draw(Graphics graphics)
    {
        Point pos = getPosition();
        graphics.setColor(getColour());

        int xSpacer = getSize()/2;
        int ySpacer = getSize();

        int[] polyX = {pos.x-xSpacer, pos.x, pos.x+xSpacer};
        int[] polyY = {pos.y+ySpacer, pos.y, pos.y+ySpacer};
        Polygon poly = new Polygon(polyX, polyY, 3);

        graphics.fillPolygon(poly);
    }
}
