package enricher;

import SoilProfiles.AbsProfile;
import adt.Edge;
import adt.Graph;
import adt.Node;
import adt.generateIsland;
import biomes.Biome;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import enricher.setColor;
import enricher.setElevation;
import lagoon.lagoonGen;
import paths.ShortestPath;
import water.aquiferGen;
import water.humidity;
import water.lakeGen;
import shapes.Shape;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import ElevationProfiles.AltProfile;
import water.riverGen;

public class remakeMesh {
    private String island;
    private Shape<Path2D> shape;
    private AltProfile elevationType;
    private AbsProfile soilType;
    private Biome biome;
    private int lakes = 0;
    private int rivers = 0;
    private int aquifers = 0;
    private int cities = 0;
    private setColor setColor = new setColor();
    public remakeMesh(String island, Shape<Path2D> shape, AltProfile elevationType, AbsProfile soilType, Biome biome, int lakes, int rivers, int aquifers, int cities) {
        this.island = island;
        this.shape = shape;
        this.elevationType = elevationType;
        this.lakes = lakes;
        this.rivers = rivers;
        this.aquifers = aquifers;
        this.soilType = soilType;
        this.biome = biome;
        this.cities = cities;
    }

    public Mesh newMeshBuilder(Mesh aMesh){
        
        Mesh.Builder newMesh = Mesh.newBuilder();
        makeVertices(aMesh, newMesh);
        makeSegments(aMesh, newMesh);
        makePolygons(aMesh, newMesh);
        makeCities(aMesh, newMesh, this.cities);

        return newMesh.build();
    }

    public void makeCities(Mesh aMesh, Mesh.Builder mesh, int cities){
        CreateCities city = new CreateCities();
        Graph graph = city.makeGraph(mesh);
        List<Node> nodes = graph.getNodeList();
        //make graph give nodes and edges properties
        //generate number of cities and assign it to random vertices that aren't lakes or oceans
        //have one capital city, and multiple other cities of random sizes
        //give vertices a property of it being a city
        //get the capital city node, and the list of other cities and use it with pathfinder
        //give edges a property of non-water(not bordered with lakes) and use those edges in shortest path
        //give vertices a colour and size property for rendering
        //update graphic renderer for vertices and edges
        city.getPath(graph, mesh, nodes.get(0), nodes);

    }

    public void makePolygons(Mesh aMesh, Mesh.Builder newMesh){
        List<Structs.Polygon.Builder> newPolygons = new ArrayList<>();
        List<Polygon> meshPolygonsList = aMesh.getPolygonsList();
        List<Segment.Builder> riverList = new ArrayList<>();

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
        setElevation atltitudeGen = new setElevation();
        lakeGen lakeGenerator = new lakeGen();
        riverGen riverGenerator = new riverGen();
        aquiferGen aquiferGenerator = new aquiferGen();
        humidity humidGen = new humidity();

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
            atltitudeGen.setElevProfile(this.elevationType, newPolygons);
            humidGen.setHumidity(newPolygons);
            if (this.lakes != 0) {
                lakeGenerator.drawLakes(this.lakes, newPolygons, soilType.lakeSize());
            }
            if (this.rivers != 0){
                riverList = riverGenerator.drawRivers(this.rivers, newPolygons, 5);
                for (Segment.Builder s : riverList){
                    setColor.assignColor(s);
                }
            }
            if (this.aquifers != 0) {
                aquiferGenerator.drawAquifers(this.aquifers, newPolygons);
            }
            soilType.absorption(newPolygons);
            biome.assignBiome(newPolygons);
            setColor.assignColor(newPolygons);
        }
        for(Polygon.Builder p: newPolygons){
            newMesh.addPolygons(p.build());
        }
        for (Segment.Builder s:riverList){
            newMesh.addSegments(s.build());
        }
    }

    public void makeSegments(Mesh aMesh, Mesh.Builder newMesh){
        List<Segment> newSegmentList = new ArrayList<>();
        List<Segment> segmentList = aMesh.getSegmentsList();

        for (Segment s : segmentList){
            Segment.Builder segment = Segment.newBuilder();
            segment.setV1Idx(s.getV1Idx()).setV2Idx(s.getV2Idx());
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
            newVertexList.add(vertex.build());
        }
        newMesh.addAllVertices(newVertexList);
    }

}
