package biomes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TundraTest {
    Tundra tundra = new Tundra();
    @Test
    public void assignBiomeTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("300").build();
        Structs.Property elev = Structs.Property.newBuilder().setKey("Elevation").setValue("300").build();
        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon.addProperties(humidity).addProperties(elev).addProperties(biome);

        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder();
        Structs.Property humidity1 = Structs.Property.newBuilder().setKey("Humidity").setValue("200").build();
        Structs.Property elev1 = Structs.Property.newBuilder().setKey("Elevation").setValue("200").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon1.addProperties(humidity1).addProperties(elev1).addProperties(biome1);

        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder();
        Structs.Property humidity2 = Structs.Property.newBuilder().setKey("Humidity").setValue("300").build();
        Structs.Property elev2 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon2.addProperties(humidity2).addProperties(elev2).addProperties(biome2);

        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder();
        Structs.Property humidity3 = Structs.Property.newBuilder().setKey("Humidity").setValue("100").build();
        Structs.Property elev3 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property biome3 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon3.addProperties(humidity3).addProperties(elev3).addProperties(biome3);

        polygonList.add(polygon);
        polygonList.add(polygon1);
        polygonList.add(polygon2);
        polygonList.add(polygon3);

        tundra.assignBiome(polygonList);

        assertEquals(polygon.getProperties(2).getValue(), "snowpeak");
        assertEquals(polygon1.getProperties(2).getValue(), "permafrost");
        assertEquals(polygon2.getProperties(2).getValue(), "ice");
        assertEquals(polygon3.getProperties(2).getValue(), "deadland");
    }
}
