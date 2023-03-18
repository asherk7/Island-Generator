package adt;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import shapes.Circle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class generateIslandTest {
    generateIsland islandgen = new generateIsland(500, 500);
    Circle circle = new Circle();

    @Test
    public void drawIslandTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Polygon.Builder polygon_in = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon_out = Structs.Polygon.newBuilder();

        Structs.Property centroid = Structs.Property.newBuilder().setKey("Centroid").setValue("150,250").build();
        Structs.Property centroid1 = Structs.Property.newBuilder().setKey("Centroid").setValue("700,700").build();

        polygon_in.addProperties(centroid);
        polygon_out.addProperties(centroid1);
        polygonList.add(polygon_in);
        polygonList.add(polygon_out);

        islandgen.drawIsland(circle, polygonList);

        String land = polygonList.get(0).getProperties(1).getValue();
        String ocean = polygonList.get(1).getProperties(1).getValue();

        assertEquals(land, "land");
        assertEquals(ocean, "ocean");
    }
}
