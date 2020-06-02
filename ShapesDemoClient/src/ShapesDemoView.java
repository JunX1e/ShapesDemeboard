import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**

 * WhiteboardView源文件与WhiteboardView.form的伴奏*
 * * updateClientInfo（）更新显示带有从服务器检索到的数据的客户机信息的JLabel。 *
 * * AssignColour（string colourString）返回新形状的Color（awt）。 *
 * * setController（WhiteboardClient inputController）分配对whiteboardClient *的引用，以便从视图上的操作调用方法。 *
 * * showForm（）打包并将视图显示给用户。 * * invokeRepaint（）通过调用updateClientInfo（）更新客户端信息，并在自定义whiteboardPanel中重新绘制形状*。 *
 * * actionPerformed（ActionEvent actionEvent）捕获视图中的事件，例如选择新形状，
 * *清除要更新的白板或颜色组合框。 *
 * * stateChanged（ChangeEvent changeEvent）在从视图更改大小时捕获。
 */
public class ShapesDemoView implements ActionListener, ChangeListener
{
    private JButton clearBtn;
    private JButton circleBtn;
    private JButton triangleBtn;
    private JButton squareBtn;
    private JButton subscribeBtn;
    private JPanel panel1;
    private JPanel WhiteboardContainerPanel;
    private ShapesDemoPanel shapesDemoPanel;
    private JSpinner sizeSpinner;
    private JComboBox colourPicker;
    private JLabel connectedClientsLbl;
    private JLabel clientNumberLbl;
    private ShapesDemoClient shapesDemoClient = null;
    private  int index =-1;
    public ArrayList<Integer> currentlist = new ArrayList<Integer>(){} ;
    private boolean isSelected = true;


    public void setShapesDemoClient(ShapesDemoClient shapesDemoClient) {
        this.shapesDemoClient = shapesDemoClient;
    }



    public ArrayList<Integer> getCurrentlist() {
        return currentlist;
    }

    public void setCurrentlist(ArrayList<Integer> currentlist) {
        this.currentlist = currentlist;
    }

    //Constructor
    public ShapesDemoView()
    {
        squareBtn.addActionListener(this);
        circleBtn.addActionListener(this);
        triangleBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        subscribeBtn.addActionListener(this);

        colourPicker.addItem("Red");
        colourPicker.addItem("Green");
        colourPicker.addItem("Blue");

        sizeSpinner.setValue(50);
        sizeSpinner.addChangeListener(this);
        colourPicker.addActionListener(this);

    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void updateClientInfo()
    {
        String connectedClientsStr = shapesDemoClient.getConnectedClients() + "";
        String clientNumberStr = shapesDemoClient.getClientNumber() + "";

        connectedClientsLbl.setText(connectedClientsStr);
        clientNumberLbl.setText(clientNumberStr);
    }
    private Color assignColour(String colourString)
    {
        if(colourString.equals("Green"))
            return Color.green;
        else if(colourString.equals("Red"))
            return Color.red;
        else
            return Color.blue;
    }

    public void setController(ShapesDemoClient inputController)
    {
        shapesDemoClient = inputController;
        shapesDemoPanel.setShapesDemoClient(inputController);
        updateClientInfo();
    }
    public void showForm()
    {
        JFrame frame = new JFrame("Shapes-Demo-SHOWBOARD");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700,600);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing (WindowEvent we)
            {
                shapesDemoClient.deregisterClient();
                System.exit(0);
            }
        });
    }
    public void invokeRepaint()
    {
        updateClientInfo();
        shapesDemoPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        String act = actionEvent.getActionCommand();
        if(act.equals("Square"))
        {
            shapesDemoClient.setSelectedShape(ShapeType.Square);
//            System.out.println(this.getCurrentlist());

        }
        else if(act.equals("Triangle"))
        {
            shapesDemoClient.setSelectedShape(ShapeType.Triangle);
        }
        else if(act.equals("Circle"))
        {
            shapesDemoClient.setSelectedShape(ShapeType.Circle);

        }
        else if(act.equals("Clear"))
        {
            shapesDemoPanel.clearWhiteboard();
            shapesDemoClient.setCurrentlist(new ArrayList<Integer>());
        }
        else if(act.equals("comboBoxChanged"))
        {
            String colStr = colourPicker.getSelectedItem().toString();
            shapesDemoClient.setColour(assignColour(colStr));
        }
        else if(act.equals("subscribe") || act.equals("confirm")) {
            ArrayList<Integer> currentlist1 = shapesDemoClient.getCurrentList();
//            System.out.println("页面上的值为："+shapesDemoClient.getCurrentList());
            if(!isSelected) {
                if (currentlist1.size() < shapesDemoClient.getShapes().size()) {
//                    System.out.println("IN");
                    for (int j = currentlist1.size(); j < shapesDemoClient.getShapes().size(); j++) {
                        currentlist1.add(0);
                    }
                }
                if(index == -1){
                    isSelected = true;
                    subscribeBtn.setText("subscribe");
                }
                else if (currentlist1.get(index) == 0) {
                    currentlist1.set(index, 1);
                }
                shapesDemoClient.setCurrentlist(currentlist1);
               shapesDemoClient.setCurrentlist(currentlist1);
//               System.out.println(this.getCurrentlist());

            }
            if (isSelected) {
                ShapeListView sv = new ShapeListView(shapesDemoClient.getShapes(), shapesDemoClient.getCurrentList(),this);
                sv.show();
                isSelected = false;
                subscribeBtn.setText("confirm");
            } else {
                isSelected = true;
                subscribeBtn.setText("subscribe");

            }

        }
    }



    @Override
    public void stateChanged(ChangeEvent changeEvent)
    {
        shapesDemoClient.setSize((Integer)sizeSpinner.getValue());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
