package tests;

import api.EdgeData;
import api.NodeData;
import main.Edge;
import main.Graph;
import main.Node;
import main.Point3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    Graph graph;
    Random rand = new Random();

    @BeforeEach
    void create(){
        graph=Graph.generateRandomGraph(10);
        if(graph.nodeSize()<1){
            graph=new Graph();
            graph.addNode(new Node(0,new Point3D(0,0,0),0,"",0));
            graph.addNode(new Node(1,new Point3D(0,0,0),0,"",0));
        }

    }

    @Test
    void testRemoveNode(){
        int toBeRemoved = rand.nextInt(graph.nodeSize());
        NodeData getToBeRemoved = graph.getNode(toBeRemoved);
        NodeData removed = graph.removeNode(toBeRemoved);

        assertEquals(getToBeRemoved,removed);
    }

    @Test
    void testGetNode(){
        int toBeGet = rand.nextInt(graph.nodeSize());
        assertEquals(graph.getNodes().get(toBeGet),graph.getNode(toBeGet));
    }

    @Test
    void testGetEdge(){
        int src = rand.nextInt(graph.nodeSize());
        int dest = rand.nextInt(graph.nodeSize());
        assertEquals(graph.getNodes().get(src).getEdges().get(dest),graph.getEdge(src,dest));
    }

    @Test
    void testAddNode(){
        Node toAdd=new Node(graph.nodeSize(),new Point3D(1,2,3),15,"new",0);
        graph.addNode(toAdd);
        assertEquals(graph.getNode(toAdd.getKey()),toAdd);
    }

    @Test
    void testConnect(){
        int src = rand.nextInt(graph.nodeSize());
        int dest = rand.nextInt(graph.nodeSize());
        double weight = rand.nextInt(1000);
        graph.connect(src,dest,weight);
        assertTrue(graph.getEdge(src,dest).getSrc()==src && graph.getEdge(src,dest).getDest()==dest && graph.getEdge(src,dest).getWeight()==weight);
    }

    @Test
    void testNodeIterator(){
        Hashtable table = graph.getNodes();
        int[] expected = new int[table.size()];

        for (int i = 0; i < expected.length ; i++) {
            expected[i]=1;
        }

        int iteratorSize = 0;
        int[] actual = new int[table.size()];
        Iterator iterator = graph.nodeIter();
        while (iterator.hasNext()){
            iteratorSize++;
            Node n = (Node) iterator.next();
            actual[n.getKey()]=1;
        }
        assertEquals(table.size(),iteratorSize);
        assertArrayEquals(actual,expected);
    }

    @Test
    void testEdgeIterator(){
        int iteratorSizeActual=0;
        int iteratorSizeExpected=0;

        Iterator<EdgeData> edgeIterator = graph.edgeIter();
        int[][] graphArrayExpected = new int[graph.nodeSize()][graph.nodeSize()];

        while (edgeIterator.hasNext()) {
            iteratorSizeExpected++;
            EdgeData currentExpected = edgeIterator.next();
            graphArrayExpected[currentExpected.getSrc()][currentExpected.getDest()] = 1;
        }


        int[][] graphArrayActual = new int[graph.nodeSize()][graph.nodeSize()];
        Iterator<NodeData> nodeIterator = graph.nodeIter();
        while(nodeIterator.hasNext()){
            Iterator<EdgeData> specificEdgeIterator = graph.edgeIter(nodeIterator.next().getKey());
            while (specificEdgeIterator.hasNext()){
                iteratorSizeActual++;
                Edge currentActual = (Edge)specificEdgeIterator.next();
                graphArrayActual[currentActual.getSrc()][currentActual.getDest()]=1;
            }
        }

        assertEquals(iteratorSizeExpected,iteratorSizeActual);
        assertArrayEquals(graphArrayExpected,graphArrayActual);
    }

    @Test
    void testSpecificEdgeIterator(){
        int randNum=rand.nextInt(graph.nodeSize());
        Iterator iterator = graph.edgeIter(randNum);
        Hashtable<Integer,Edge> actual = new Hashtable<>();
        int actualSize=0;
        while (iterator.hasNext()){
            EdgeData e = (EdgeData) iterator.next();
            actual.put(e.getDest(), (Edge) e);
            actualSize++;
        }

        assertEquals(graph.getNodes().get(randNum).getEdges().size(),actualSize);
        assertEquals(((Node)graph.getNode(randNum)).getEdges(),actual);

    }

    @Test
    void testRemoveEdge(){
        int src = rand.nextInt(graph.nodeSize());
        int dest = rand.nextInt(graph.nodeSize());
        graph.connect(src,dest,15);
        Edge edge = (Edge)graph.removeEdge(src,dest);
        assertTrue(edge.getSrc()==src && edge.getDest()==dest && edge.getWeight()==15);


    }

}
