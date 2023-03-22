package elevation;
import ElevationProfiles.Volcano;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class VolcanoTest {
    Volcano volcanoGen = new Volcano();

    @Test
    public void testMarkHeight(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Property center = Structs.Property.newBuilder().setKey("Centroid").setValue("250,250").build();
        Structs.Property centroid = Structs.Property.newBuilder().setKey("Centroid").setValue("100,100").build();
        Structs.Property centroid1 = Structs.Property.newBuilder().setKey("Centroid").setValue("250,100").build();
        Structs.Property centroid2 = Structs.Property.newBuilder().setKey("Centroid").setValue("400,100").build();
        Structs.Property centroid3 = Structs.Property.newBuilder().setKey("Centroid").setValue("100,250").build();
        Structs.Property centroid4 = Structs.Property.newBuilder().setKey("Centroid").setValue("400,250").build();
        Structs.Property centroid5 = Structs.Property.newBuilder().setKey("Centroid").setValue("100,400").build();
        Structs.Property centroid6 = Structs.Property.newBuilder().setKey("Centroid").setValue("250,400").build();
        Structs.Property centroid7 = Structs.Property.newBuilder().setKey("Centroid").setValue("400,400").build();

        Structs.Polygon.Builder peak = Structs.Polygon.newBuilder().addProperties(center);
        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder().addProperties(centroid);
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder().addProperties(centroid1);
        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder().addProperties(centroid2);
        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder().addProperties(centroid3);
        Structs.Polygon.Builder polygon4 = Structs.Polygon.newBuilder().addProperties(centroid4);
        Structs.Polygon.Builder polygon5 = Structs.Polygon.newBuilder().addProperties(centroid5);
        Structs.Polygon.Builder polygon6 = Structs.Polygon.newBuilder().addProperties(centroid6);
        Structs.Polygon.Builder polygon7 = Structs.Polygon.newBuilder().addProperties(centroid7);

        polygonList.add(peak);
        polygonList.add(polygon);
        polygonList.add(polygon1);
        polygonList.add(polygon2);
        polygonList.add(polygon3);
        polygonList.add(polygon4);
        polygonList.add(polygon5);
        polygonList.add(polygon6);
        polygonList.add(polygon7);

        peak.addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).addNeighborIdxs(4).addNeighborIdxs(5).addNeighborIdxs(6).addNeighborIdxs(7).addNeighborIdxs(8);

        volcanoGen.markHeight(polygonList,polygonList);

        assertEquals(peak.getProperties(1).getValue(), "800");
        for (int i: peak.getNeighborIdxsList()){
            assertEquals(polygonList.get(i).getProperties(1).getValue(), "700");
        }
    }
}
