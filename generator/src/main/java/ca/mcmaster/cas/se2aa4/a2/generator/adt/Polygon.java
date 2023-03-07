package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

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

    public Structs.Property assignBiome(Polygon p, Mesh aMesh){
        Vertex centroid = p.centroid();
        float x = centroid.x();
        float y = centroid.y();

        int width = aMesh.width;
        int height = aMesh.height;

        int r1 = (2*width)/5;
        int r2 = (width)/4;

        //put formula and check stuff
        double circle = Math.pow((x - (width / 2.0)), 2) + Math.pow((y - (height / 2.0)), 2);
        if (circle <= Math.pow(r2, 2)){
            return Structs.Property.newBuilder().setKey("Biome").setValue("Lagoon").build();
        }
        else if ((circle <= Math.pow(r1,2)) && (circle > Math.pow(r2,2))){
            return Structs.Property.newBuilder().setKey("Biome").setValue("Land").build();
        }
        else if (circle > Math.pow(r1,2)){
            return Structs.Property.newBuilder().setKey("Biome").setValue("Ocean").build();
        }
        else {
            return null;
        }
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
