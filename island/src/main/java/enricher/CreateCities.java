package enricher;

import adt.Edge;
import adt.Graph;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import paths.ShortestPath;
import water.CheckNeighbours;

import java.util.ArrayList;
import java.util.List;

public class CreateCities {
    CheckNeighbours check = new CheckNeighbours();

    public Graph makeGraph(Structs.Mesh.Builder mesh, List<Structs.Vertex> specialVertices){
        Graph graph = new Graph();
        outer:
        for (Structs.Vertex v: specialVertices){
            //make sure polygon centroids aren't created as nodes
            for (Structs.Polygon p: mesh.getPolygonsList()){ //delete since we're checking segment vertices
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
            for (Structs.Property p: v.getPropertiesList()){
                n.addProperty(p.getKey(), p.getValue());
            }
            graph.registerNode(n);
        }
        for (Node n: graph.getNodeList()){
            for (Integer i: n.getNeighbours()){
                Node neighbour = graph.getNode(i);
                Edge e = new Edge(n, neighbour);
                for (Structs.Segment s: mesh.getSegmentsList()){
                    if ((s.getV1Idx() == graph.getNodeList().indexOf(n) && s.getV2Idx() == i) || (s.getV2Idx() == graph.getNodeList().indexOf(n) && s.getV1Idx() == i) ){
                        for (Structs.Property p: s.getPropertiesList()){
                            e.addProperty(p.getKey(), p.getValue());
                        }
                        break;
                    }
                }
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

    public List<Structs.Vertex> findValidVertices(Structs.Mesh.Builder newMesh){
        List<Structs.Polygon> tempPoly = new ArrayList<>();
        List<Structs.Vertex> vertices = new ArrayList<>();
        for (int i=0; i<newMesh.getPolygonsList().size(); i++){
            for (Structs.Property p: newMesh.getPolygons(i).getPropertiesList()){
                if (p.getKey().equals("Biome") && !(p.getValue().equals("lake") || p.getValue().equals("ocean"))) {
                    if (check.checkWaterNeighbours(newMesh.getPolygons(i), newMesh.getPolygonsList())){
                        tempPoly.add(newMesh.getPolygons(i));
                    }
                }
            }
        }
        for (Structs.Polygon p: tempPoly){
            for (Integer i: p.getSegmentIdxsList()){
                int v1 = newMesh.getSegments(i).getV1Idx();
                int v2 = newMesh.getSegments(i).getV2Idx();

            }
        }
        return vertices;
    }
}
