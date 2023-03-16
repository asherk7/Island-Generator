package lagoon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.junit.jupiter.api.TestTemplate;

import java.util.ArrayList;
import java.util.List;

public class lagoonGenTest {
    public lagoonGen lagoon = new lagoonGen();

    @Test
    public void testAssignBiome(){
        List<Structs.Vertex> vertices = new ArrayList<>();

        Structs.Vertex centroid = Structs.Vertex.newBuilder().setX(20).setY(20).build();
        Structs.Vertex centroid1 = Structs.Vertex.newBuilder().setX(15).setY(15).build();
        Structs.Polygon p = Structs.Polygon.newBuilder().setCentroidIdx(0).build();
        Structs.Polygon p1 = Structs.Polygon.newBuilder().setCentroidIdx(1).build();
        vertices.add(centroid);
        vertices.add(centroid1);

        Structs.Property width = Structs.Property.newBuilder().setKey("Width").setValue("30").build();
        Structs.Property height = Structs.Property.newBuilder().setKey("Height").setValue("30").build();
        Mesh aMesh = Mesh.newBuilder().addPolygons(p).addPolygons(p1).addProperties(width).addProperties(height).build();

        Structs.Property biome = lagoon.assignBiome(aMesh, p, vertices);
        Structs.Property biome1 = lagoon.assignBiome(aMesh, p1, vertices);

        assertEquals(biome.getValue(), "Land");
        assertNotEquals(biome1.getValue(), "Ocean");
        assertEquals(biome1.getValue(), "Lagoon");
    }

    @Test
    public void testAssignColor(){
        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("Ocean").build();
        Structs.Polygon.Builder p = Structs.Polygon.newBuilder().addProperties(biome);
        Structs.Property color1 = lagoon.assignColour(p);
        assertEquals(color1.getKey(), "Color");
        assertEquals(color1.getValue(), "0,0,255");
        assertNotEquals(color1.getKey(), "Biome");
        assertNotEquals(color1.getValue(), "0,5,243");
    }
}
