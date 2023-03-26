package water;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class lakeGenTest {
    lakeGen lakegen = new lakeGen();

    @Test
    public void testDrawLakes(){
        List<Structs.Polygon.Builder> landland = new ArrayList<>();
        List<Structs.Polygon.Builder> landocean = new ArrayList<>();
        List<Structs.Polygon.Builder> landhighland = new ArrayList<>();

        Structs.Property e = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e1 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e2 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e3 = Structs.Property.newBuilder().setKey("Elevation").setValue("0").build();
        Structs.Property e4 = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property e5 = Structs.Property.newBuilder().setKey("Elevation").setValue("200").build();

        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();

        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome1 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome2 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome3 = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
        Structs.Property biome4 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        Structs.Property biome5 = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder().addProperties(e).addProperties(biome).addNeighborIdxs(1);
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder().addProperties(e1).addProperties(biome1).addNeighborIdxs(0);
        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder().addProperties(e2).addProperties(biome2).addNeighborIdxs(1);
        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder().addProperties(e3).addProperties(biome3).addNeighborIdxs(0);
        Structs.Polygon.Builder polygon4 = Structs.Polygon.newBuilder().addProperties(e4).addProperties(biome4).addNeighborIdxs(1);
        Structs.Polygon.Builder polygon5 = Structs.Polygon.newBuilder().addProperties(e5).addProperties(biome5).addProperties(humidity).addNeighborIdxs(0);

        landland.add(polygon);
        landland.add(polygon1);
        landocean.add(polygon2);
        landocean.add(polygon3);
        landhighland.add(polygon4);
        landhighland.add(polygon5);

        lakegen.drawLakes(1, landland, 0);
        assertEquals(polygon.getProperties(1).getValue(), "lake");
        assertEquals(polygon1.getProperties(1).getValue(), "lake");

        lakegen.drawLakes(1, landocean, 0);
        assertNotEquals(polygon2.getProperties(1).getValue(), "lake");
        assertNotEquals(polygon3.getProperties(1).getValue(), "lake");

        lakegen.drawLakes(1, landhighland, 0);
        assertEquals(polygon4.getProperties(1).getValue(), "lake");
        assertNotEquals(polygon5.getProperties(1).getValue(), "lake");
        assertEquals(polygon5.getProperties(2).getValue(), "150");

    }
}
