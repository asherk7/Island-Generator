package paths;

import adt.Edge;
import adt.Graph;
import adt.Node;

import java.util.ArrayList;
import java.util.List;

public class ShortestPath implements pathfinder {
    @Override
    public List<Edge> getPath(Graph graph, Node start, Node end) {
        List<Edge> path = new ArrayList<>();

        return path;
    }
    //for path, just use dijsktra algo
    //return the path as an ordered list of edges(2 nodes)
    //convert this path to a list of segments and send it back (just like how moser did with polygons)
    //give each segment a property indicating that it's a path
}
