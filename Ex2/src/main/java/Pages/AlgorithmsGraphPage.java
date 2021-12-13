package Pages;

import api.NodeData;
import main.Algorithm;
import main.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlgorithmsGraphPage extends Page{

    JButton isConnected = new JButton("isConnected ");
    JButton shortestPathDist = new JButton("shortestPathDist ");
    JButton shortestPath = new JButton("shortestPath ");
    JButton center = new JButton("center ");
    JButton tsp = new JButton("tsp ");
    JButton backFromAlgorithms = new JButton("↩");


    public AlgorithmsGraphPage() {
        super();

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

        panel.add(shortestPathDist);
        shortestPathDist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        panel.add(shortestPath);
        shortestPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input="";
                String[] regex;
                try {
                    input = JOptionPane.showInputDialog(panel, "Enter src key(id), dest key\nExample: 3,2", null);
                } catch (NullPointerException n) {
                    input = "";

                }
                if(input.matches("(\\d+),(\\d+)")) {
                    regex = input.split(",");
                    String ret = Algorithm.printList(algorithm.dijkstra(Integer.parseInt(regex[0]),Integer.parseInt(regex[1])));
                    if(!ret.equals(""))
                        JOptionPane.showMessageDialog(panel, ret);
                    else
                        JOptionPane.showMessageDialog(panel, "There is no path between "+ Integer.parseInt(regex[0])+","+Integer.parseInt(regex[1]));
                }
                else
                    JOptionPane.showMessageDialog(panel, "Invalid input!");


            }
        });

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

        panel.add(tsp);
        tsp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel.add(backFromAlgorithms);
        backFromAlgorithms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, MAINPANEL);
            }
        });
        cards.add(panel, ALGORITHMSPANEL);
    }
}
