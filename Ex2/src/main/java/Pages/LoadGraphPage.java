package Pages;

import main.Algorithm;
import main.Graph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/** This page allows loading json files and showing them as graphs, there are two options: loading through a file chooser, or inputting a file path/name. */
public class LoadGraphPage extends Page {
    JButton chooseFileLoad = new JButton("Choose a file ");
    JButton insertNameLoad = new JButton("Insert file name ");
    JButton backFromLoad = new JButton("↩");

    public LoadGraphPage() {
        super();

        //add addNode button and its listener.
        panel.add(chooseFileLoad);
        chooseFileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //use the static FileChooser object from the superclass to allow the user to choose a json file in his system.
                //if a valid file was chosen, load the json file.

                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON File", "json");
                fc.setFileFilter(filter);

                int returnVal = fc.showOpenDialog(panel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    algorithm.load(fc.getSelectedFile().getPath());
                    rePaint();
                }
            }
        });

        //add addNode button and its listener.
        panel.add(insertNameLoad);
        insertNameLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display an input dialog and later check if the input is correct,
                //if it is load the graph from the json file.
                String name;
                try {
                    name = JOptionPane.showInputDialog(panel, "Insert File Name", null);
                } catch (NullPointerException n) {
                    name = "";
                }

                if (name != null) {
                    if ((name.matches("(.*).json"))) {
                        algorithm.load(name);
                        rePaint();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Invalid input!");
                    }
                }
            }
        });

        //add back to main menu button and its listener.
        panel.add(backFromLoad);
        backFromLoad.addActionListener(e -> cl.show(cards, MAINPANEL));

        //add string identifier for the cards panel in the superclass,used for switching between panels.
        cards.add(panel, LOADGRAPHPANEL);
    }
}
