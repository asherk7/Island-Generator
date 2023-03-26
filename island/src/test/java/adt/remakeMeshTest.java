package adt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import ElevationProfiles.Volcano;
import SoilProfiles.Sand;
import biomes.Tundra;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import shapes.Circle;

public class remakeMeshTest {
    public remakeMesh remake = new remakeMesh("lagoon", new Circle(), new Volcano(), new Sand(), new Tundra(), 3, 3, 3);

    @Test
    public void testMakePolygons() {
        Structs.Polygon p = Structs.Polygon.newBuilder().setCentroidIdx(0).build();
        Structs.Vertex v = Structs.Vertex.newBuilder().setX(10).setY(10).build();
        Mesh aMesh = Mesh.newBuilder().addPolygons(p).addVertices(v).build();
        Mesh.Builder aMeshBuild = Mesh.newBuilder();
        remake.makePolygons(aMesh, aMeshBuild);
        Structs.Polygon p1 = aMeshBuild.build().getPolygons(0);
        assertEquals(p1.getCentroidIdx(), p.getCentroidIdx());
        assertNotEquals(p1.getCentroidIdx(), 4);
    }

    @Test
    public void testMakeSegments(){
        Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(5).setV2Idx(10).build();
        Mesh aMesh = Mesh.newBuilder().addSegments(s).build();
        Mesh.Builder aMeshBuild = Mesh.newBuilder();
        remake.makeSegments(aMesh, aMeshBuild);
        Structs.Segment s1 = aMeshBuild.build().getSegments(0);
        assertEquals(s.getV1Idx(), s1.getV1Idx());
        assertEquals(s.getV2Idx(), s1.getV2Idx());
        assertNotEquals(s1.getV1Idx(), 13);
    }

    @Test
    public void testMakeVertices(){
        Structs.Vertex v = Structs.Vertex.newBuilder().setX(10).setY(10).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v).build();
        Mesh.Builder aMeshBuild = Mesh.newBuilder();
        remake.makeVertices(aMesh, aMeshBuild);
        Structs.Vertex v1 = aMeshBuild.build().getVertices(0);
        assertEquals(v.getX(), v1.getX());
        assertEquals(v.getY(), v1.getY());
        assertNotEquals(v1.getY(), 34);
    }

}
