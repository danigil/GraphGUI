package Pages;

import main.Algorithm;
import main.Graph;

import javax.swing.*;
import java.awt.*;

/** a main page class that defines shared variables and structure between all the different page classes.
 *  all page classes inherit from this class.
 */
public class Page {
    JPanel panel;//basic page structure,every page has its own panel, and a main shared panel called cards.
    protected static JPanel cards;//cards panel uses card layout to switch between different panels at runtime.

    /*
    Strings that are used by the cards panel to identify different panels and know which panel to switch to,depending on the identifier string.
     */
    protected final static String MAINPANEL = "MAINPANEL";
    protected final static String LOADGRAPHPANEL = "LOADGRAPHPANEL";
    protected final static String EDITGRAPHPANEL = "EDITGRAPHPANEL";
    protected final static String ALGORITHMSPANEL = "ALGORITHMSPANEL";


    protected static CardLayout cl=new CardLayout();
    //shared JFileChooser which is used in a couple of the page classes.
    protected final static JFileChooser fc = new JFileChooser();
    //shared algorithm object.
    protected final static Algorithm algorithm = new Algorithm(new Graph());

    final static Draw draw = new Draw((Graph) getAlgorithm().getGraph());

    //constructor for the algorithm,edit,load pages.
    public Page() {
        panel=new JPanel();
    }

    //constructor for the mainpage class
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

    //function that repaints the graph.
    protected void rePaint(){
        draw.setGraph((Graph) getAlgorithm().getGraph());
        draw.erase(draw.getGraphics());
        draw.repaint();
    }
}
