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
        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon4 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon5 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon6 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon7 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon8 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon9 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon10 = Structs.Polygon.newBuilder();

        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("rainforest").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("dryforest").build();
        Structs.Property biome3 = Structs.Property.newBuilder().setKey("Biome").setValue("wetland").build();
        Structs.Property biome4 = Structs.Property.newBuilder().setKey("Biome").setValue("dryland").build();
        Structs.Property biome5 = Structs.Property.newBuilder().setKey("Biome").setValue("snowpeak").build();
        Structs.Property biome6 = Structs.Property.newBuilder().setKey("Biome").setValue("permafrost").build();
        Structs.Property biome7 = Structs.Property.newBuilder().setKey("Biome").setValue("ice").build();
        Structs.Property biome8 = Structs.Property.newBuilder().setKey("Biome").setValue("deadland").build();
        Structs.Property biome9 = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
        Structs.Property biome10 = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();

        polygon.addProperties(biome);
        polygon1.addProperties(biome1);
        polygon2.addProperties(biome2);
        polygon3.addProperties(biome3);
        polygon4.addProperties(biome4);
        polygon5.addProperties(biome5);
        polygon6.addProperties(biome6);
        polygon7.addProperties(biome7);
        polygon8.addProperties(biome8);
        polygon9.addProperties(biome9);
        polygon10.addProperties(biome10);

        polygonList.add(polygon);
        polygonList.add(polygon1);
        polygonList.add(polygon2);
        polygonList.add(polygon3);
        polygonList.add(polygon4);
        polygonList.add(polygon5);
        polygonList.add(polygon6);
        polygonList.add(polygon7);
        polygonList.add(polygon8);
        polygonList.add(polygon9);
        polygonList.add(polygon10);

        setColor.assignColor(polygonList);
        String c = polygon.getProperties(1).getValue();
        String c1 = polygon1.getProperties(1).getValue();
        String c2 = polygon2.getProperties(1).getValue();
        String c3 = polygon3.getProperties(1).getValue();
        String c4 = polygon4.getProperties(1).getValue();
        String c5 = polygon5.getProperties(1).getValue();
        String c6 = polygon6.getProperties(1).getValue();
        String c7 = polygon7.getProperties(1).getValue();
        String c8 = polygon8.getProperties(1).getValue();
        String c9 = polygon9.getProperties(1).getValue();
        String c10 = polygon10.getProperties(1).getValue();

        assertEquals(c, "1,50,32");
        assertEquals(c1, "3,190,23");
        assertEquals(c2, "76,117,46");
        assertEquals(c3, "5,66,4");
        assertEquals(c4, "158,162,107");
        assertEquals(c5, "255,255,255");
        assertEquals(c6, "18,126,148");
        assertEquals(c7, "165,242,243");
        assertEquals(c8, "128,91,0");
        assertEquals(c9, "32,4,145");
        assertEquals(c10, "32,4,145");

        Structs.Segment.Builder segment = Structs.Segment.newBuilder();
        Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue("3").build();
        segment.addProperties(river);
        setColor.assignColor(segment);
        String c_river = segment.getProperties(1).getValue();
        assertEquals(c_river, "32,40,185");

    }
}
