package ca.mcmaster.cas.se2aa4.a2.generator.neighborhoud;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.Mesh;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Neighborhood {

    private final Map<Polygon, Set<Polygon>> neighbors;
    private final Map<Vertex, Polygon> registry;

    protected Neighborhood() {
        this.neighbors = new HashMap<>();
        this.registry = new HashMap<>();
    }

    public final Set<Polygon> neighbors(Polygon p) {
        return neighbors.getOrDefault(p, Set.of());
    }

    public final void build(Set<Polygon> polygons) {
        buildRegistry(polygons);
        computeRelations();
    }

    protected abstract void computeRelations();

    protected final void register(Polygon p, Set<Polygon> neighbors) {
        neighbors.forEach(n -> register(p,n));
    }

    protected final Set<Vertex> registeredCentroids() {
        return this.registry.keySet();
    }

    protected final Polygon polygonAt(Vertex centroid) {
        return this.registry.get(centroid);
    }



    protected final void register(Polygon p1, Polygon p2) {
        Set<Polygon> p1Neighbors = neighbors.getOrDefault(p1, new HashSet<>());
        p1Neighbors.add(p2);
        neighbors.put(p1, p1Neighbors);
        Set<Polygon> p2Neighbors = neighbors.getOrDefault(p2, new HashSet<>());
        p2Neighbors.add(p1);
        neighbors.put(p2, p2Neighbors);
    }

    private void buildRegistry(Set<Polygon> polygons) {
        polygons.forEach(p -> registry.put(p.centroid(), p));
    }






}
