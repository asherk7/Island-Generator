import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex.Builder;

import java.util.ArrayList;
import java.util.List;

public class remakeMesh {

    public Mesh newMeshBuilder(Mesh aMesh){
        
        Mesh.Builder newMesh = Mesh.newBuilder();
        makeVertices(aMesh, newMesh);
        makeSegments(aMesh, newMesh);
        makePolygons(aMesh, newMesh);
        
        return newMesh.build();
    }

    public void makePolygons(Mesh aMesh, Mesh.Builder newMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        List<Polygon> polygonList = aMesh.getPolygonsList();
        for (Polygon p: polygonList){
            Polygon.Builder polygon = Polygon.newBuilder();

            polygon.addAllNeighborIdxs(p.getNeighborIdxsList());
            polygon.setCentroidIdx(p.getCentroidIdx());
            polygon.addAllSegmentIdxs(p.getSegmentIdxsList());

            islandGen assign = new islandGen();
            polygon.addProperties(assign.assignBiome(aMesh, p, aMesh.getVerticesList()));
            polygon.addProperties(assign.assignColour(polygon));

            newPolygons.add(polygon.build());
        }

        newMesh.addAllPolygons(newPolygons);
    }

    public void makeSegments(Mesh aMesh, Mesh.Builder newMesh){
        List<Segment> newSegmentList = new ArrayList<>();
        List<Segment> segmentList = aMesh.getSegmentsList();
        for (Segment s : segmentList){
            newSegmentList.add(Segment.newBuilder().setV1Idx(s.getV1Idx()).setV2Idx(s.getV2Idx()).build());
        }
        newMesh.addAllSegments(newSegmentList);
    }

    public void makeVertices(Mesh aMesh, Mesh.Builder newMesh){
        List<Vertex> newVertexList = new ArrayList<>();
        List<Vertex> vertexList = aMesh.getVerticesList();
        for (Vertex v : vertexList){
            newVertexList.add(Vertex.newBuilder().setX(v.getX()).setY(v.getY()).build());
        }
        newMesh.addAllVertices(newVertexList);
    }

}
