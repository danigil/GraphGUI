package Pages;

import api.NodeData;
import main.Algorithm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/** This page has 5 buttons, each corresponding to running a different algorithm on the current open graph. */
public class AlgorithmsGraphPage extends Page{
    JButton isConnected = new JButton("isConnected ");
    JButton shortestPathDist = new JButton("shortestPathDist ");
    JButton shortestPath = new JButton("shortestPath ");
    JButton center = new JButton("center ");
    JButton tsp = new JButton("tsp ");
    JButton backFromAlgorithms = new JButton("↩");

    public AlgorithmsGraphPage() {
        super();

        //add isConnected button and its listener.
        panel.add(isConnected);
        isConnected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(algorithm.isConnected())
                    JOptionPane.showMessageDialog(panel, "This is a connected graph! (TRUE)");
                else
                    JOptionPane.showMessageDialog(panel, "This is not a connected graph! (FALSE)");
            }
        });

        //add shortestPathDist button and its listener.
        panel.add(shortestPathDist);
        shortestPathDist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                // if it is run the algorithm and show a new dialog that contains the returned data.
                String input="";
                String[] regex;
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter src key(id), dest key\nExample: 3,2", null);
                } catch (NullPointerException n) {
                    input = "";
                }
                if(input.matches("(\\d+),(\\d+)")) {
                    regex = input.split(",");
                    Double dist = algorithm.shortestPathDist(Integer.parseInt(regex[0]),Integer.parseInt(regex[1]));
                    if(dist!=-1)
                        JOptionPane.showMessageDialog(panel,"Shortest distance between "+ Integer.parseInt(regex[0])+","+Integer.parseInt(regex[1])+": "+ dist );
                    else
                        JOptionPane.showMessageDialog(panel,"There is no path between "+ Integer.parseInt(regex[0])+","+Integer.parseInt(regex[1]));
                }
                else
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
            }
        });

        //add shortestPath button and its listener.
        panel.add(shortestPath);
        shortestPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                // if it is run the algorithm and show a new dialog that contains the returned data.
                String input="";
                String[] regex;
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter src key(id), dest key\nExample: 3,2", null);
                } catch (NullPointerException n) {
                    input = "";

                }
                if(input.matches("(\\d+),(\\d+)")) {
                    regex = input.split(",");
                    String ret = Algorithm.printList(algorithm.shortestPath(Integer.parseInt(regex[0]),Integer.parseInt(regex[1])));
                    if(!ret.equals(""))
                        JOptionPane.showMessageDialog(panel, ret);
                    else
                        JOptionPane.showMessageDialog(panel, "There is no path between "+ Integer.parseInt(regex[0])+","+Integer.parseInt(regex[1]));
                }
                else
                    JOptionPane.showMessageDialog(panel, "Invalid input!");


            }
        });

        //add center button and its listener.
        panel.add(center);
        center.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NodeData center=algorithm.center();
                if(center!=null)
                    JOptionPane.showMessageDialog(panel, "The center node is: N"+center.getKey());
                else
                    JOptionPane.showMessageDialog(panel, "There is no center node.");
            }
        });

        //add tsp button and its listener.
        panel.add(tsp);
        tsp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input="";
                String[] regex;
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter 4 node keys.\nExample: 3,2,15,1", null);
                } catch (NullPointerException n) {
                    input = "";

                }
                if(input.matches("(\\d+),(\\d+),(\\d+),(\\d+)")) {
                    regex = input.split(",");
                    List<NodeData> cities = new LinkedList<>();
                    cities.add(algorithm.getGraph().getNode(Integer.parseInt(regex[0])));
                    cities.add(algorithm.getGraph().getNode(Integer.parseInt(regex[1])));
                    cities.add(algorithm.getGraph().getNode(Integer.parseInt(regex[2])));
                    cities.add(algorithm.getGraph().getNode(Integer.parseInt(regex[3])));
                    JOptionPane.showMessageDialog(panel, "the tsp route is: "+Algorithm.printList(algorithm.tsp(cities)));
                }
                else
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
            }
        });

        //add back to main menu button and its listener.
        panel.add(backFromAlgorithms);
        backFromAlgorithms.addActionListener(e -> cl.show(cards, MAINPANEL));

        //add string identifier for the cards panel in the superclass,used for switching between panels.
        cards.add(panel, ALGORITHMSPANEL);
    }
}
