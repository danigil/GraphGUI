package main;

import api.EdgeData;
import api.NodeData;

import java.util.*;
import java.util.function.Consumer;

public class Graph implements api.DirectedWeightedGraph {

    Hashtable<Integer, Node> Nodes;
    int edgeSize=0;

    public Graph(Hashtable<Integer, Node> nodes) {
        this.Nodes = nodes;
    }

    public Graph(){
        this.Nodes=new Hashtable<>();
    }

    @Override
    public NodeData getNode(int key) {
        return Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return (EdgeData) Nodes.get(src).getEdges().get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        Nodes.put(n.getKey(), (Node) n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(getNode(src)==null)
            return;
        edgeSize++;
        Edge new_edge = new Edge(src,dest,w,"",0);
        Nodes.get(src).getEdges().put(dest,new_edge);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator<NodeData> iterator = new Iterator() {
            Enumeration<Integer> e = Nodes.keys();
            NodeData removed=null;
            @Override
            public boolean hasNext() {
                return e.hasMoreElements() || removed!=null;
            }

            @Override
            public NodeData next() {
                if(removed!=null) {
                    NodeData tmp = removed;
                    removed=null;
                    return tmp;
                }
                else
                    return Nodes.get(e.nextElement());
            }

            @Override
            public void remove() {

            }

            @Override
            public void forEachRemaining(Consumer action) {

            }
        };

        return iterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator<EdgeData> edgeDataIterator = new Iterator() {
            Iterator<NodeData> nodeIterator = nodeIter();
            Iterator<EdgeData> currentEdgeIterator= new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public EdgeData next() {
                    return null;
                }
            };
            EdgeData removed = null;
            LinkedList<Integer> ls=createLS();

            @Override
            public boolean hasNext() {
                if(currentEdgeIterator==null || !currentEdgeIterator.hasNext()){
                    advanceIterLS();
                }
                return currentEdgeIterator.hasNext();
            }

            @Override
            public EdgeData next() {
                if(removed!=null) {
                    EdgeData tmp = removed;
                    removed=null;
                    return tmp;
                }
                return currentEdgeIterator.next();
            }

            @Override
            public void remove() {
                removed=next();
                removeEdge(removed.getSrc(),removed.getDest());
            }

            @Override
            public void forEachRemaining(Consumer action) {
                while (hasNext()){
                    action.accept(next());
                }
            }

            private void advanceIterLS(){
                if(!ls.isEmpty()){
                    currentEdgeIterator=edgeIter(ls.removeFirst());
                }
            }

            private LinkedList createLS(){
                LinkedList<Integer> ls=new LinkedList<>();
                while (nodeIterator.hasNext()){
                    Node n = (Node)nodeIterator.next();
                    if(!n.getEdges().isEmpty()){
                        ls.add(n.getKey());
                    }
                }
                return ls;
            }
        };

        return edgeDataIterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Iterator<EdgeData> iterator = new Iterator() {
            int node_num=node_id;
            Enumeration<Integer> e = Nodes.get(node_num).getEdges().keys();
            @Override
            public boolean hasNext() {
                return e.hasMoreElements();
            }

            @Override
            public Edge next() {
                return (Edge)Nodes.get(node_num).getEdges().get(e.nextElement());
            }
        };
        return iterator;
    }

    @Override
    public NodeData removeNode(int key) {
        Iterator<EdgeData> edgeIterator = edgeIter();
        while (edgeIterator.hasNext()){
            EdgeData current = edgeIterator.next();
            if(current.getDest()==key){
                removeEdge(current.getSrc(),current.getDest());
            }
        }
        return Nodes.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(Nodes.get(src)==null)
            return null;
        return (EdgeData)Nodes.get(src).getEdges().remove(dest);
    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        int size=0;
        Iterator iterator = edgeIter();
        while (iterator.hasNext()){
            iterator.next();
            size++;
        }
        return size;
    }

    @Override
    public int getMC() {
        return 0;
    }

    public Hashtable<Integer, Node> getNodes() {
        return Nodes;
    }

    public static Graph generateRandomGraph(int nodeSIze){
        Graph graph = new Graph();
        Random rand = new Random();
        int numOfNodes = rand.nextInt(nodeSIze);
        for (int i = 0; i < numOfNodes ; i++) {
            double x=100 * rand.nextDouble();
            double y=100 * rand.nextDouble();
            double weightNode=10 * rand.nextDouble();
            Node node = new Node(i,new Point3D(x,y,0),weightNode,"",0);
            for (int j = 0; j < numOfNodes ; j++) {
                if(j==i)
                    continue;

                int bit = rand.nextInt(2);
                if(bit==1){
                    double weightEdge=10 * rand.nextDouble();
                    Edge addNew = new Edge(i,j,weightEdge,"",0);
                    node.getEdges().put(j,addNew);
                    //graph.connect(i,j,weightEdge);
                }
            }
            graph.getNodes().put(i,node);
        }

        return graph;
    }

    public static Graph graphTranspose(Graph graph){
        Graph ret = new Graph();

        Iterator nodeIterator = graph.nodeIter();
        while (nodeIterator.hasNext()){
            Node insert = ((Node)nodeIterator.next()).clone();
            insert.setEdges(new Hashtable<>());
            insert.setTag(Node.UNVISITED);
            ret.addNode(insert);
        }

        Iterator edgeIterator = graph.edgeIter();
        while (edgeIterator.hasNext()){
            Edge insertTranspose = ((Edge)edgeIterator.next());
            ret.connect(insertTranspose.getDest(),insertTranspose.getSrc(),insertTranspose.getWeight());
        }

        return ret;
    }
}
