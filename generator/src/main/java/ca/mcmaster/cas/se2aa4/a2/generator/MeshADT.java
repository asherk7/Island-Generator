package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;

import java.util.ArrayList;
import java.util.List;

public class MeshADT {
    private Mesh mesh;
    private List<Polygon> polygonList;
    private List<Segment> segmentList;
    private List<Vertex> vertexList;
    //constructor accepting all necessary lists to create a new mesh.
    public MeshADT(Mesh aMesh) {
        this.polygonList = aMesh.getPolygonsList();
        this.segmentList = aMesh.getSegmentsList();
        this.vertexList = aMesh.getVerticesList();
        //precisionCheck();
        this.mesh = aMesh;
    }
    public MeshADT(List<Vertex> vertices, List<Segment> segments, List<Polygon> polygons) {
        this.polygonList = polygons;
        this.segmentList = segments;
        this.vertexList = vertices;
        //precisionCheck();
        this.mesh = Mesh.newBuilder().addAllVertices(this.vertexList).addAllSegments(this.segmentList).addAllPolygons(this.polygonList).build();
    }

    //precision is ensured at the generator level, using all int values (for now)
    /*
    private void precisionCheck() {
        List<Polygon> precisePolygons = new ArrayList<>();
        for (Polygon g: this.polygonList) {
            Polygon n = Polygon.newBuilder().build();

        }
    }

     */
    public Mesh getMesh() {return this.mesh;}

    //need method to check precision

    //Enter debug mode, segments + polygons change to black, vertices to red
    public Mesh debug() {
        List<Segment> debugSegments = new ArrayList<>();
        List<Vertex> debugVertices = new ArrayList<>();
        List<Polygon> debugPolygons = new ArrayList<>();
        Property blackColorProperty = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();

        for (Segment s: this.segmentList){
            Segment d = Segment.newBuilder().build();
            for (Property p: s.getPropertiesList()){
                if (!(p.getKey() == "rgb_color")){
                    d.newBuilderForType().addProperties(p).build();
                }
            }
            d.newBuilderForType().addProperties(blackColorProperty);
            debugSegments.add(d);
        }

        for (Polygon s: this.polygonList){
            Polygon d = Polygon.newBuilder().build();
            for (Property p: s.getPropertiesList()){
                if (!(p.getKey() == "rgb_color")){
                    d.newBuilderForType().addProperties(p).build();
                }
            }
            d.newBuilderForType().addProperties(blackColorProperty);
            debugPolygons.add(d);
        }

        int i = 0;
        while (i < 625){
            Vertex d = Vertex.newBuilder().build();
            for (Property p: vertexList.get(i).getPropertiesList()){
                if (!(p.getKey() == "rgb_color")){
                    d.newBuilderForType().addProperties(p).build();
                }
            }
            d.newBuilderForType().addProperties(blackColorProperty);
            debugVertices.add(d);
            i++;
        }
        //Create new instance of mesh class with these properties.
        MeshADT debugMesh = new MeshADT(debugVertices, debugSegments, debugPolygons);
        return debugMesh.getMesh();
    }
}

