package enricher;
import adt.Edge;
import adt.Graph;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class CreateCitiesTest {

    CreateCities makegraph = new CreateCities();
    @Test
    public void makeGraphTest(){
        Structs.Mesh.Builder mesh = Structs.Mesh.newBuilder();
        List<Structs.Vertex> specialVertices = new ArrayList<>();

        Structs.Property p = Structs.Property.newBuilder().setKey("Test").setValue("100").build();

        Structs.Vertex v = Structs.Vertex.newBuilder().setX(0).setY(0).addProperties(p).build();
        Structs.Vertex v1 = Structs.Vertex.newBuilder().setX(1).setY(1).addProperties(p).build();
        Structs.Vertex v2 = Structs.Vertex.newBuilder().setX(2).setY(2).addProperties(p).build();

        HashMap<Structs.Vertex, Set<Structs.Vertex>> vertexNeighbours = new HashMap<>();

        mesh.addVertices(v).addVertices(v1).addVertices(v2);
        specialVertices.add(v);
        specialVertices.add(v1);

        Set<Structs.Vertex> temp = new HashSet<>();
        temp.add(v1);
        temp.add(v2);

        vertexNeighbours.put(v, temp);

        Graph graph = makegraph.makeGraph(mesh, specialVertices, vertexNeighbours);
        List<Node> nodes = graph.getNodeList();
        Set<Edge> edges = graph.getEdgeSet();

        assertEquals(nodes.get(0).getProperty("Test"), "100");
        assertEquals(nodes.get(0).getNeighbours().size(), 1);
        assertEquals(nodes.get(1).getNeighbours().size(), 0);
        assertEquals(nodes.size(), 2);
        assertEquals(edges.size(), 1);

    }
}
