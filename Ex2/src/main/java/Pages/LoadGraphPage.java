package Pages;

import main.Algorithm;
import main.Graph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoadGraphPage extends Page {
    JButton chooseFileLoad = new JButton("Choose a file ");
    JButton insertNameLoad = new JButton("Insert file name ");
    JButton backFromLoad = new JButton("↩");

    public LoadGraphPage() {
        super();
        panel.add(chooseFileLoad);
        chooseFileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON File", "json");
                fc.setFileFilter(filter);
                int returnVal = fc.showOpenDialog(panel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    algorithm.load(fc.getSelectedFile().getPath());
//                    draw.setGraph((Graph)algorithm.getGraph());
//                    draw.erase(draw.getGraphics());
//                    draw.repaint();
                    rePaint();
                }
            }
        });
        panel.add(insertNameLoad);
        insertNameLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                try {
                    name = JOptionPane.showInputDialog(panel, "Insert File Name", null);
                } catch (NullPointerException n) {
                    name = "";
                }

                //loadPanel.add(new JTextField("TextField", 20));
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
        panel.add(backFromLoad);
        backFromLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cl.show(cards, MAINPANEL);
            }
        });



        cards.add(panel, LOADGRAPHPANEL);
    }
}
