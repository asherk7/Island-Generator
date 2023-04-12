package water;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class riverGenTest {
    riverGen river = new riverGen();
    @Test
    public void drawRiversTest(){
        Structs.Mesh.Builder mesh = Structs.Mesh.newBuilder();
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Property e = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();
        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder().addProperties(e).addProperties(humidity).addProperties(biome).addSegmentIdxs(0);
        polygonList.add(polygon);

        Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(0).setV2Idx(1).build();

        Structs.Vertex v = Structs.Vertex.newBuilder().setX(0).setY(0).build();
        Structs.Vertex v1 = Structs.Vertex.newBuilder().setX(10).setY(10).build();

        mesh.addVertices(v).addVertices(v1).addSegments(s).addPolygons(polygon);

        river.drawRivers(1, polygonList, mesh, 1);
        assertEquals(polygon.getProperties(2).getValue(), "lake"); //because one river with nowhere to go will become a lake
    }
}
