package paths;
import adt.Edge;
import adt.Graph;
import adt.Node;
import java.util.List;

public interface pathfinder {
    List<Edge> getPath(Graph graph, Node start, Node end);
}
