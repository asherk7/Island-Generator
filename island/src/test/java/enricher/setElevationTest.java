package enricher;
import java.util.ArrayList;
import java.util.List;
import ElevationProfiles.AltProfile;
import ElevationProfiles.Volcano;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class setElevationTest {
    //this function sets the ocean level, each individual elevation type will be tested in their respected test files (VolcanoTest, etc)
    setElevation elevation = new setElevation();

    @Test
    public void testSetElevProfile(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Polygon.Builder polygon_in = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon_out = Structs.Polygon.newBuilder();

        Structs.Property centroid = Structs.Property.newBuilder().setKey("Centroid").setValue("150,250").build();
        Structs.Property centroid1 = Structs.Property.newBuilder().setKey("Centroid").setValue("700,700").build();

        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();

        polygon_in.addProperties(centroid).addProperties(biome);
        polygon_out.addProperties(centroid1).addProperties(biome1);
        polygonList.add(polygon_in);
        polygonList.add(polygon_out);

        elevation.setElevProfile(new Volcano(), polygonList);
        assertEquals(polygon_in.getProperties(2).getValue(), "0");
        assertNotEquals(polygon_out.getProperties(2).getValue(), "0");
    }
}
