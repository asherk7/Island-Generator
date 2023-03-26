package biomes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CanadaTest {
    Canada canada = new Canada();
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
        Structs.Property elev1 = Structs.Property.newBuilder().setKey("Elevation").setValue("300").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon1.addProperties(humidity1).addProperties(elev1).addProperties(biome1);

        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder();
        Structs.Property humidity2 = Structs.Property.newBuilder().setKey("Humidity").setValue("300").build();
        Structs.Property elev2 = Structs.Property.newBuilder().setKey("Elevation").setValue("200").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon2.addProperties(humidity2).addProperties(elev2).addProperties(biome2);

        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder();
        Structs.Property humidity3 = Structs.Property.newBuilder().setKey("Humidity").setValue("200").build();
        Structs.Property elev3 = Structs.Property.newBuilder().setKey("Elevation").setValue("200").build();
        Structs.Property biome3 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon3.addProperties(humidity3).addProperties(elev3).addProperties(biome3);

        Structs.Polygon.Builder polygon4 = Structs.Polygon.newBuilder();
        Structs.Property humidity4 = Structs.Property.newBuilder().setKey("Humidity").setValue("100").build();
        Structs.Property elev4 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property biome4 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon4.addProperties(humidity4).addProperties(elev4).addProperties(biome4);

        Structs.Polygon.Builder polygon5 = Structs.Polygon.newBuilder();
        Structs.Property humidity5 = Structs.Property.newBuilder().setKey("Humidity").setValue("300").build();
        Structs.Property elev5 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property biome5 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon5.addProperties(humidity5).addProperties(elev5).addProperties(biome5);

        polygonList.add(polygon);
        polygonList.add(polygon1);
        polygonList.add(polygon2);
        polygonList.add(polygon3);
        polygonList.add(polygon4);
        polygonList.add(polygon5);

        canada.assignBiome(polygonList);

        assertEquals(polygon.getProperties(2).getValue(), "rainforest");
        assertEquals(polygon1.getProperties(2).getValue(), "snowpeak");
        assertEquals(polygon2.getProperties(2).getValue(), "wetland");
        assertEquals(polygon3.getProperties(2).getValue(), "dryland");
        assertEquals(polygon4.getProperties(2).getValue(), "deadland");
        assertEquals(polygon5.getProperties(2).getValue(), "land");
    }
}
