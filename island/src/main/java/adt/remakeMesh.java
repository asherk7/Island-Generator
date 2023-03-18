package adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import lagoon.lagoonGen;
import shapes.Shape;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class remakeMesh {
    private String island;
    private Shape<Path2D> shape;
    public remakeMesh(String island, Shape<Path2D> shape){
        this.island = island;
        this.shape = shape;
    }

    public Mesh newMeshBuilder(Mesh aMesh){
        
        Mesh.Builder newMesh = Mesh.newBuilder();
        makeVertices(aMesh, newMesh);
        makeSegments(aMesh, newMesh);
        makePolygons(aMesh, newMesh);
        
        return newMesh.build();
    }

    public void makePolygons(Mesh aMesh, Mesh.Builder newMesh){
        List<Structs.Polygon.Builder> newPolygons = new ArrayList<>();
        List<Polygon> meshPolygonsList = aMesh.getPolygonsList();

        int width = 0;
        int height = 0;
        for (Structs.Property property: aMesh.getPropertiesList()){
            if (property.getKey().equals("Width")){
                width = Integer.parseInt(property.getValue());
            }
            if (property.getKey().equals("Height")){
                height = Integer.parseInt(property.getValue());
            }
        }

        lagoonGen lagoon = new lagoonGen();
        generateIsland gen = new generateIsland(width, height);

        for (Polygon p: meshPolygonsList){
            Polygon.Builder polygon = Polygon.newBuilder();

            polygon.addAllNeighborIdxs(p.getNeighborIdxsList());
            polygon.setCentroidIdx(p.getCentroidIdx());
            polygon.addAllSegmentIdxs(p.getSegmentIdxsList());

            Vertex c = aMesh.getVerticesList().get(p.getCentroidIdx());
            String x = Double.toString(c.getX());
            String y = Double.toString(c.getY());
            Structs.Property centroid = Structs.Property.newBuilder().setKey("Centroid").setValue(x+","+y).build();
            polygon.addProperties(centroid);

            if (this.island.equals("lagoon")) {
                polygon.addProperties(lagoon.assignBiome(aMesh, p, aMesh.getVerticesList()));
                polygon.addProperties(lagoon.assignColour(polygon));
            }
            newPolygons.add(polygon);
        }

        if (this.island.equals("island")){
            gen.drawIsland(this.shape, newPolygons);
        }

        List<Polygon> polygonList = new ArrayList<>();
        for(Polygon.Builder p: newPolygons){
            polygonList.add(p.build());
        }
        newMesh.addAllPolygons(polygonList);
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
