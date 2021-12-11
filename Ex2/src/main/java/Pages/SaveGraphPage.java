package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveGraphPage extends Page{
    JButton filePathSave = new JButton("Choose a file ");
    JButton insertNameSave = new JButton("Insert file name ");
    JButton backFromSave = new JButton("↩");

    public SaveGraphPage(){
        super();
        panel.add(filePathSave);
        filePathSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;

                name = JOptionPane.showInputDialog(panel, "Insert File Name", null);
                //String[] parts = name.split("/");
                //name= parts[parts.length-1];

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
        }
        });
        panel.add(insertNameSave);
        insertNameSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                try {
                    name = JOptionPane.showInputDialog(panel, "Insert File Name", null);
                } catch (NullPointerException n) {
                    name = "";
                }

                if (name!=null &&(name.matches("(.*).json"))) {
                    //save
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid input!");
                }
            }
        });
        panel.add(backFromSave);
        backFromSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, MAINPANEL);

            }
        });
        cards.add(panel, SAVEGRAPHPANEL);
    }
}
