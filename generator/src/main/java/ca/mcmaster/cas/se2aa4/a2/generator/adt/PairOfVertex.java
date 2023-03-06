package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PairOfVertex {

    private final Set<Vertex> vertices;

    public PairOfVertex(Vertex v1, Vertex v2) {
        this.vertices = new HashSet<>();
        vertices.add(v1);
        vertices.add(v2);
    }

    public Vertex[] contents() {
        return this.vertices.toArray(new Vertex[]{});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairOfVertex that = (PairOfVertex) o;
        return vertices.equals(that.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }
}
