import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;


import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class ShapeListView implements ActionListener {
    private JFrame frame ;
    private JPanel panel;
    private JList list1 = null;
    private JButton btn ;
    //定义列表框
    private  int index = -1;
    private ArrayList<IShape> selectshapes;
    private ArrayList<Integer> subscribelist= null;
    public ArrayList<IShape> getSelectshapes(){return selectshapes;}
    public void setSelectshapes(ArrayList<IShape> shape) {selectshapes=shape;}

    public ShapeListView(ArrayList<IShape> shapes,ArrayList<Integer> sublist ,ShapesDemoView sdv) {
        btn.addActionListener(this);
        ArrayList<IShape> shapes1 = shapes;
//        System.out.println("传入了"+shapes1.get(0).getClass().toString());
        Vector<String> shape = new Vector<String>();
        String temple1, temple2, temple3 = "";
        String temple4 = "未订阅";
        DefaultListModel listModel = new DefaultListModel();
        list1.setModel(listModel);
        for (int i = 0; i < shapes1.size(); i++) {
            switch (shapes1.get(i).getClass().toString()) {
                case "class Square":
                    temple1 = "矩形";
                    break;
                case "class Circle":
                    temple1 = "圆形";
                    break;
                case "class Triangle":
                    temple1 = "三角形";
                    break;
                default:
                    temple1 = "读取失败";
                    break;
            }
            temple2 = "位置" + shapes1.get(i).getPosition().x + "," + shapes1.get(i).getPosition().y;

            Color color = shapes1.get(i).getColour();
            if (color.equals(Color.RED)) {
                temple3 = "红色";


            } else if (color.equals(Color.BLUE)) {
                temple3 = "蓝色";

            } else if (color.equals(Color.GREEN)) {
                temple3 = "绿色";
            } else {
                temple3 = shapes1.get(i).getColour().toString();
//            System.out.println("加入了"+temple);
            }
            if(sublist.get(i)==1){
                temple4 ="订阅";
            }
//            System.out.println(temple1 + temple2);
            shape.add(temple1 + "--" + temple2 + "--" + temple3);

            listModel.addElement(temple1 + "--" + temple2 + "--" + temple3 +"--"+ temple4);
        }

        list1.setBorder(BorderFactory.createTitledBorder("选择需要订阅的图形"));
        list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                 index = list1.getSelectedIndex() ;

                 sdv.setIndex(index);

            };
        });

        //第一个列表框一次可以选择多个选项
//        list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //第二个列表框每次可以选择一个选项
//        cont.add(this.list1);                   //加入面板
//        cont.add(btn);

//        System.out.println(index);
    }
    public void show(){
        frame = new JFrame("select");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(300,300);

    }

    public int getIndex() {
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String act = actionEvent.getActionCommand();
        if(act.equals("confirm"))
        {
//            System.out.println(index);

            frame.dispose();
            showMessageDialog(null,"请确认！！");

        }

    }
}


