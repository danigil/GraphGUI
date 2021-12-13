package main;

import api.EdgeData;
import api.NodeData;

import java.util.*;
import java.util.function.Consumer;

public class Graph implements api.DirectedWeightedGraph {

    private Hashtable<Integer, Node> Nodes;
    private Point3D leftCorner,rightCorner;
    private Queue<Integer> removedKeys;
    int edgeSize=0;

    public Graph(Hashtable<Integer, Node> nodes) {
        this.Nodes = nodes;
        leftCorner=new Point3D(Double.MAX_VALUE,Double.MAX_VALUE,0);
        rightCorner=new Point3D(Double.MIN_VALUE,Double.MIN_VALUE,0);
        removedKeys=new LinkedList<>();
    }

    public Graph(){
        this.Nodes=new Hashtable<>();
        leftCorner=new Point3D(Double.MAX_VALUE,Double.MAX_VALUE,0);
        rightCorner=new Point3D(Double.MIN_VALUE,Double.MIN_VALUE,0);
        removedKeys=new LinkedList<>();
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
        double x=n.getLocation().x();
        double y=n.getLocation().y();
        if(x<leftCorner.x())
            leftCorner.setX(x);

        if(x>rightCorner.x())
            rightCorner.setX(x);

        if(y<leftCorner.y())
            leftCorner.setY(y);

        if(y>rightCorner.y())
            rightCorner.setY(y);


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
            Queue<NodeData> notHiddens=createQ();
            Enumeration<Integer> e = Nodes.keys();
            Node removed=null;
            @Override
            public boolean hasNext() {
                return !notHiddens.isEmpty() || removed!=null;
            }

            @Override
            public NodeData next() {
                if(removed!=null) {
                    NodeData tmp = removed;
                    removed=null;
                    return tmp;
                }
                else
                    return notHiddens.poll();
            }

            @Override
            public void remove() {

            }

            @Override
            public void forEachRemaining(Consumer action) {

            }

            public Queue<NodeData> createQ(){
                Queue<NodeData> ret = new LinkedList<>();
                Enumeration<Node> enumeration = Nodes.elements();
                while (enumeration.hasMoreElements()){
                    Node current = enumeration.nextElement();
                    if(current.getTag()!=Node.HIDDEN)
                        ret.add(current);
                }

                return ret;
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
        if(Nodes.get(key)==null)
            return null;

        Iterator<EdgeData> edgeIterator = edgeIter();
        while (edgeIterator.hasNext()){
            EdgeData current = edgeIterator.next();
            if(current.getDest()==key){
                removeEdge(current.getSrc(),current.getDest());
            }
        }
//        edgeIterator = edgeIter();
//        while (edgeIterator.hasNext()){
//            Edge current = (Edge)edgeIterator.next();
//            if(current.getSrc()==key+1){
//                current.setSrc(key);
//            }
//
//            if(current.getDest()==key+1){
//                current.setDest(key);
//            }
//        }

        Node remove=Nodes.remove(key);
        //Nodes.put(key,new Node(key,null,-1,null,Node.HIDDEN));
        shift(key);
        //removedKeys.add(key);

        return remove;
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

    public Point3D getLeftCorner() {
        return leftCorner;
    }

    public Point3D getRightCorner() {
        return rightCorner;
    }

    public Queue getRemovedKeys(){
        return removedKeys;
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
                if(bit==1 && node.getEdges().size()<10){
                    double weightEdge=10 * rand.nextDouble();
                    Edge addNew = new Edge(i,j,weightEdge,"",0);
                    node.getEdges().put(j,addNew);
                    //graph.connect(i,j,weightEdge);
                }
            }
            //graph.getNodes().put(i,node);
            graph.addNode(node);
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

    public int getSizeNoHiddens(){
        return Nodes.size()-removedKeys.size();
    }

    private void shift(int pos){
        if(pos==nodeSize())
            return;
        Iterator<EdgeData> edgeIterator = edgeIter();
        while (edgeIterator.hasNext()){
            Edge current = (Edge)edgeIterator.next();

            if(current.getSrc()>=pos+1)
                current.setSrc(current.getSrc()-1);

            if(current.getDest()>=pos+1)
                current.setDest(current.getDest()-1);
        }

        Nodes.put(pos, (Node) this.getNode(pos+1));
        for (int i = pos+1; i < this.nodeSize(); i++) {
            Node temp = (Node)this.getNode(i);
            temp.setKey(i-1);
            Nodes.replace(i-1,temp);
        }
        Nodes.remove(this.nodeSize()-1);
    }
}
