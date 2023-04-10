package adt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private final double x, y;
    private final Set<Integer> neighbours;
    private final HashMap<String, String> properties;

    public Node(double x, double y){
        this.x = x;
        this.y = y;
        this.neighbours = new HashSet<>();
        this.properties = new HashMap<>();
    }

    public void registerNeighbour(Integer position){
        this.neighbours.add(position);
    }

    public Set<Integer> getNeighbours(){ return this.neighbours; }

    public Double[] getCoord(){
        Double[] coords = new Double[] { this.x, this.y } ;
        return coords;
    }
}
