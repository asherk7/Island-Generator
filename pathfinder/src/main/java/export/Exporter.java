package export;

import adt.Edge;
import adt.Graph;
import adt.Node;
import build.makeGraph;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import paths.ShortestPath;

import java.util.ArrayList;
import java.util.List;

public class Exporter {

    public List<Structs.Segment> export(Structs.Mesh.Builder mesh, Node start, Node end){
        List<Structs.Segment> final_path = new ArrayList<>();

        makeGraph createGraph = new makeGraph();
        ShortestPath createPath = new ShortestPath();
        Graph graph = createGraph.run(mesh);
        List<Edge> path = createPath.getPath(graph, start, end);

        for (Edge e : path){
            Structs.Segment.Builder segment = Structs.Segment.newBuilder();
            //nodes are mapped to the same index as the vertex
            Node n1 = e.getNodes()[0];
            Node n2 = e.getNodes()[1];

            Structs.Vertex v1 = mesh.getVertices(graph.getNodeList().indexOf(n1));
            Structs.Vertex v2 = mesh.getVertices(graph.getNodeList().indexOf(n2));

            for (Structs.Segment s: mesh.getSegmentsList()){
                if (s.getV1Idx() == graph.getNodeList().indexOf(n1) && s.getV2Idx() == graph.getNodeList().indexOf(n2)){
                    Structs.Property property = Structs.Property.newBuilder().setKey("Path").setValue("True").build();
                    s.toBuilder().addProperties(property).build();
                    final_path.add(s);
                }
            }
        }
        return final_path;
    }

}
