package Pages;

import api.EdgeData;
import api.NodeData;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Iterator;


/** This is the class that draws the graph itself, it is a jframe with an overridden paint method.
 *  The graph is drawn by first scaling the points to fit in the screen all in one piece(Using the scale method), then drawing the edges,
 *  and on top of them, the nodes, this jframe rescales the drawing when the frame expands or shrinks in size by using a listener.
 */
public class Draw extends JFrame{

    Graph graph;
    int width=1000;
    int height=700;

    public Draw(Graph graph){
        JPanel panel = new JPanel();

        add(panel);
        setSize(width, height);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.graph=graph;

        ComponentListener l = new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                width=e.getComponent().getWidth();
                height=e.getComponent().getHeight();

                erase(getGraphics());//call to method that erases the screen.

                revalidate();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        };

        panel.addComponentListener(l);//adds window resize listener.
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * erase method erases all the screen's drawings.
     * @param g
     */
    public void erase(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(this.getBackground());
        g2d.fillRect(0,0,width,height);
    }

    /**
     * paint method draws all the edges after rescaling, and then all the nodes on top of them.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Scale scale = new Scale(width, height);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circleShape;

        /*
        in case graph only has one node, draw it in the middle of the screen and stop the method.
         */
        if(graph.nodeSize()==1){
            NodeData current = graph.nodeIter().next();
            g2d.fill(new Ellipse2D.Double(width/2, height/2, 5, 5));
            g.setColor(Color.BLACK);
            g2d.drawString("N"+current.getKey(),width/2,height/2-10);
            return;
        }

        //iterate on all of the edges and draw them one by one.
        g.setColor(Color.BLACK);
        Iterator<EdgeData> iteratorEdges=graph.edgeIter();
        while (iteratorEdges.hasNext()){
            EdgeData current = iteratorEdges.next();

            NodeData src = graph.getNode(current.getSrc());
            NodeData dest = graph.getNode(current.getDest());

            //scaled points of the current edge.
            Point3D srcScale = scale.scalePoint3D(src.getLocation(),graph.getLeftCorner(),graph.getRightCorner());
            Point3D destScale = scale.scalePoint3D(dest.getLocation(),graph.getLeftCorner(),graph.getRightCorner());

            //create a new line and draw it.
            Line2D.Double line = new Line2D.Double(srcScale.x(),srcScale.y(),destScale.x(),destScale.y());
            g2d.draw(line);

            //create an arrowhead for the line.
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint( 0,3);
            arrowHead.addPoint( -3, -3);
            arrowHead.addPoint( 3,-3);

            //draw new arrowhead at line end.
            AffineTransform tx = new AffineTransform();
            tx.setToIdentity();

            double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);

            Point3D vector = new Point3D(line.x2-line.x1,line.y2-line.y1,0);
            Double norm = Math.sqrt(Math.pow(vector.x(),2)+Math.pow(vector.y(),2));
            Point3D normalized = new Point3D(vector.x()/norm,vector.y()/norm,0);

            tx.translate(line.x2-6*normalized.x(), line.y2-6*normalized.y());
            tx.rotate((angle-Math.PI/2d));
            g2d.setTransform(tx);
            g2d.fill(arrowHead);

            g2d.setTransform(new AffineTransform());
        }
        //iterate on all of the nodes and draw them one by one.
        Iterator<NodeData> iterator = graph.nodeIter();
        while(iterator.hasNext()) {
            g.setColor(Color.RED);

            NodeData current = iterator.next();

            //rescale the node.
            Point3D currentScale = scale.scalePoint3D(current.getLocation(),graph.getLeftCorner(),graph.getRightCorner());

            //draw node shape
            circleShape = new Ellipse2D.Double(currentScale.x()-2.5, currentScale.y()-2.5, 5, 5);
            g2d.fill(circleShape);

            //draw node label
            g.setColor(Color.BLACK);
            g2d.drawString("N"+current.getKey(),(int)Math.ceil(currentScale.x()),(int)Math.ceil(currentScale.y()-10));
        }




    }

}
