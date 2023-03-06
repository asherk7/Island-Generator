package ca.mcmaster.cas.se2aa4.a2.generator.specification;

import ca.mcmaster.cas.se2aa4.a2.generator.PrecisionModel;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.*;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.generator.geomfilters.BoundingBox;
import ca.mcmaster.cas.se2aa4.a2.generator.geomfilters.ExtractCentroids;
import ca.mcmaster.cas.se2aa4.a2.generator.geomfilters.ToPolygons;
import ca.mcmaster.cas.se2aa4.a2.generator.neighborhoud.DelaunayNeighbourhood;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.*;

public class IrregularSpecification implements Buildable {

    private final int width, height, nbTiles, relax;
    public IrregularSpecification(int width, int height, int nbTiles, int relax) {
        this.nbTiles = nbTiles;
        this.relax = relax;
        this.width = width;
        this.height = height;
    }

     IrregularSpecification(Map<String, String> options){
        this(Integer.parseInt(options.getOrDefault(Configuration.WIDTH, "500")),
                Integer.parseInt(options.getOrDefault(Configuration.HEIGHT, "1000")),
                Integer.parseInt(options.getOrDefault(Configuration.NB_POLYGONS, "200")),
                Integer.parseInt(options.getOrDefault(Configuration.RELAXATION, "10")));
    }

    @Override
    public Mesh build() {
        Set<Coordinate> seeds = initialize();
        Set<Polygon> polygons = generate(this.relax, seeds);
        Mesh result = new Mesh(this.width, this.height);
        for (Polygon p: polygons){
            result.register(p);
        }
        result.populateNeighbours(new DelaunayNeighbourhood());
        return result;
    }

    private Set<Coordinate> initialize() {
        Random bag = new Random();
        Set<Coordinate> seeds = new HashSet<>();
        for(int i = 0; i < this.nbTiles; i++){
            seeds.add(new Coordinate(bag.nextFloat(0, this.width), bag.nextFloat(0, this.height)));
        }
        return seeds;
    }

    private Set<Polygon> generate(int relaxation, Set<Coordinate> sites) {
        GeometryCollection result = tesselate(sites);
        if (relaxation == 0) {
            ToPolygons transformer = new ToPolygons();
            result.apply(transformer);
            return transformer.transformed();
        } else {
            ExtractCentroids extractor = new ExtractCentroids();
            result.apply(extractor);
            return generate(relaxation - 1, extractor.centroids());
        }
    }

    private GeometryCollection tesselate(Set<Coordinate> sites) {
        VoronoiDiagramBuilder builder = new VoronoiDiagramBuilder();
        builder.setSites(sites);
        Geometry result = builder.getDiagram(PrecisionModel.FACTORY);
        BoundingBox clipper = new BoundingBox(this.width, this.height);
        result.apply(clipper);
        GeometryCollection clipped = PrecisionModel.FACTORY.createGeometryCollection(clipper.contents());
        return clipped;
    }


}
