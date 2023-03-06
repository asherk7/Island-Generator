package ca.mcmaster.cas.se2aa4.a2.generator.geomfilters;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ToPolygons implements GeometryFilter {
    private Set<Polygon> results = new HashSet<>();

    @Override
    public void filter(Geometry geometry) {
        if (geometry instanceof org.locationtech.jts.geom.Polygon) {
            transform((org.locationtech.jts.geom.Polygon) geometry);
        }
    }

    public Set<Polygon> transformed() {
        return results;
    }

    private void transform(org.locationtech.jts.geom.Polygon p) {
        Geometry hull = p.convexHull();
        Coordinate[] coords = Arrays.copyOfRange(hull.getCoordinates(), 0, hull.getCoordinates().length - 1);
        Polygon transformed = new Polygon();
        for(Coordinate c: coords){
            transformed.add(new Vertex((float) c.x, (float) c.y));
        }
        results.add(transformed);
    }
}
