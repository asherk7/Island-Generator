package ca.mcmaster.cas.se2aa4.a2.generator.geomfilters;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.GeometryFilter;

import java.util.HashSet;
import java.util.Set;

public class BoundingBox implements GeometryFilter {

    private static GeometryFactory factory =
            new GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(10));

    private final Geometry boundingBox;
    private final Set<Geometry> clipped = new HashSet<>();

    public BoundingBox(int w, int h) {
        this.boundingBox = factory.createPolygon(new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(0, h),
                new Coordinate(w, h), new Coordinate(w, 0),
                new Coordinate(0, 0)});
    }

    public Geometry[] contents() {
        return clipped.toArray(new Geometry[]{});
    }

    @Override
    public void filter(Geometry geometry) {
        if (geometry instanceof org.locationtech.jts.geom.Polygon) {
            clipped.add(geometry.intersection(boundingBox));
        }

    }
}
