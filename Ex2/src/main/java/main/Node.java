package main;

import api.GeoLocation;
import api.NodeData;

import java.util.Enumeration;
import java.util.Hashtable;

public class Node implements NodeData {
    private Hashtable<Integer,Edge> edges;

    private int key;
    private GeoLocation location;
    private double weight;
    private String info;
    private int tag;

    private double dist;

    public final static int UNVISITED =0;
    public final static int VISITED=2;

    public Node(int key, GeoLocation location, double weight, String info, int tag) {
        this.key = key;
        this.location = location;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
        edges =new Hashtable<>();
    }

    public Node(int key, GeoLocation location, double weight, String info, int tag,Hashtable edges) {
        this.key = key;
        this.location = location;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
        this.edges = edges;
    }

    @Override
    public int getKey() {
        return key;
    }

    public void setKey(int key){
        this.key=key;
    }

    @Override
    public GeoLocation getLocation() {
        return location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location=p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    public Hashtable getEdges(){
        return edges;
    }

    public void setEdges(Hashtable<Integer, Edge> edges) {
        this.edges = edges;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    @Override
    public Node clone(){
        Enumeration<Integer> keys = this.edges.keys();
        Hashtable<Integer,Edge> edgesLocal = new Hashtable<>();
        while (keys.hasMoreElements()){
            int current = keys.nextElement();
            edgesLocal.put(current,edges.get(current).clone());
        }

        return new Node(key,((Point3D)location).clone(),weight,info,tag,edgesLocal);

    }
}
