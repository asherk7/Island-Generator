package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        //step 2: polygon references the index's of neighbour(sides and diagonal)
        //step 3: reference centroid vertex index (create it)
        //step 4: give each polygon a colour(average of segments?)
        //step 5: add thickness and transparency? can maybe ignore thickness
        //step 6: each polygon should list its segments consecutively(probably for spencer)

        List<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
            }
        }
        //creating segments
        List<Segment> segments = new ArrayList<>();
        for (int i=0; i < vertices.size(); i++) {
            if ((i + 1) % 25 != 0) {
                Segment s1 = Segment.newBuilder().setV1Idx(i).setV2Idx(i + 1).build();
                segments.add(s1);
            }
        }
        for (int i=0; i<vertices.size(); i++) {
            if(i<600){
                Segment s2 = Segment.newBuilder().setV1Idx(i).setV2Idx(i+25).build();
                segments.add(s2);
            }
        }
        //creating polygons
        List<Polygon> polygons = new ArrayList<>();
        for(int i=0, j=0; i < segments.size(); i++, j++){
            if (i<576) {
                if(i%24==0) j += 1;
                //using the loop to go through the first set of segments which go down
                //created a new variable j to track the segments that go from left to right, since there's one more per column
                Polygon p1 = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(i + 24).addSegmentIdxs(j + 600).addSegmentIdxs(j + 601).build();
                polygons.add(p1);
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        List<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }
        // distributing colours to the segments
        List<Segment> segmentsWithColors = new ArrayList<>();
        for (Segment s: segments){
            Vertex vertex1 = verticesWithColors.get(s.getV1Idx());
            Vertex vertex2 = verticesWithColors.get(s.getV2Idx());

            String[] split1 = getColorVal(vertex1.getPropertiesList());
            String[] split2 = getColorVal(vertex2.getPropertiesList());
            int red1 = Integer.parseInt(split1[0]);
            int green1 = Integer.parseInt(split1[1]);
            int blue1 = Integer.parseInt(split1[2]);
            int red2 = Integer.parseInt(split2[0]);
            int green2 = Integer.parseInt(split2[1]);
            int blue2 = Integer.parseInt(split2[2]);

            int red3 = (red1+red2)/2;
            int green3 = (green1+green2)/2;
            int blue3 = (blue1+blue2)/2;

            String colorCode1 = red3+","+green3+","+blue3;
            Property color1 = Property.newBuilder().setKey("rgb_color").setValue(colorCode1).build();
            Segment coloredSegment = Segment.newBuilder(s).addProperties(color1).build();
            segmentsWithColors.add(coloredSegment);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();
    }

    private String[] getColorVal(List<Property> properties){
        String color = null;
        for (Property p: properties){
            if (p.getKey().equals("rgb_color")){
                color = p.getValue();
            }
        }
        if (color == null){
            color = "0,0,0";
        }
        return color.split(",");
    }

}
