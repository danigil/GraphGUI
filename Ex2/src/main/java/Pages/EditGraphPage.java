package Pages;

import main.Node;
import main.Point3D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This page has 4 buttons, each corresponding to editing the current graph in 4 different ways. */
public class EditGraphPage extends Page{
    JButton addNode = new JButton("Add node ");
    JButton deleteNode = new JButton("Delete node ");
    JButton addEdge = new JButton("Add edge ");
    JButton deleteEdge = new JButton("Delete edge ");
    JButton backFromEdit = new JButton("↩");

    public EditGraphPage(){

        //add addNode button and its listener.
        panel.add(addNode);
        addNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                //if it is run the command and repaint the graph,if it isn't correct, run the command with default values.
                String input = "";
                double x = 0;
                double y = 0;
                double weight = 1;
                String info = "";
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter Node Values: x,y,weight,info\nExample: 5.3,7.1,12,Hello!", null);
                } catch (NullPointerException n) {
                    input = "";
                }

                if (input.matches("(\\d+),(\\d+),(\\d+),(\\w+)")) {
                    String[] regex = input.split(",");
                    x = Double.parseDouble(regex[0]);
                    y = Double.parseDouble(regex[1]);
                    weight = Double.parseDouble(regex[2]);
                    info = regex[3];
                }

                algorithm.getGraph().addNode(new Node(algorithm.getGraph().nodeSize(), new Point3D(x, y, 0), weight, info, 0));

                rePaint();
            }});

        //add deleteNode button and its listener.
        panel.add(deleteNode);
        deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                //if it is run the command and repaint the graph.
                String input="";
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter node key(id)", null);
                } catch (NullPointerException n) {
                    input = "";
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
                }
                if(input.matches("\\d+")) {
                    getAlgorithm().getGraph().removeNode(Integer.parseInt(input));

                    rePaint();
                }

            }
        });

        //add addEdge button and its listener.
        panel.add(addEdge);
        addEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                //if it is run the command and repaint the graph.
                String input="";
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter src key(id), dest key, weight\nExample: 5,7,1.5", null);
                } catch (NullPointerException n) {
                    input = "";
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
                }
                if(input.matches("(\\d+),(\\d+),(\\d+)")) {
                    String[] regex = input.split(",");
                    getAlgorithm().getGraph().connect(Integer.parseInt(regex[0]), Integer.parseInt(regex[1]), Double.parseDouble(regex[2]));
                    rePaint();
                }
                }});

        //add deleteEdge button and its listener.
        panel.add(deleteEdge);
        deleteEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                //if it is run the command and repaint the graph.
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

        //add back to main menu button and its listener.
        panel.add(backFromEdit);
        backFromEdit.addActionListener(e -> cl.show(cards, MAINPANEL));

        //add string identifier for the cards panel in the superclass,used for switching between panels.
        cards.add(panel, EDITGRAPHPANEL);
    }
}
