package ca.mcmaster.cas.se2aa4.a2.generator.geomfilters;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.Polygon;

import java.util.HashSet;
import java.util.Set;

public class ExtractCentroids implements GeometryFilter {

    private final Set<Coordinate> centroids = new HashSet<>();

    public Set<Coordinate> centroids() {
        return centroids;
    }

    @Override
    public void filter(Geometry geometry) {
        if(geometry instanceof Polygon) {
            centroids.add(geometry.getCentroid().getCoordinate());
        }
    }


}
