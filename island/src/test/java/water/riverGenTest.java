package water;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class riverGenTest {
    @Test
    public void drawRiversTest(){
        riverGen river = new riverGen();
        List<Structs.Polygon.Builder> landland = new ArrayList<>();
        List<Structs.Polygon.Builder> landocean = new ArrayList<>();
        List<Structs.Polygon.Builder> landtolake = new ArrayList<>();

        Structs.Property e = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e1 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e2 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e3 = Structs.Property.newBuilder().setKey("Elevation").setValue("0").build();
        Structs.Property e4 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();

        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();

        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome3 = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
        Structs.Property biome4 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder().addProperties(e).addProperties(biome).addProperties(humidity).addNeighborIdxs(1).setCentroidIdx(0);
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder().addProperties(e1).addProperties(biome1).addProperties(humidity).addNeighborIdxs(0).setCentroidIdx(0);
        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder().addProperties(e2).addProperties(biome2).addProperties(humidity).addNeighborIdxs(1).setCentroidIdx(5);
        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder().addProperties(e3).addProperties(biome3).addProperties(humidity).addNeighborIdxs(0).setCentroidIdx(5);
        Structs.Polygon.Builder polygon4 = Structs.Polygon.newBuilder().addProperties(e4).addProperties(biome4).addProperties(humidity);

        landland.add(polygon);
        landland.add(polygon1);
        landocean.add(polygon2);
        landocean.add(polygon3);
        landtolake.add(polygon4);

        List<Structs.Segment.Builder> river1 = river.drawRivers(1, landland, 1);
        assertEquals(river1.get(0).getV1Idx(), 0);
        assertEquals(river1.get(0).getV2Idx(), 0);
        for (int i = 0; i<polygon.getPropertiesList().size(); i++){
            if (polygon.getProperties(i).getKey().equals("River")){
                assertEquals(polygon1.getProperties(2).getValue(), "lake");
                assertEquals(polygon.getProperties(3).getValue(), "225");
            }
        }
        for (int i = 0; i<polygon1.getPropertiesList().size(); i++){
            if (polygon1.getProperties(i).getKey().equals("River")){
                assertEquals(polygon.getProperties(2).getValue(), "lake");
                assertEquals(polygon1.getProperties(3).getValue(), "225");
            }
        }

        List<Structs.Segment.Builder> river2 = river.drawRivers(1, landocean, 1);
        assertEquals(river2.get(1).getV1Idx(), 5);
        assertEquals(river2.get(1).getV2Idx(), 5);
        assertEquals(polygon2.getProperties(3).getValue(), "75");

        List<Structs.Segment.Builder> river3 = river.drawRivers(1, landtolake, 0);
        assertEquals(polygon4.getProperties(2).getValue(), "lake");
    }
}
