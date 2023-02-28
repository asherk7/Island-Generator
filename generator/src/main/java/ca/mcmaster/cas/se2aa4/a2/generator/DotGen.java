package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.*;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.noding.SegmentSetMutualIntersector;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

public class DotGen {
    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    public Mesh generateVoronoi(int polyCount, int relaxation_level) {
        //generate voronoi diagram mesh, currently uncoloured.
        Random rand = new Random();
        List<Vertex> centroids = new ArrayList<>();
        List<Coordinate> vertexCoords = new ArrayList<>();
        //generate random vertex coords
        for (int i = 0; i < polyCount; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            vertexCoords.add(new Coordinate(x,y));
        }

        //test coords
        for (Coordinate c: vertexCoords){
            System.out.print("(" + c.x + ", " + c.y + ")" + ", " );
        } System.out.println("\n\n\n");
        
        //generate voronoi diagram using jts voronoi diagram builder
        VoronoiDiagramBuilder voronoiBuilder = new VoronoiDiagramBuilder();
        voronoiBuilder.setTolerance(0.0001);
        voronoiBuilder.setClipEnvelope(new Envelope(0,width,0,height));
        GeometryFactory geometryFactory = new GeometryFactory();
        voronoiBuilder.setSites(geometryFactory.createMultiPointFromCoords(vertexCoords.toArray(new Coordinate[0])));
        GeometryCollection polygons = (GeometryCollection) voronoiBuilder.getDiagram(geometryFactory);

        //Initalize arraylists
        List<Vertex> polyVertexList = new ArrayList<>();
        List<Segment> polySegmentList = new ArrayList<>();
        List<Polygon> polygonList = new ArrayList<>();
        List<Integer> polySegmentIdxList = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();

        //Relaxation
        for (int loop = 0; loop < relaxation_level; loop++){
            //Reset arraylist
            polyVertexList = new ArrayList<>();
            polySegmentList = new ArrayList<>();
            polygonList = new ArrayList<>();
            polySegmentIdxList = new ArrayList<>();
            vertices = new ArrayList<>();
            segments = new ArrayList<>();
            centroids = new ArrayList<>();

            //Store all necessary values for each polygon
            for (int i = 0; i < polygons.getNumGeometries(); i++) {
                //get coordinates of current polygon, parse to vertices
                Coordinate[] polyCoords = polygons.getGeometryN(i).getCoordinates();
                for (Coordinate c: polyCoords) {
                    polyVertexList.add(Vertex.newBuilder().setX(c.getX()).setY(c.getY()).build());
                }
                for (int k = 1; k < polyVertexList.size()-1; k++) {
                    polySegmentList.add(Segment.newBuilder().setV1Idx(k-1+vertices.size()).setV2Idx(k+vertices.size()).build());
                }
                polySegmentList.add(Segment.newBuilder().setV1Idx(polyVertexList.size()+vertices.size()-1).setV2Idx(vertices.size()).build());
                vertices.addAll(polyVertexList);//vertices list has all vertices, indexes should be correct.
                polyVertexList.clear();
                for (int j = 0; j < polySegmentList.size()-1; j++) {
                    polySegmentIdxList.add(j+segments.size());
                }
                segments.addAll(polySegmentList);
                polySegmentList.clear();
                centroids.add(Vertex.newBuilder()
                        .setX(polygons.getGeometryN(i).getCentroid().getX())
                        .setY(polygons.getGeometryN(i).getCentroid().getY())
                        .build());
                polygonList.add(Polygon.newBuilder().setCentroidIdx(i).addAllSegmentIdxs(polySegmentIdxList).build());
            }

            //Move inital point to centroid
            for (int i = 0; i < vertexCoords.size(); i++){
                Vertex current_centroid = centroids.get(i);
                double x_val = current_centroid.getX();
                double y_val = current_centroid.getY();
                //test centroid coords
                System.out.print("(" + x_val + "," + y_val + ")" + ", ");
                vertexCoords.set(i, new Coordinate(x_val,y_val));
            }System.out.println("\n\n\n");

            //test new coordinates
            for (Coordinate c: vertexCoords){
                System.out.print("(" + c.x + ", " + c.y + ")" + ", " );
            } System.out.println("\n\n\n");

            //Apply relaxation again
            voronoiBuilder.setTolerance(0.0001);
            voronoiBuilder.setClipEnvelope(new Envelope(0,width,0,height));
            voronoiBuilder.setSites(geometryFactory.createMultiPointFromCoords(vertexCoords.toArray(new Coordinate[0])));
            polygons = (GeometryCollection) voronoiBuilder.getDiagram(geometryFactory);
        }

        System.out.println(centroids.size());
        System.out.println(vertexCoords.size());
        System.out.println(polygonList.size());

        System.out.println(vertices.size());
        System.out.println(centroids.size());

        Mesh aMesh = new MeshADT(vertices,centroids,segments,polygonList).getMesh();
        return DelauneyTriangulation(aMesh);
    }

    public Mesh DelauneyTriangulation(Mesh aMesh){
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> coords = new ArrayList<>();
        for(int i =0; i <= aMesh.getPolygonsList().size(); i++) {
            Polygon p = aMesh.getPolygonsList().get(i);
            Vertex centroid = aMesh.getVerticesList().get(p.getCentroidIdx()+625);
            coords.add(new Coordinate(centroid.getX(), centroid.getY()));
        }
        DelaunayTriangulationBuilder triangleBuilder = new DelaunayTriangulationBuilder();
        triangleBuilder.setSites(coords);
        Geometry triangles = triangleBuilder.getTriangles(geometryFactory);
        List<Geometry> trianglesProduced = new ArrayList<>();
        if(triangles instanceof GeometryCollection geometryCollection) {
            for (int i = 0; i < geometryCollection.getNumGeometries(); i++) {
                Geometry triangle = geometryCollection.getGeometryN(i);
                trianglesProduced.add(triangle);
            }
        }
        List<Polygon> polygons = aMesh.getPolygonsList();
        for(Polygon p: polygons){
            for(int i=0; i<trianglesProduced.size(); i++){
                Coordinate[] coord = trianglesProduced.get(i).getCoordinates();
                for(int j=0; j<coord.length; j++){
                    Vertex centroid = aMesh.getVerticesList().get(p.getCentroidIdx()+625);
                    if (coord[j].getX() == centroid.getX() && coord[j].getY() == centroid.getY()){
                        for(Polygon p1: polygons){
                            Vertex centroid1 = aMesh.getVerticesList().get(p1.getCentroidIdx()+625);
                            if(p1 != p){
                                for (int k=0; k<coord.length; k++) {
                                    if (coord[k].getX() == centroid1.getX() && coord[k].getY() == centroid1.getY()) {
                                        if (!p.getNeighborIdxsList().contains(polygons.indexOf(p1))) {
                                            Polygon p2 = Polygon.newBuilder(p).addNeighborIdxs(polygons.indexOf(p1)).build();
                                            polygons.set(polygons.indexOf(p), p2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new MeshADT(aMesh.getVerticesList().subList(0, 625), aMesh.getVerticesList().subList(625, aMesh.getVerticesCount()), aMesh.getSegmentsList(), polygons).getMesh();
    }

    public Mesh generate() {

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
                if(i%24==0 && i!= 0) j += 1;
                //using the loop to go through the first set of segments which go down
                //created a new variable j to track the segments that go from left to right, since there's one more per column
                Polygon p1 = Polygon.newBuilder().addSegmentIdxs(i).addSegmentIdxs(j + 600).addSegmentIdxs(i + 24).addSegmentIdxs(j + 601).build();
                //segments are added consecutively
                polygons.add(p1);
            }
        }
        //creating centroids with color red and adding to polygons
        List<Vertex> centroids = new ArrayList<>();
        for(Polygon p: polygons){
            //add both x, divide by 2, add both y, divide by 2, that's the centroid
            Segment s1 = Segment.newBuilder(segments.get(p.getSegmentIdxs(0))).build(); //left segment
            Segment s2 = Segment.newBuilder(segments.get(p.getSegmentIdxs(2))).build(); //right segment

            Vertex v1 = Vertex.newBuilder(vertices.get(s1.getV1Idx())).build(); //top left
            Vertex v2 = Vertex.newBuilder(vertices.get(s2.getV1Idx())).build(); //top right
            Vertex v3 = Vertex.newBuilder(vertices.get(s1.getV2Idx())).build(); //bottom left

            double x1 = v1.getX();
            double x2 = v2.getX();
            double y1 = v1.getY();
            double y2 = v3.getY();

            double x3 = (x1+x2)/2.0;
            double y3 = (y1+y2)/2.0;

            Vertex centroid = Vertex.newBuilder().setX(x3).setY(y3).build();
            String colorCode = 255 + "," + 0 + "," + 0;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex centroid_red = Vertex.newBuilder(centroid).addProperties(color).build();
            centroids.add(centroid_red);
            //add the centroid created to the polygon in loop
            Polygon p1 = Polygon.newBuilder(p).setCentroidIdx(centroids.indexOf(centroid_red)).build();
            polygons.set(polygons.indexOf(p), p1);
        }
        //referencing neighbours
        for(int i=0; i < polygons.size(); i++){
            //sides: i-1, i+1, i+24, i-24
            //diagonals: i-25, i-23, i+23, i+25
            Polygon p1;
            //corners
            if(i==0) {p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i+1).addNeighborIdxs(i+24).addNeighborIdxs(i+25).build();}
            else if (i==23) {p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-1).addNeighborIdxs(i+24).addNeighborIdxs(i+23).build();}
            else if (i==552) {p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i+1).addNeighborIdxs(i-24).addNeighborIdxs(i-23).build();}
            else if (i==575) {p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-1).addNeighborIdxs(i-24).addNeighborIdxs(i-25).build();}
            else if(i % 24 == 0){
                //top row
                p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-24).addNeighborIdxs(i-23).addNeighborIdxs(i+1).addNeighborIdxs(i+24).addNeighborIdxs(i+25).build();
            }
            else if(i<23){
                //left column
                p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-1).addNeighborIdxs(i+23).addNeighborIdxs(i+24).addNeighborIdxs(i+25).addNeighborIdxs(i+1).build();
            }
            else if(i % 24 == 23){
                //bottom row
                p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-24).addNeighborIdxs(i-25).addNeighborIdxs(i-1).addNeighborIdxs(i+23).addNeighborIdxs(i+24).build();
            }
            else if(i<575 && i > 552){
                //right column
                p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-1).addNeighborIdxs(i-23).addNeighborIdxs(i-24).addNeighborIdxs(i-25).addNeighborIdxs(i+1).build();
            }
            else{
                //every other square not on the border
                p1 = Polygon.newBuilder(polygons.get(i)).addNeighborIdxs(i-1).addNeighborIdxs(i+1).addNeighborIdxs(i-24).addNeighborIdxs(i+24).addNeighborIdxs(i-23).addNeighborIdxs(i-25).addNeighborIdxs(i+23).addNeighborIdxs(i+25).build();
            }
            polygons.set(i, p1);
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

            String colorCode = segmentColorCode(split1, split2);
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment coloredSegment = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(coloredSegment);
        }
        //distributing colours to polygons
        List<Polygon> polygonsWithColors = new ArrayList<>();
        for (Polygon p: polygons){
            Segment s1 = segmentsWithColors.get(p.getSegmentIdxs(0));
            Segment s2 = segmentsWithColors.get(p.getSegmentIdxs(1));
            Segment s3 = segmentsWithColors.get(p.getSegmentIdxs(2));
            Segment s4 = segmentsWithColors.get(p.getSegmentIdxs(3));

            String[] split1 = getColorVal(s1.getPropertiesList());
            String[] split2 = getColorVal(s2.getPropertiesList());
            String[] split3 = getColorVal(s3.getPropertiesList());
            String[] split4 = getColorVal(s4.getPropertiesList());

            String colorCode = polygonColorCode(split1, split2, split3, split4);
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Polygon coloredPolygon = Polygon.newBuilder(p).addProperties(color).build();
            polygonsWithColors.add(coloredPolygon);
        }
        return new MeshADT(verticesWithColors, centroids, segmentsWithColors,polygonsWithColors).getMesh();
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

    private String segmentColorCode(String[] split1, String[] split2){
        int red1 = Integer.parseInt(split1[0]);
        int green1 = Integer.parseInt(split1[1]);
        int blue1 = Integer.parseInt(split1[2]);

        int red2 = Integer.parseInt(split2[0]);
        int green2 = Integer.parseInt(split2[1]);
        int blue2 = Integer.parseInt(split2[2]);

        int red3 = (red1+red2)/2;
        int green3 = (green1+green2)/2;
        int blue3 = (blue1+blue2)/2;

        return red3+","+green3+","+blue3;
    }

    private String polygonColorCode(String[] split1, String[] split2, String[] split3, String[] split4){
        int red1 = Integer.parseInt(split1[0]);
        int green1 = Integer.parseInt(split1[1]);
        int blue1 = Integer.parseInt(split1[2]);

        int red2 = Integer.parseInt(split2[0]);
        int green2 = Integer.parseInt(split2[1]);
        int blue2 = Integer.parseInt(split2[2]);

        int red3 = Integer.parseInt(split3[0]);
        int green3 = Integer.parseInt(split3[1]);
        int blue3 = Integer.parseInt(split3[2]);

        int red4 = Integer.parseInt(split4[0]);
        int green4 = Integer.parseInt(split4[1]);
        int blue4 = Integer.parseInt(split4[2]);

        int red_f = (red1+red2+red3+red4)/4;
        int green_f = (green1+green2+green3+green4)/4;
        int blue_f = (blue1+blue2+blue3+blue4)/4;

        return red_f+","+green_f+","+blue_f;
    }

}
