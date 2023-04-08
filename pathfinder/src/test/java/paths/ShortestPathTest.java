package paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import adt.Edge;
import adt.Graph;
import adt.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestPathTest {
    ShortestPath shortestpath = new ShortestPath();
    @Test
    public void getPathTest(){
        Graph graph = new Graph();

        Node n1 = new Node(0.0, 0.0);
        n1.registerNeighbour(1);
        n1.registerNeighbour(2);
        Node n2 = new Node(5, 0);
        n2.registerNeighbour(0);
        n2.registerNeighbour(3);
        Node n3 = new Node(0, 7);
        n3.registerNeighbour(0);
        n3.registerNeighbour(3);
        Node n4 = new Node(6, 7);
        n4.registerNeighbour(1);
        n4.registerNeighbour(2);

        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n3);
        Edge e3 = new Edge(n2, n4);
        Edge e4 = new Edge(n3, n4);

        graph.registerNode(n1);
        graph.registerNode(n2);
        graph.registerNode(n3);
        graph.registerNode(n4);
        graph.registerEdge(e1);
        graph.registerEdge(e2);
        graph.registerEdge(e3);
        graph.registerEdge(e4);

        List<Edge> path = shortestpath.getPath(graph, n1, n4);
        assertEquals(path.size(), 2);
        assertEquals(path.get(0), e1);
        assertEquals(path.get(1), e3);
    }
}
