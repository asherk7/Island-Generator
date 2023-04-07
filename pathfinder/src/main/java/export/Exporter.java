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
            //nodes are mapped to the same index as the polygon with the centroid associated
            Node n1 = e.getNodes()[0];
            Node n2 = e.getNodes()[1];
            Structs.Polygon p1 = mesh.getPolygons(graph.getNodeList().indexOf(n1));
            Structs.Polygon p2 = mesh.getPolygons(graph.getNodeList().indexOf(n2));

            Structs.Property property = Structs.Property.newBuilder().setKey("Path").setValue("True").build();
            segment.setV1Idx(p1.getCentroidIdx()).setV2Idx(p2.getCentroidIdx()).addProperties(property);
            final_path.add(segment.build());
        }

        return final_path;
    }

}
