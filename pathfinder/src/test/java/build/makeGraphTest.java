package build;

import adt.Edge;
import adt.Graph;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class makeGraphTest {
    makeGraph makegraph = new makeGraph();
    @Test
    public void runTest(){
        Structs.Mesh.Builder mesh = Structs.Mesh.newBuilder();
        List<Structs.Polygon> polygonList = new ArrayList<>();
        List<Structs.Vertex> vertices = new ArrayList<>();

        Structs.Polygon p1 = Structs.Polygon.newBuilder().setCentroidIdx(0).build();
        Structs.Polygon p2 = Structs.Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(2).build();
        Structs.Polygon p3 = Structs.Polygon.newBuilder().setCentroidIdx(2).addNeighborIdxs(0).addNeighborIdxs(1).build();

        polygonList.add(p1);
        polygonList.add(p2);
        polygonList.add(p3);

        Structs.Vertex v1 = Structs.Vertex.newBuilder().setX(0).setY(0).build();
        Structs.Vertex v2 = Structs.Vertex.newBuilder().setX(7).setY(0).build();
        Structs.Vertex v3 = Structs.Vertex.newBuilder().setX(12).setY(0).build();

        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        mesh.addAllVertices(vertices);
        mesh.addAllPolygons(polygonList);

        Graph graph = makegraph.run(mesh);
        List<Node> nodes = graph.getNodeList();
        Set<Edge> edges = graph.getEdgeSet();

        assertEquals(nodes.size(), 3); //making sure every node was creted
        assertEquals(nodes.get(0).getNeighbours().size(), 0);
        assertTrue(nodes.get(1).getNeighbours().contains(nodes.indexOf(nodes.get(2))));
        assertTrue(nodes.get(2).getNeighbours().contains(nodes.indexOf(nodes.get(0))));
        assertTrue(nodes.get(2).getNeighbours().contains(nodes.indexOf(nodes.get(1)))); //making sure the neighbours are correct
        assertEquals(edges.size(), 2); //making sure the edges are created properly with no duplicates between 2 nodes
        for (Edge e: edges){
            if (e.contains(nodes.get(2)) && e.contains(nodes.get(1))){
                assertEquals(e.getWeight(), 5.0); //making sure the weights are correct
            }
            else if (e.contains(nodes.get(2)) && e.contains(nodes.get(0))){
                assertEquals(e.getWeight(), 12.0);
            }
        }
    }
}
