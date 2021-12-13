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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Draw extends JFrame{
    Graph graph;
    int width=1000;
    int height=700;
    Point3D points[];
    ArrayList<Point3D> point;
    public Draw(Graph graph){
        //setTitle("Drawing a Circle");
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
                erase(getGraphics());
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

        panel.addComponentListener(l);
    }

    private void findScale(){
        if(graph.getNodes()!=null && !graph.getNodes().isEmpty()) {
            points=new Point3D[graph.nodeSize()];
            point=new ArrayList<>(graph.nodeSize());
            Iterator<NodeData> iterator = graph.nodeIter();
            double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
            while (iterator.hasNext()) {
                Node n = (Node) iterator.next();
                double nX = n.getLocation().x();
                double nY = n.getLocation().y();
                if (nX < minX) {
                    minX = nX;
                }

                if (nX > maxX) {
                    maxX = nX;
                }

                if (nY < minY) {
                    minY = nY;
                }

                if (nY > maxY) {
                    maxY = nY;
                }
            }

            Node leftCorner = new Node(graph.nodeSize() + 1, new Point3D(minX, minY, 0), 0, "", 0);
            Node rightCorner = new Node(graph.nodeSize() + 2, new Point3D(maxX, maxY, 0), 0, "", 0);
            Scale scale = new Scale(width, height);
            iterator = graph.nodeIter();
            while (iterator.hasNext()) {
                Node n = (Node) iterator.next();
                //n.setLocation(scale.scalePoint3D(n.getLocation(), leftCorner.getLocation(), rightCorner.getLocation()));
                points[n.getKey()]=scale.scalePoint3D(n.getLocation(), leftCorner.getLocation(), rightCorner.getLocation());
            }


        }
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void erase(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(this.getBackground());
        g2d.fillRect(0,0,width,height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Scale scale = new Scale(width, height);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circleShape;

        if(graph.nodeSize()==1){
            NodeData current = graph.nodeIter().next();
            g2d.fill(new Ellipse2D.Double(width/2, height/2, 5, 5));
            g.setColor(Color.BLACK);
            g2d.drawString("N"+current.getKey(),width/2,height/2-10);
            return;
        }

        //findScale();

        g.setColor(Color.BLACK);
        Iterator<EdgeData> iteratorEdges=graph.edgeIter();
        while (iteratorEdges.hasNext()){
            EdgeData current = iteratorEdges.next();
            NodeData src = graph.getNode(current.getSrc());
            NodeData dest = graph.getNode(current.getDest());
            //Line2D.Double line = new Line2D.Double(points[src.getKey()].x(),points[src.getKey()].y(),points[dest.getKey()].x(),points[dest.getKey()].y());
            Point3D srcScale = scale.scalePoint3D(src.getLocation(),graph.getLeftCorner(),graph.getRightCorner());
            Point3D destScale = scale.scalePoint3D(dest.getLocation(),graph.getLeftCorner(),graph.getRightCorner());
            Line2D.Double line = new Line2D.Double(srcScale.x(),srcScale.y(),destScale.x(),destScale.y());
            g2d.draw(line);


            Polygon arrowHead = new Polygon();
            arrowHead.addPoint( 0,3);
            arrowHead.addPoint( -3, -3);
            arrowHead.addPoint( 3,-3);

            //g2d.drawString(Float.toString((float) current.getWeight()),(int)((line.x2+line.x1)/2),(int)((line.y2+line.y1)/2));


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

        Iterator<NodeData> iterator = graph.nodeIter();
        while(iterator.hasNext()) {
            g.setColor(Color.RED);

            NodeData current = iterator.next();
            Point3D currentScale = scale.scalePoint3D(current.getLocation(),graph.getLeftCorner(),graph.getRightCorner());
            circleShape = new Ellipse2D.Double(currentScale.x()-2.5, currentScale.y()-2.5, 5, 5);
            g2d.fill(circleShape);

            //g2d.fillRect(points[current.getKey()].x(),points[current.getKey()].y(),5,5);

            g.setColor(Color.BLACK);
            g2d.drawString("N"+current.getKey(),(int)Math.ceil(currentScale.x()),(int)Math.ceil(currentScale.y()-10));
        }




    }

}
