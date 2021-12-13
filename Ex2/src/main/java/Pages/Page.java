package Pages;

import main.Algorithm;
import main.Graph;

import javax.swing.*;
import java.awt.*;

public class Page {
    JPanel panel;
    protected static JPanel cards;
    protected final static String MAINPANEL = "MAINPANEL";
    protected final static String LOADGRAPHPANEL = "LOADGRAPHPANEL";
    protected final static String SAVEGRAPHPANEL = "SAVEGRAPHPANEL";
    protected final static String EDITGRAPHPANEL = "EDITGRAPHPANEL";
    protected final static String ALGORITHMSPANEL = "ALGORITHMSPANEL";

    protected static CardLayout cl=new CardLayout();
    protected final static JFileChooser fc = new JFileChooser();
    protected final static Algorithm algorithm = new Algorithm(new Graph());
    //protected static Graph copyGraph;

    final static Draw draw = new Draw((Graph) getAlgorithm().getGraph());

    public Page() {
        panel=new JPanel();
    }

    public Page(JPanel panel) {
        this.panel=panel;
        cards=panel;
        cl = (CardLayout) (panel.getLayout());
    }

    public static Algorithm getAlgorithm() {
        return algorithm;
    }

    public void changeGraph(Graph graph){
        algorithm.init(graph);
        rePaint();
    }

    protected void rePaint(){
        draw.setGraph((Graph) getAlgorithm().getGraph());
        draw.erase(draw.getGraphics());
        draw.repaint();
    }
}
