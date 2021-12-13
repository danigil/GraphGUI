package Pages;

import main.Algorithm;
import main.Graph;
import main.Node;
import main.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGraphPage extends Page{

    /////////////////// used to make the panel/////////////////////////
    //JPanel cards; //a panel that uses CardLayout
    JButton loadGraph = new JButton("Load Graph");
    JButton saveGraph = new JButton("Save Graph");
    JButton editGraph = new JButton("Edit Graph");
    JButton algorithms = new JButton("Algorithms");

    JFrame frame = new JFrame();

    public MainGraphPage(){
        super(new JPanel(new CardLayout()));
    }

    public void addComponentToPane(Container pane) {
        frame.setAlwaysOnTop(true);
        JPanel main = new JPanel();


        ///////////////add to the main.main the 4 important button////////////
        main.add(loadGraph);
        main.add(saveGraph);
        main.add(editGraph);
        main.add(algorithms);

        ///////////add button to each one of important linked button//////////////

        //Create the panel that contains the "cards".
        //JPanel cards = super.panel;
        panel.add(main, MAINPANEL);

        new AlgorithmsGraphPage();
        new LoadGraphPage();
        new SaveGraphPage();
        new EditGraphPage();

        loadGraph.addActionListener(e -> cl.show(cards, LOADGRAPHPANEL));

        saveGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String name;

            name = JOptionPane.showInputDialog(panel, "Insert File Name,or Path", null);

            if (name!=null && (name.matches("(.*).json"))) {
                boolean b =algorithm.save(name);
                if(b)
                    JOptionPane.showMessageDialog(panel, "Graph Saved Successfully!");
                else
                    JOptionPane.showMessageDialog(panel, "ERROR: Graph wasn't saved.");
            } else {
                if(name!=null)
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
            }
        }});

        editGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, EDITGRAPHPANEL);
            }
        });

        algorithms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, ALGORITHMSPANEL);
            }
        });

        pane.add(cards, BorderLayout.CENTER);
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // choose x will close the program

        addComponentToPane(frame.getContentPane()); //Create and set up the content pane.

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main.main(String[] args) {
//
//        Graph graph2 = new Graph();
//        Node n0 = new Node(0,new Point3D(10,10,0),0,"N1",0);
//        Node n1 = new Node(1,new Point3D(5,3,0),0,"N2",0);
//        Node n2 = new Node(2,new Point3D(7,7,0),0,"N3",0);
//        graph2.addNode(n0);
//        graph2.addNode(n1);
//        graph2.addNode(n2);
//        graph2.connect(0,1,0);
//        //graph2.connect(1,2,0);
//        graph2.connect(2,0,0);
//        graph2.connect(1,2,0);
//
//
//
//        MainGraphPage mainGraphPage = new MainGraphPage();
//        mainGraphPage.createAndShowGUI();
//        mainGraphPage.changeGraph(graph2);
//        //mainGraphPage.algorithm.getGraph().removeNode(0);
//    }
}

