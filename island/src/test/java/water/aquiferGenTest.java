package water;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class aquiferGenTest {
    @Test
    public void drawAquifersTest(){
        aquiferGen aquifer = new aquiferGen();
        List<Structs.Polygon.Builder> landland = new ArrayList<>();
        List<Structs.Polygon.Builder> land = new ArrayList<>();

        Structs.Property e = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e1 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e2 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();

        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();
        Structs.Property humidity1 = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();
        Structs.Property humidity2 = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();

        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder().addProperties(e).addProperties(biome).addProperties(humidity).addNeighborIdxs(1);
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder().addProperties(e1).addProperties(biome1).addProperties(humidity1).addNeighborIdxs(0);
        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder().addProperties(e2).addProperties(biome2).addProperties(humidity2);

        landland.add(polygon);
        landland.add(polygon1);
        land.add(polygon2);

        aquifer.drawAquifers(1, landland);
        aquifer.drawAquifers(1, land);

        assertEquals(polygon2.getProperties(2).getKey(), "Aquifer");
        assertEquals(polygon2.getProperties(3).getValue(), "150");

        if (polygon.getPropertiesList().size() == 4){
            assertEquals(polygon1.getProperties(2).getValue(), "150");
        }
        else{
            assertEquals(polygon.getProperties(2).getValue(), "150");
        }

    }
}
