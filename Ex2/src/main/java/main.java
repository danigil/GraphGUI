import Pages.MainGraphPage;
import api.NodeData;
import main.*;

import java.util.LinkedList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        Node u = new Node(0,new Point3D(3,3,0),0,"U",0);
        Node v = new Node(1,new Point3D(5,3,0),0,"V",0);
        graph.addNode(u);
        graph.connect(0,3,0);
        graph.connect(0,1,0);
        graph.addNode(v);
        graph.connect(1,2,0);
        Node y = new Node(2,new Point3D(5,6,0),0,"Y",0);
        graph.addNode(y);
        graph.connect(2,3,0);
        Node x = new Node(3,new Point3D(3,6,0),0,"X",0);
        graph.addNode(x);
        graph.connect(3,1,0);
        Node w = new Node(4,new Point3D(7,3,0),0,"W",0);
        graph.addNode(w);
        graph.connect(4,5,0);
        graph.connect(4,2,0);
        Node z = new Node(5,new Point3D(7,6,0),0,"Z",0);
        graph.addNode(z);
        graph.connect(5,5,0);

        Graph graph2 = new Graph();
        Node n0 = new Node(0,new Point3D(10,10,0),0,"N1",0);
        Node n1 = new Node(1,new Point3D(5,3,0),0,"N2",0);
        Node n2 = new Node(2,new Point3D(7,7,0),0,"N3",0);
        graph2.addNode(n0);
        graph2.addNode(n1);
        graph2.addNode(n2);
        graph2.connect(0,1,0);
        //graph2.connect(1,2,0);
        graph2.connect(2,0,0);
        graph2.connect(1,2,0);



        MainGraphPage mainGraphPage = new MainGraphPage();
        mainGraphPage.createAndShowGUI();
        mainGraphPage.changeGraph(graph2);


        Algorithm algorithm = new Algorithm();
        algorithm.init(graph2);
        List<NodeData> list = algorithm.dijkstra(n2.getKey(),n1.getKey());
        Algorithm.printList(list);
        //System.out.println(algorithm.isConnected());




        //algorithm.save("file.json");
        //algorithm=new Algorithm();
        //algorithm.init(Graph.generateRandomGraph(10));
        //algorithm.load("file.json");
        //int x=5;


    }

}
