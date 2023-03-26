package SoilProfile;
import SoilProfiles.WetSoil;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class WetSoilTest {
    WetSoil soil = new WetSoil();
    @Test
    public void absorptionTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();
        Structs.Property humidity1 = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();

        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();

        Structs.Property centroid = Structs.Property.newBuilder().setKey("Centroid").setValue("100,100").build();
        Structs.Property centroid1 = Structs.Property.newBuilder().setKey("Centroid").setValue("200,200").build();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder().addProperties(humidity).addProperties(biome).addProperties(centroid);
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder().addProperties(humidity1).addProperties(biome1).addProperties(centroid1);

        polygonList.add(polygon);
        polygonList.add(polygon1);

        soil.absorption(polygonList);
        assertEquals(polygon.getProperties(2).getValue(), "6");
    }
}
