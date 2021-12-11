package Pages;

import main.Algorithm;
import main.Graph;

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


        ///////////////add to the main the 4 important button////////////
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

        saveGraph.addActionListener(e -> {
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
        });

        editGraph.addActionListener(e -> cl.show(cards, EDITGRAPHPANEL));

        algorithms.addActionListener(e -> cl.show(cards, ALGORITHMSPANEL));

        pane.add(cards, BorderLayout.CENTER);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // choose x will close the program

        addComponentToPane(frame.getContentPane()); //Create and set up the content pane.

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        //JLayeredPane layeredPane = new JLayeredPane();
        //layeredPane.add(cards,BorderLayout.CENTER);
        //layeredPane.add(draw.getContentPane());
        //frame.getContentPane().add(layeredPane);
    }



    public static void main(String[] args) {
        Algorithm algorithm = new Algorithm();

        //MainGraphPage main = new MainGraphPage();
        //main.createAndShowGUI();

    }
}

