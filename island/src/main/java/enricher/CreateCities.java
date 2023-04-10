package enricher;

import adt.Edge;
import adt.Graph;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import paths.ShortestPath;

import java.util.ArrayList;
import java.util.List;

public class CreateCities {

    public Graph makeGraph(Structs.Mesh.Builder mesh){
        Graph graph = new Graph();
        outer:
        for (Structs.Vertex v: mesh.getVerticesList()){
            //make sure polygon centroids aren't created as nodes
            for (Structs.Polygon p: mesh.getPolygonsList()){
                if (mesh.getVerticesList().indexOf(v) == p.getCentroidIdx()){
                    continue outer;
                }
            }
            Node n = new Node(v.getX(), v.getY()); //mapping node index to the same index as the vertex index
            for (Structs.Segment s: mesh.getSegmentsList()){
                if (s.getV1Idx() == mesh.getVerticesList().indexOf(v)){
                    n.registerNeighbour(s.getV2Idx());
                }
                else if (s.getV2Idx() == mesh.getVerticesList().indexOf(v)){
                    n.registerNeighbour(s.getV1Idx());
                }
            }
            graph.registerNode(n);
        }
        for (Node n: graph.getNodeList()){
            for (Integer i: n.getNeighbours()){
                Node neighbour = graph.getNode(i);
                Edge e = new Edge(n, neighbour);
                graph.registerEdge(e);
            }
        }
        return graph;
    }

    public void getPath(Graph graph, Structs.Mesh.Builder mesh, Node start, List<Node> end){
        ShortestPath createPath = new ShortestPath();
        for (Node n: end) {
            List<Edge> path = createPath.getPath(graph, start, n);

            for (Edge e : path) {
                Structs.Segment.Builder segment = Structs.Segment.newBuilder();
                //nodes are mapped to the same index as the vertex
                Node n1 = e.getNodes()[0];
                Node n2 = e.getNodes()[1];

                for (Structs.Segment s : mesh.getSegmentsList()) {
                    if (s.getV1Idx() == graph.getNodeList().indexOf(n1) && s.getV2Idx() == graph.getNodeList().indexOf(n2)) {
                        Structs.Property property = Structs.Property.newBuilder().setKey("Path").setValue("True").build();
                        s.toBuilder().addProperties(property).build();
                    }
                }
            }
        }
    }
}
