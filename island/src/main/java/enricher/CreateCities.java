package enricher;

import adt.Edge;
import adt.Graph;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import paths.ShortestPath;
import water.CheckNeighbours;

import java.util.*;

public class CreateCities {

    public Graph makeGraph(Structs.Mesh.Builder mesh, List<Structs.Vertex> specialVertices, HashMap<Structs.Vertex, Set<Structs.Vertex>> polygonNeighbours){
        Graph graph = new Graph();
        for (Structs.Vertex v : specialVertices){
            Node n = new Node(v.getX(), v.getY());
            Set<Structs.Vertex> neighbours = polygonNeighbours.get(v);
            for (Structs.Vertex v1 : neighbours){
                if (specialVertices.contains(v1)){
                    n.registerNeighbour(specialVertices.indexOf(v1));
                }
            }
            for (Structs.Property property : v.getPropertiesList()){
                n.addProperty(property.getKey(), property.getValue());
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

    public void makePath(Graph graph, Structs.Mesh.Builder mesh, Node start, Node end){
        ShortestPath createPath = new ShortestPath();
        List<Edge> path = createPath.getPath(graph, start, end);
        for (Edge e : path) {
            //nodes are mapped to the same index as the vertex
            Node n1 = e.getNodes()[0];
            Node n2 = e.getNodes()[1];

            //FIX THIS PART

            int v1 = mesh.getPolygonsList().get(graph.getNodeList().indexOf(n1)).getCentroidIdx();
            int v2 = mesh.getPolygonsList().get(graph.getNodeList().indexOf(n2)).getCentroidIdx();
            Structs.Property property = Structs.Property.newBuilder().setKey("Path").setValue("True").build();
            Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).addProperties(property).build();
            mesh.addSegments(s);
        }
    }

    public List<Structs.Vertex> findValidVertices(Structs.Mesh.Builder newMesh){
        List<Structs.Polygon> polygons = newMesh.getPolygonsList();
        List<Structs.Vertex> vertices = new ArrayList<>();

        for (Structs.Polygon p: polygons){
            for (Structs.Property property: p.getPropertiesList()){
                if (property.getKey().equals("Biome") && !(property.getValue().equals("lake") || property.getValue().equals("ocean"))) {
                    vertices.add(newMesh.getVertices(p.getCentroidIdx()));
                }
            }
        }
        return vertices;
    }
}
