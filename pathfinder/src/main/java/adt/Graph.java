package adt;

import java.util.*;

public class Graph {
    private List<Node> nodes;
    private Set<Edge> edges;

    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new HashSet<>();
    }

    public void registerNode(Node n){ this.nodes.add(n); }
    public void registerEdge(Edge e){
        if (!exists(e)) {
            this.edges.add(e);
        }
    }

    public Boolean exists(Edge e){
        Node n1 = e.getNodes()[0];
        Node n2 = e.getNodes()[1];
        for (Edge e1: this.edges){
            if (e1.contains(n1) && e1.contains(n2)){
                return true;
            }
        }
        return false;
    }

    public List<Node> getNodeList(){ return this.nodes; }

    public Edge getEdge(Node n1, Node n2){
        for (Edge e: this.edges){
            if (e.contains(n1) && e.contains(n2)){
                return e;
            }
        }
        return null;
    }

    public Node getNode(Integer position){ return nodes.get(position); }

}
