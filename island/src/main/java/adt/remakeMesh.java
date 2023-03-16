package adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import lagoon.lagoonGen;

import java.util.ArrayList;
import java.util.List;

public class remakeMesh {
    private String island;
    public remakeMesh(String island){
        this.island = island;
    }

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

            if (this.island.equals("Lagoon")) {
                lagoonGen assign = new lagoonGen();
                polygon.addProperties(assign.assignBiome(aMesh, p, aMesh.getVerticesList()));
                polygon.addProperties(assign.assignColour(polygon));
            }
            else if (this.island.equals("Island")){
                //input properties for island polygons
            }
            newPolygons.add(polygon.build());
        }

        newMesh.addAllPolygons(newPolygons);
    }

    public void makeSegments(Mesh aMesh, Mesh.Builder newMesh){
        List<Segment> newSegmentList = new ArrayList<>();
        List<Segment> segmentList = aMesh.getSegmentsList();
        for (Segment s : segmentList){
            Segment.Builder segment = Segment.newBuilder();
            segment.setV1Idx(s.getV1Idx()).setV2Idx(s.getV2Idx());
            if(this.island.equals("Island")){
                //input new properties for just island
            }
            newSegmentList.add(segment.build());
        }
        newMesh.addAllSegments(newSegmentList);
    }

    public void makeVertices(Mesh aMesh, Mesh.Builder newMesh){
        List<Vertex> newVertexList = new ArrayList<>();
        List<Vertex> vertexList = aMesh.getVerticesList();
        for (Vertex v : vertexList){
            Vertex.Builder vertex = Vertex.newBuilder();
            vertex.setX(v.getX()).setY(v.getY());
            if(this.island.equals("Island")){
                //input new properties for just island
            }
            newVertexList.add(vertex.build());
        }
        newMesh.addAllVertices(newVertexList);
    }

}
