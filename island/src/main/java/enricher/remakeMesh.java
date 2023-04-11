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
import lagoon.lagoonGen;
import water.aquiferGen;
import water.humidity;
import water.lakeGen;
import shapes.Shape;

import java.awt.geom.Path2D;
import java.util.*;

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
    Random rnd = new Random();

    HashMap<Vertex, Set<Vertex>> polygonNeighbours;

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
        this.polygonNeighbours = new HashMap<>();
    }

    public Mesh newMeshBuilder(Mesh aMesh){
        
        Mesh.Builder newMesh = Mesh.newBuilder();
        makeVertices(aMesh, newMesh);
        makeSegments(aMesh, newMesh);
        makePolygons(aMesh, newMesh);
        makeCities(newMesh, this.cities);

        return newMesh.build();
    }

    public void makeCities(Mesh.Builder newMesh, int cities){
        CreateCities city = new CreateCities();

        for (Polygon p: newMesh.getPolygonsList()){
            Vertex v = newMesh.getVertices(p.getCentroidIdx());
            Set<Vertex> n = new HashSet<>();
            for (Integer i: p.getNeighborIdxsList()){
                n.add(newMesh.getVertices(newMesh.getPolygonsList().get(i).getCentroidIdx()));
            }
            this.polygonNeighbours.put(v, n);
        }

        List<Vertex> specialVertices = city.findValidVertices(newMesh); //vertices that aren't a lake or ocean
        List<Integer> city_positions = new ArrayList<>();

        int i = 0; //assigning the city property to random vertices
        while (i<cities){
            Vertex rand_city = specialVertices.get(rnd.nextInt(specialVertices.size()));
            if (!city_positions.contains(specialVertices.indexOf(rand_city))){
                city_positions.add(specialVertices.indexOf(rand_city));
                i += 1;
            }
        }

        int capital = city_positions.get(rnd.nextInt(city_positions.size()));
        for (Integer integer : city_positions){
            int vertexplacement = newMesh.getVerticesList().indexOf(specialVertices.get(integer));
            Vertex cityVertex = newMesh.getVerticesList().get(vertexplacement);
            Vertex.Builder temp = cityVertex.toBuilder();
            Structs.Property cityproperty;
            if (integer != capital) {
                cityproperty = Structs.Property.newBuilder().setKey("City").setValue(String.valueOf(rnd.nextInt(5) + 10)).build();
            }
            else{
                cityproperty = Structs.Property.newBuilder().setKey("City").setValue("20").build();
            }
            temp.addProperties(cityproperty);
            newMesh.setVertices(vertexplacement, temp.build());
        }

        Graph graph = city.makeGraph(newMesh, specialVertices, this.polygonNeighbours);
        for (Integer integer: city_positions){
            if (integer != capital) {
                city.makePath(graph, newMesh, graph.getNode(capital), graph.getNode(integer)); //creating the path
            }
        }

        for (Vertex v: newMesh.getVerticesList()){
            Vertex.Builder v1 = v.toBuilder();
            setColor.assignColor(v1);
            newMesh.setVertices(newMesh.getVerticesList().indexOf(v), v1.build());
        }
        for (Segment s: newMesh.getSegmentsList()){
            Segment.Builder s1 = s.toBuilder();
            setColor.assignColor(s1);
            newMesh.setSegments(newMesh.getSegmentsList().indexOf(s), s1.build());
        }
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
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            segment.setV1Idx(v1).setV2Idx(v2);
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
