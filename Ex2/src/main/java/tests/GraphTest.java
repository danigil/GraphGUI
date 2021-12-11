package tests;

import api.EdgeData;
import api.NodeData;
import main.Edge;
import main.Graph;
import main.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphTest {
    Graph graph;
    Random rand = new Random();

    public static void main(String[] args) {


    }

    @BeforeEach
    void create(){
//
//        int numOfNodes = rand.nextInt(1000);
//        for (int i = 0; i < numOfNodes ; i++) {
//            double x=100 * rand.nextDouble();
//            double y=100 * rand.nextDouble();
//            double weightNode=10 * rand.nextDouble();
//            Node node = new Node(i,new Point3D(x,y,0),weightNode,"",0);
//            for (int j = 0; j < numOfNodes ; j++) {
//                if(j==i)
//                    continue;
//
//                int bit = rand.nextInt(2);
//                if(bit==1){
//                    double weightEdge=10 * rand.nextDouble();
//                    Edge addNew = new Edge(i,j,weightEdge,"",0);
//                    node.getEdges().put(j,addNew);
//                }
//            }
//            graph.getNodes().put(i,node);
//        }

        graph=Graph.generateRandomGraph(10);
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
    void testRemoveNode(){
        int toBeRemoved = rand.nextInt(graph.nodeSize());
        NodeData getToBeRemoved = graph.getNode(toBeRemoved);
        NodeData removed = graph.removeNode(toBeRemoved);

        int x=5;

        assertEquals(getToBeRemoved,removed);

    }
}
