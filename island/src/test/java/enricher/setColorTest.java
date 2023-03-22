package enricher;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class setColorTest {
    setColor setColor = new setColor();

    @Test
    public void assignColorTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();
        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder();
        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
        polygon.addProperties(biome);
        polygon1.addProperties(biome1);
        polygon2.addProperties(biome2);
        polygonList.add(polygon);
        polygonList.add(polygon1);
        polygonList.add(polygon2);

        setColor.assignColor(polygonList);
        String c = polygon.getProperties(1).getValue();
        String c1 = polygon1.getProperties(1).getValue();
        String c2 = polygon2.getProperties(1).getValue();

        assertEquals(c, "1,50,32");
        assertEquals(c1, "32,4,145");
        assertEquals(c2, "32,4,145");

        Structs.Segment.Builder segment = Structs.Segment.newBuilder();
        Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue("3").build();
        segment.addProperties(river);
        setColor.assignColor(segment);
        String c3 = segment.getProperties(1).getValue();
        assertEquals(c3, "32,4,145");

    }
}
