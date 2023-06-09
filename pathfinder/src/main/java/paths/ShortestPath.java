package paths;

import adt.Edge;
import adt.Graph;
import adt.Node;

import java.util.*;

public class ShortestPath implements pathfinder {
    @Override
    public List<Edge> getPath(Graph graph, Node start, Node end) {
        List<Edge> path = new ArrayList<>();
        //using dijkstra's shortest path algorithm
        Map<Node, Node> nodePath = new HashMap<>();
        Map<Node, Double> pathCost = new HashMap<>();

        //add every node
        for (Node n: graph.getNodeList()){
            nodePath.put(n, null);
            pathCost.put(n, Double.MAX_VALUE);
        }

        nodePath.put(start, start);
        pathCost.put(start, 0.0);

        //using a priority queue, and giving it a node with a value(the cost), and sorting its priority by cost
        PriorityQueue<HashMap<Node, Double>> Q = new PriorityQueue<>((a, b) -> {
            Node keyInA = a.keySet().iterator().next();
            Node keyInB = b.keySet().iterator().next();
            return (int) (a.get(keyInA) - b.get(keyInB));
        });

        Q.add(new HashMap<>(){ { put(start, pathCost.get(start)); } });

        while (!Q.isEmpty()){
            Map<Node, Double> data = Q.poll();
            Node m = data.keySet().iterator().next();
            for (Integer i: m.getNeighbours()) {
                Node n = graph.getNode(i);
                Edge e = graph.getEdge(m, n);
                if (pathCost.get(m) + e.getWeight() < pathCost.get(n)){
                    nodePath.put(n, m);
                    pathCost.put(n, pathCost.get(m) + e.getWeight());
                    Q.add(new HashMap<>(){ { put(n, pathCost.get(n)); } });
                }
            }
        }
        List<Node> nodes = new ArrayList<>();
        nodes.add(end);
        Node node_iterator = nodePath.get(end);
        while (!node_iterator.equals(start)){
            nodes.add(node_iterator);
            node_iterator = nodePath.get(node_iterator);
        }
        nodes.add(start);
        //creating a path of edges
        for (int i = nodes.size()-1; i>0; i--){
            Node node1 = nodes.get(i);
            Node node2 = nodes.get(i-1);
            path.add(graph.getEdge(node1, node2));
        }
        return path;
    }
}
