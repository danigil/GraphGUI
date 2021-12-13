package main;

import Pages.MainGraphPage;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class Algorithm implements api.DirectedWeightedGraphAlgorithms {
    Graph graph;

    public Algorithm() {
//        if(graph==null){
//            init(new Graph());
//        }
    }

    public Algorithm(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Hashtable<Integer,Node> hashtable = new Hashtable<>();

        Iterator nodeIter = graph.nodeIter();

        while (nodeIter.hasNext()){
            Node currentNode = ((Node)nodeIter.next());
            hashtable.put(currentNode.getKey(),currentNode.clone());
        }

        DirectedWeightedGraph ret = new Graph(hashtable);
        return ret;
    }

    @Override
    public boolean isConnected() {
        /**
         * Main idea: run dfs on original graph,if some node was unvisited,return false, run dfs on transpose graph,
         * if some node was unvisited,return false, else return true;
         */
        if(graph.nodeSize()==1)
            return true;

        Iterator<NodeData> iterator = graph.nodeIter();

        while (iterator.hasNext()){
            iterator.next().setTag(Node.UNVISITED);
        }

        iterator = graph.nodeIter();
        NodeData n = iterator.next();

        dfs(n,graph);

        iterator = graph.nodeIter();
        while (iterator.hasNext()){
            NodeData current = iterator.next();
            if(current.getTag()==Node.UNVISITED){
                return false;
            }
        }

        Graph transpose = Graph.graphTranspose(graph);

        iterator = transpose.nodeIter();
        while (iterator.hasNext()){
            iterator.next().setTag(Node.UNVISITED);
        }

        dfs(n,transpose);

        iterator = transpose.nodeIter();
        while (iterator.hasNext()){
            NodeData current = iterator.next();
            if(current.getTag()==Node.UNVISITED){
                return false;
            }
        }

        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        /**
         * calculated with dijkstra algorithm
         */
        dijkstra(src,dest);
        return ((Node)graph.getNode(dest)).getDist();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        /**
         * calculated with dijkstra algorithm
         */
        return dijkstra(src,dest);
    }

    @Override
    public NodeData center() {
        //floyd-warshall - and then search for the node with minimum eccentricity(or one of them at least)
        if (graph.nodeSize()<1)
            return null;

        if (!isConnected())
            return null;

        double[][] floyd = floyd();
        double[] eccentricity=new double[graph.nodeSize()];

        for (int i = 0; i < eccentricity.length ; i++) {
            eccentricity[i]=Integer.MIN_VALUE;
        }

        for (int i = 0; i < floyd.length ; i++) {
            for (int j = 0; j < floyd.length ; j++) {
                if(i!=j && floyd[i][j]>eccentricity[i] && floyd[i][j]!=Integer.MAX_VALUE){
                    eccentricity[i]=floyd[i][j];
                }
            }
        }


        int minIndex=graph.nodeIter().next().getKey();
        for (int i = 0; i < eccentricity.length ; i++) {
            if(eccentricity[i]<eccentricity[minIndex])
                minIndex=i;
        }

        return graph.getNode(minIndex);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        List<NodeData> linkedList= new LinkedList<>();
        for (int i = 0; i < cities.size()-1; i++) {
            List<NodeData> current = shortestPath(cities.get(i).getKey(), cities.get(i+1).getKey());
            if (current.get(0).getKey()==cities.get(i).getKey())
                current.remove(0);
            linkedList.addAll(current);
        }
        return linkedList;
    }

    @Override
    public boolean save(String file) {
        Iterator nodeIterator = getGraph().nodeIter();
        Iterator edgeIterator = getGraph().edgeIter();

        gsonInput gsonIn = new gsonInput(graph.edgeSize(), graph.nodeSize());
        while (nodeIterator.hasNext()) {
            Node current = (Node) nodeIterator.next();
            simpleNode simpleNode= new simpleNode(current.getLocation().x() +"," + current.getLocation().y() +","+ current.getLocation().z(), current.getKey());

            gsonIn.Nodes[current.getKey()]= simpleNode;
        }
        int increment = 0;
        while(edgeIterator.hasNext()){
            Edge current= (Edge) edgeIterator.next();
            simpleEdge simpleEdge= new simpleEdge(current.getSrc() , current.getWeight(), current.getDest());

            gsonIn.Edges[increment]= simpleEdge;
            increment++;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json=gson.toJson(gsonIn);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean load(String file) {
        graph=new Graph();
        Gson gson = new Gson();
        JsonReader reader;
        gsonInput data;
        try {
            reader = new JsonReader(new FileReader(file));
            data = gson.fromJson(reader, gsonInput.class);
        } catch (FileNotFoundException e) {
            return false;
        }
        for (int i = 0; i < data.Nodes.length; i++) {
            int key = data.Nodes[i].id;

            String[] toSplit = data.Nodes[i].pos.split(",");
            double x = Double.parseDouble(toSplit[0]);
            double y = Double.parseDouble(toSplit[1]);
            double z = Double.parseDouble(toSplit[2]);

            Point3D location = new Point3D(x, y, z);
            int weight = 0;
            String info = "";
            int tag = 0;

            Node toAdd = new Node(key, location, weight, info, tag);
            graph.addNode(toAdd);
        }

        for (int i = 0; i < data.Edges.length; i++) {
            int src = data.Edges[i].src;
            int dest = data.Edges[i].dest;
            double weight = data.Edges[i].w;

            graph.connect(src, dest, weight);
        }

        return true;
    }

    /**
     * These three classes are for use of json parsing with gson
     */
    private class gsonInput {
        simpleEdge[] Edges;
        simpleNode[] Nodes;

        protected gsonInput(int lengthEdges, int lengthNodes) {
            Edges = new simpleEdge[lengthEdges];
            Nodes = new simpleNode[lengthNodes];
        }
    }

    private class simpleEdge {
        int src;
        double w;
        int dest;

        protected simpleEdge(int src, double w, int dest){
            this.src = src;
            this.w= w;
            this.dest = dest;
        }
    }

    private class simpleNode {
        String pos;
        int id;

        protected simpleNode(String pos, int id) {
            this.pos = pos;
            this.id = id;
        }
    }

    private void dfs(NodeData n,DirectedWeightedGraph g){
        n.setTag(Node.VISITED);
        Iterator<EdgeData> iterator = g.edgeIter(n.getKey());
        while (iterator.hasNext()){
            EdgeData edge = iterator.next();
            NodeData neighbour = g.getNode(edge.getDest());
            if(neighbour.getTag()!=Node.VISITED)
                dfs(neighbour,g);
        }
    }

    private List<NodeData> dijkstra(int src,int dest){
        Node srcNode = (Node) graph.getNode(src);
        Node destNode = (Node)graph.getNode(dest);
        srcNode.setDist(0);
        PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingDouble(Node::getDist));

        NodeData[] prev = new NodeData[graph.nodeSize()];

        Iterator<NodeData> nodeIterator = graph.nodeIter();
        while (nodeIterator.hasNext()){
            Node current = (Node)nodeIterator.next();
            if(!current.equals(srcNode)){
                current.setDist(Double.MAX_VALUE);
                prev[current.getKey()]=null;
            }

            q.add(current);
        }

        while (!q.isEmpty()){
            Node current = q.poll();
            Iterator<EdgeData> edgeIterator = graph.edgeIter(current.getKey());


            while (edgeIterator.hasNext()){
                EdgeData currentEdge = edgeIterator.next();
                Node neighbour = (Node)graph.getNode(currentEdge.getDest());//v
                double alt = current.getDist()+currentEdge.getWeight();
                if(alt<neighbour.getDist()){
                    neighbour.setDist(alt);
                    prev[neighbour.getKey()]=current;
                    q.remove(neighbour);
                    q.add(neighbour);
                }
            }
        }

        Stack<NodeData> stack = new Stack<>();
        NodeData currentTarget = destNode;
        if(prev[currentTarget.getKey()]!=null || srcNode.equals(currentTarget)){
            while (currentTarget!=null){
                stack.add(currentTarget);
                currentTarget= prev[currentTarget.getKey()];
            }
        }

        List<NodeData> ret = new ArrayList<>();
        while (!stack.isEmpty())
            ret.add(stack.pop());

        return ret;
    }

    private double[][] floyd(){
        double[][] ret = new double[graph.nodeSize()][graph.nodeSize()];
        for (int i = 0; i < ret.length; i++) {
            for (int j = 0; j < ret.length ; j++) {
                ret[i][j]=Integer.MAX_VALUE;
            }
        }

        Iterator<EdgeData> edgeIterator = graph.edgeIter();
        while (edgeIterator.hasNext()){
            EdgeData currentEdge = edgeIterator.next();
            ret[currentEdge.getSrc()][currentEdge.getDest()]=currentEdge.getWeight();
        }

        for (int i = 0; i < ret.length ; i++) {
            ret[i][i]=0;
        }

        for (int i = 0; i < ret.length ; i++) {
            for (int j = 0; j < ret.length ; j++) {
                for (int k = 0; k < ret.length; k++) {
                    if(ret[j][k] > ret[j][i] + ret[i][k])
                        ret[j][k]= ret[j][i] + ret[i][k];
                }
            }
        }

        return ret;
    }

    public static String printList(List<NodeData> list){
        if(list.size()<1)
            return "";

        String ret="";
        for (int i = 0; i < list.size()-1 ; i++) {
            ret+="N"+list.get(i).getKey()+" -> ";
        }

        ret+="N"+list.get(list.size()-1).getKey();


        return ret;
    }



}
