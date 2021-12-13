package Pages;

import main.Algorithm;
import main.Graph;
import main.Node;
import main.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

public class EditGraphPage extends Page{
    JButton addNode = new JButton("Add node ");
    JButton deleteNode = new JButton("Delete node ");
    JButton addEdge = new JButton("Add edge ");
    JButton deleteEdge = new JButton("Delete edge ");
    JButton backFromEdit = new JButton("↩");

    public EditGraphPage(){
        panel.add(addNode);
        addNode.addActionListener(e -> {
            int key=0;

            String input="";
            double x=0;
            double y=0;
            double weight=1;
            String info="";
            try {
                input = JOptionPane.showInputDialog(panel, "Enter Node Values: x,y,weight,info\nExample: 5.3,7.1,12,Hello!", null);
            } catch (NullPointerException n) {
                input = "";
            }
                if(input.matches("(\\d+),(\\d+),(\\d+),(\\w+)")) {
                    String[] regex = input.split(",");
                    x = Double.parseDouble(regex[0]);
                    y = Double.parseDouble(regex[1]);
                    weight=Double.parseDouble(regex[2]);
                    info=regex[3];
                }

            Queue<Integer> keys=((Graph)algorithm.getGraph()).getRemovedKeys();
            if(keys.isEmpty())
                algorithm.getGraph().addNode(new Node(algorithm.getGraph().nodeSize(), new Point3D(x, y, 0), weight, info, 0));
            else
                algorithm.getGraph().addNode(new Node(keys.poll(), new Point3D(x, y, 0), weight, info, 0));

            rePaint();
            });

        panel.add(deleteNode);
        deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input="";
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter node key(id)", null);
                } catch (NullPointerException n) {
                    input = "";
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
                }
                if(input.matches("\\d+")) {
                    System.out.println(Integer.parseInt(input));
                    getAlgorithm().getGraph().removeNode(Integer.parseInt(input));

                    rePaint();
                }

            }
        });

        panel.add(addEdge);
        addEdge.addActionListener(e -> {
            String input="";
            try {
                input = JOptionPane.showInputDialog(panel, "Enter src key(id), dest key, weight\nExample: 5,7,1.5", null);
            } catch (NullPointerException n) {
                input = "";
                JOptionPane.showMessageDialog(panel, "Invalid input!");
            }
            if(input.matches("(\\d+),(\\d+),(\\d+)")) {
                String[] regex = input.split(",");
                getAlgorithm().getGraph().connect(Integer.parseInt(regex[0]),Integer.parseInt(regex[1]),Double.parseDouble(regex[2]));
                rePaint();
            }

        });

        panel.add(deleteEdge);
        deleteEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input="";
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter src key(id), dest key\nExample: 3,2", null);
                } catch (NullPointerException n) {
                    input = "";
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
                }
                if(input.matches("(\\d+),(\\d+)")) {
                    String[] regex = input.split(",");
                    getAlgorithm().getGraph().removeEdge(Integer.parseInt(regex[0]),Integer.parseInt(regex[1]));
                    rePaint();
                }
            }
        });

        panel.add(backFromEdit);
        backFromEdit.addActionListener(e -> cl.show(cards, MAINPANEL));
        cards.add(panel, EDITGRAPHPANEL);
    }
}
