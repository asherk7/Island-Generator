package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.util.ArrayList;
import java.util.List;

public class MeshADT {
    private Mesh mesh;
    private List<Polygon> polygonList;
    private List<Segment> segmentList;
    private List<Vertex> vertexList;
    //constructor accepting all necessary lists to create a new mesh.
    public MeshADT(List<Vertex> vertices, List<Segment> segments, List<Polygon> polygons) {
        this.polygonList = polygons;
        this.segmentList = segments;
        this.vertexList = precisionCheck(vertices, 2);
        this.mesh = Mesh.newBuilder()
                .addAllVertices(this.vertexList)
                .addAllSegments(this.segmentList)
                .addAllPolygons(this.polygonList).build();
    }
    //round all values assoc. with vertices to 2 decimal places
    private List<Vertex> precisionCheck(List<Vertex> vertices, int precision) {
        List<Vertex> preciseVertexList = new ArrayList<>();
        for (Vertex v: vertices) {
            preciseVertexList.add(Vertex.newBuilder()
                    .setX(Double.parseDouble(String.format("%." + precision + "f", v.getX())))
                    .setY(Double.parseDouble(String.format("%." + precision + "f", v.getY())))
                    .addAllProperties(v.getPropertiesList()).build());
        }
        //polygons + segments store only integer values, no need to round.
        return preciseVertexList;
    }
    public Mesh getMesh() {return this.mesh;}

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
            d.newBuilderForType().addProperties(blackColorProperty).build();
            debugSegments.add(d);
        }

        for (Polygon s: this.polygonList){
            Polygon d = Polygon.newBuilder().build();
            for (Property p: s.getPropertiesList()){
                if (!(p.getKey() == "rgb_color")){
                    d.newBuilderForType().addProperties(p).build();
                }
            }
            d.newBuilderForType().addProperties(blackColorProperty).build();
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
            d.newBuilderForType().addProperties(blackColorProperty).build();
            debugVertices.add(d);
            i++;
        }
        //Create new instance of mesh class with these properties.
        MeshADT debugMesh = new MeshADT(debugVertices, debugSegments, debugPolygons);
        return debugMesh.getMesh();
    }
}

