package adt;

import java.util.Set;

public class Graph {
    private Set<Node> nodes;
    private Set<Edge> edges;

    public void registerNode(Node n){ this.nodes.add(n); }
    public void registerEdge(Edge e){ this.edges.add(e); }

}
