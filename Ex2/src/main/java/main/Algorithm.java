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
        if(graph==null){
            init(new Graph());
        }
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
        dijkstra(src,dest);
        return ((Node)graph.getNode(dest)).getDist();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return dijkstra(src,dest);
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
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
        //System.out.println(json);
        try {
            FileWriter fileWriter = new FileWriter(file);
            //JsonWriter jsonWriter = new JsonWriter(fileWriter);
            //jsonWriter.setIndent();
            //jsonWriter.jsonValue(json);
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

    private void bfs(NodeData src,NodeData dest,DirectedWeightedGraph g){
        Queue<NodeData> q = new LinkedList<>();
        src.setTag(Node.VISITED);
        q.add(src);

        double weight=0;

        while (!q.isEmpty()){
            NodeData current = q.remove();
            if(current.equals(dest));
        }
    }

    public List<NodeData> dijkstra(int src,int dest){
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

    public static String printList(List<NodeData> list){
        String ret="";
        for (int i = 0; i < list.size()-1 ; i++) {
            ret+="N"+list.get(i).getKey()+" -> ";
        }

        ret+="N"+list.get(list.size()-1).getKey();


        return ret;
    }



}
