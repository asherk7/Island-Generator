package adt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private final double x, y;
    private final Set<Integer> neighbours;

    public Node(double x, double y){
        this.x = x;
        this.y = y;
        this.neighbours = new HashSet<>();
    }

    public void registerNeighbour(List<Integer> positions){
        this.neighbours.addAll(positions);
    }

    public Set<Integer> getNeighbours(){ return this.neighbours; }

    public Double[] getCoord(){
        Double[] coords = new Double[] { this.x, this.y } ;
        return coords;
    }
}
