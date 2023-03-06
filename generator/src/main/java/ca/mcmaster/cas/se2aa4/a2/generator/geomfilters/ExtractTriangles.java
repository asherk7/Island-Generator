package ca.mcmaster.cas.se2aa4.a2.generator.geomfilters;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.Vertex;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;

import java.util.*;

public class ExtractTriangles implements GeometryFilter {
    @Override
    public void filter(Geometry geometry) {
        if (geometry instanceof org.locationtech.jts.geom.Polygon) {
            process((org.locationtech.jts.geom.Polygon) geometry);
        }
    }

    private Map<Vertex, Set<Vertex>> links = new HashMap<>();

    public Map<Vertex, Set<Vertex>> getLinks() {
        return links;
    }

    private void process(org.locationtech.jts.geom.Polygon g) {
        // Triangles are closed (first and last are the same) => we remove the last element
        Coordinate[] triangle = Arrays.copyOfRange(g.getCoordinates(), 0, g.getCoordinates().length - 1);
        register(triangle[0], triangle[1]);
        register(triangle[1], triangle[2]);
        register(triangle[2], triangle[0]);
    }

    private void register(Coordinate c1, Coordinate c2) {
        Vertex v1 = new Vertex((float) c1.x, (float) c1.y);
        Vertex v2 = new Vertex((float) c2.x, (float) c2.y);
        link(v1, v2);
    }

    private void link(Vertex v1, Vertex v2) {
        Set<Vertex> v1Links = links.getOrDefault(v1, new HashSet<>());
        v1Links.add(v2);
        links.put(v1, v1Links);
        Set<Vertex> v2Links = links.getOrDefault(v2, new HashSet<>());
        v2Links.add(v1);
        links.put(v2, v2Links);
    }
}
