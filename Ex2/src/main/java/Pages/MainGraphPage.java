package Pages;

import main.Algorithm;
import main.Graph;
import main.Node;
import main.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/** This page is the main page which contains the "outer shell" frame that switches between the other 3 classes(Jpanels).*/
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

        //Create and set up the frame.
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel();

        ///////////////add to the main.main the 4 page buttons////////////
        main.add(loadGraph);
        main.add(saveGraph);
        main.add(editGraph);
        main.add(algorithms);

        //Create the panel that contains the "cards".
        //JPanel cards = super.panel;
        panel.add(main, MAINPANEL);

        //instantiate the other three page classes
        new AlgorithmsGraphPage();
        new LoadGraphPage();
        new EditGraphPage();

        //set the loadGraph button to change the panel to the LoadGraphPage panel
        loadGraph.addActionListener(e -> cl.show(cards, LOADGRAPHPANEL));

        //set the saveGraph button to prompt user to input name for saved json file, and then save it in case the name is valid.
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

        //set the editGraph button to change the panel to the EditGraphPage panel
        editGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, EDITGRAPHPANEL);
            }
        });

        //set the algorithmGraph button to change the panel to the AlgorithmGraphPage panel
        algorithms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, ALGORITHMSPANEL);
            }
        });

        frame.getContentPane().add(cards, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }
}

