package elevation;
import ElevationProfiles.Hills;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class HillsTest {
    Hills hillGen = new Hills();

    @Test
    public void testMarkHeight(){
        List<Structs.Polygon.Builder> peakList = new ArrayList<>();
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Polygon.Builder peak = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon2 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon3 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon4 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon5 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon6 = Structs.Polygon.newBuilder();
        Structs.Polygon.Builder polygon7 = Structs.Polygon.newBuilder();

        peakList.add(peak);
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

        hillGen.markHeight(peakList, polygonList);
        assertEquals(peak.getProperties(1).getValue(), "300");
        for (int i: peak.getNeighborIdxsList()){
            assertEquals(polygonList.get(i).getProperties(0).getValue(), "200");
        }
    }
}
