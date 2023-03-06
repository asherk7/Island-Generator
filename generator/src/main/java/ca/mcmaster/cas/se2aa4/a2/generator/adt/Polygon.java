package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import java.util.*;
import java.util.Objects;

public class Polygon implements Cropable<Polygon>, Iterable<Vertex> {
    private final List<Vertex> hull;

    private final Set<Polygon> neighbors;

    public Polygon() {
        this(new ArrayList<>());
    }

    private Polygon(List<Vertex> hull) {
        this.hull = hull;
        this.neighbors = new HashSet<>();
    }

    public void add(Vertex v) {
        this.hull.add(v);
    }

    public void registerAsNeighbour(Polygon p) {
        this.neighbors.add(p);
    }

    public Set<Polygon> neighbours() {
        return this.neighbors;
    }

    public Polygon crop(float maxX, float maxY) {
        List<Vertex> cropped = new ArrayList<>();
        for (Vertex v: this.hull){
            cropped.add(v.crop(maxX, maxY));
        }
        return new Polygon(cropped);
    }

    public Vertex centroid() {
        float xs = 0.0f, ys = 0.0f;
        for (Vertex v: this.hull) {
            xs += v.x();
            ys += v.y();
        }
        return new Vertex(xs/this.hull.size(), ys/this.hull.size());
    }

    public List<PairOfVertex> hull() {
        List<PairOfVertex> result = new ArrayList<>();
        Iterator<Vertex> it = this.hull.iterator();
        Vertex start = it.next();
        Vertex current = start;
        while(it.hasNext()) {
            Vertex next = it.next();
            result.add(new PairOfVertex(current, next));
            current = next;
        }
        result.add(new PairOfVertex(current, start));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return hull.equals(polygon.hull);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hull);
    }

    @Override
    public Iterator<Vertex> iterator() {
        return this.hull.iterator();
    }

    @Override
    public String toString() {
        return "Polygon(" +centroid()+ "," + hull + ", "+ this.neighbors.size() +")";
    }
}
