import java.awt.*;
/**

 *
 *利用Factory设计模式，客户只需要一个新的IShape对象，然后
 * *工厂处理实例化逻辑。该程序不关心返回的底层对象
 * *仅是IShape类型。
 */
public class ShapeFactory
{
    public IShape generateShape(ShapeType shapeType, Color colour, int size, Point position ,int xspeed,int yspeed)
    {
        IShape shape = null;

        switch (shapeType)
        {
            case Circle:
                shape = new Circle(colour, size, position,xspeed,yspeed);
                break;
            case Triangle:
                shape = new Triangle(colour, size, position,xspeed,yspeed);
                break;
            case Square:
                shape = new Square(colour, size, position,xspeed,yspeed);
                break;
        }
        return shape;
    }


}
