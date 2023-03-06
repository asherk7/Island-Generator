package ca.mcmaster.cas.se2aa4.a2.generator.neighborhoud;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.Vertex;

import java.util.HashSet;
import java.util.Set;

public class GridNeighborhood extends Neighborhood {

    private final int size;

    public GridNeighborhood(int size) {
        this.size = size;
    }

    @Override
    protected void computeRelations() {
        for (Vertex c: this.registeredCentroids()) {
            Polygon current = this.polygonAt(c);
            for (Vertex potential: potentialNeighbors(c)) {
                Polygon neighbor = this.polygonAt(potential);
                if (neighbor != null){
                    this.register(current, neighbor);
                }
            }
        }
    }

    private Set<Vertex> potentialNeighbors(Vertex c) {
        Set<Vertex> result = new HashSet<>();
        result.add(new Vertex(c.x()-size, c.y()-size));
        result.add(new Vertex(c.x()-size, c.y()));
        result.add(new Vertex(c.x()-size, c.y()+size));
        result.add(new Vertex(c.x(), c.y()-size));
        result.add(new Vertex(c.x(), c.y()+size));
        result.add(new Vertex(c.x()+size, c.y()-size));
        result.add(new Vertex(c.x()+size, c.y()));
        result.add(new Vertex(c.x()+size, c.y()+size));
        return result;
    }
}
