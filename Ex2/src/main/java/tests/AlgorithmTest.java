package tests;

import api.DirectedWeightedGraph;
import api.NodeData;
import main.Algorithm;
import main.Graph;
import main.Node;
import main.Point3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.builder.EqualsBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {
    Algorithm algorithm;
    @BeforeEach
    void create(){
        algorithm = new Algorithm();
        algorithm.init(Graph.generateRandomGraph(1000));
    }

    @Test
    void initGetGraphTest(){
        Graph graph = new Graph();
        Algorithm initAlgorithm = new Algorithm();
        initAlgorithm.init(graph);

        assertEquals(initAlgorithm.getGraph(),graph);
    }

    @Test
    void copyTest(){
        //assertTrue(EqualsBuilder.reflectionEquals(((Graph)algorithm.copy()).getNodes(),((Graph)algorithm.getGraph()).getNodes()));
    }

    @Test
    void isConnectedTest(){
        Graph graph = new Graph();

        graph.addNode(new Node(0,new Point3D(0,0,0),0,"",0));
        graph.addNode(new Node(1,new Point3D(1,1,0),0,"",0));

        algorithm.init(graph);

        assertFalse(algorithm.isConnected());

        graph.connect(0,1,2);
        graph.connect(1,0,3);
        assertTrue(algorithm.isConnected());

        algorithm.load("G1.json");
        assertTrue(algorithm.isConnected());

        DirectedWeightedGraph g1json = algorithm.getGraph();
        g1json.removeEdge(0,1);
        g1json.removeEdge(1,0);
        g1json.removeEdge(9,10);
        g1json.removeEdge(10,9);

        assertFalse(algorithm.isConnected());

        algorithm.load("G2.json");
        assertTrue(algorithm.isConnected());

        algorithm.load("G3.json");
        DirectedWeightedGraph g3json = algorithm.getGraph();
        assertTrue(algorithm.isConnected());
        g3json.removeEdge(40,39);
        assertFalse(algorithm.isConnected());


    }

    @Test
    void shortestPathDistTest(){
        algorithm.load("G1.json");
        assertEquals(algorithm.shortestPathDist(3,2), 1.440561778177153);
        assertNotEquals(algorithm.shortestPathDist(3,2), 6.675);

        algorithm.load("G2.json");
        assertEquals(algorithm.shortestPathDist(6,7), 1.237565124536135);
    }

    @Test
    void shortestPathTest(){
        algorithm.load("G1.json");
        assertEquals(Algorithm.printList(algorithm.shortestPath(1,3)),"N1 -> N2 -> N3");
        assertEquals(Algorithm.printList(algorithm.shortestPath(3,12)),"N3 -> N2 -> N6 -> N7 -> N8 -> N9 -> N10 -> N11 -> N12");

        algorithm.load("G2.json");
        assertEquals(Algorithm.printList(algorithm.shortestPath(29,15)),"N29 -> N28 -> N4 -> N3 -> N2 -> N1 -> N0 -> N16 -> N15");
    }

    @Test
    void centerTest(){
        algorithm.load("G1.json");
        assertEquals(algorithm.center(),algorithm.getGraph().getNode(8));

        algorithm.load("G2.json");
        assertEquals(algorithm.center(),algorithm.getGraph().getNode(0));
    }

    @Test
    void loadTest(){
        algorithm.load("G1.json");
        assertTrue(algorithm.getGraph().nodeSize()==17);
        assertTrue(algorithm.getGraph().edgeSize()==36);
    }

    @Test
    void saveTest(){
        algorithm.load("G1.json");
        algorithm.save("G1NEW.json");
        Algorithm compare = new Algorithm();
        compare.load("G1NEW.json");

        assertEquals(compare.getGraph().nodeSize(),algorithm.getGraph().nodeSize());
        assertEquals(compare.getGraph().edgeSize(),algorithm.getGraph().edgeSize());
    }


}
