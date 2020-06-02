import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.event.MouseInputListener;

/**

 *
 * WhiteboardPanel JPanel的扩展，它有助于绘制IShape对象。 setWhiteboardClient（WhiteboardClient inputWhiteboardClient）以便识别白板客户端，因此可以用来调用远程方法。 *
 * * invokeRepaint（）允许WhiteboardClient在更新时强制面板重新绘制。
 *
 */
public class ShapesDemoPanel extends JPanel implements MouseInputListener
{
    private ShapesDemoClient shapesDemoClient = null;
    private ShapesDemoView sdv = null ;

    //Constructor
    public ShapesDemoPanel()
    {
        super();

        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.LIGHT_GRAY);

        repaint();
    }
    public void setShapesDemoClient(ShapesDemoClient inputShapesDemoClient)
    {
        shapesDemoClient = inputShapesDemoClient;
    }
    public void clearWhiteboard()
    {
        shapesDemoClient.clearShapes();
    }

    @Override
    public void paint(Graphics graphics)
    {
        super.paint(graphics);

        ArrayList<IShape> shapes = shapesDemoClient.getShapes();
//        HashMap<IShape,String> viewShapes =  shapesDemoClient.getCurrentviewShapes();
//        System.out.println((shapes));
//        System.out.println((viewShapes));
        ArrayList<Integer> viewshapes = shapesDemoClient.getCurrentList(); ;
//        Set<IShape> keys = viewShapes.keySet();
//        for(IShape k:keys){
//            if(viewShapes.get(k)!="unsub"){
//                viewshapes.add(k);
//            }
//        }
//        System.out.println("客户端"+viewshapes);

        if(viewshapes.size() >-1) {
            if(viewshapes.size()<shapes.size()){
                for(int i = viewshapes.size(); i<shapes.size();i++){
                viewshapes.add(0);}
            }
            for (int shapeIndex = 0; shapeIndex < shapes.size(); shapeIndex++) {
                if (viewshapes.get(shapeIndex) == 1) {
                    IShape shape = shapes.get(shapeIndex);
                    graphics.setColor(shape.getColour());
                    shape.draw(graphics);
                }


            }
            shapesDemoClient.setCurrentlist(viewshapes);
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)

    {
        ArrayList<IShape> shapes = shapesDemoClient.getShapes();
        ArrayList<Integer> viewshapes = shapesDemoClient.getCurrentList();
//        System.out.println(viewshapes);
        if(viewshapes.size() >-1) {
            if (viewshapes.size() < shapes.size()) {
                for (int i = viewshapes.size(); i < shapes.size(); i++) {
                    viewshapes.add(0);
                }
            }
        }
        viewshapes.add(1);
        shapesDemoClient.setCurrentlist(viewshapes);
        if(shapesDemoClient.getCurrentShape() != null)
            shapesDemoClient.addNewShape(mouseEvent.getPoint());
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {

    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {

    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {

    }

    public void invokeRepaint()
    {
        repaint();
    }
}
