package adt;

import java.util.*;

public class Node {
    private final double x, y;
    private final Set<Integer> neighbours;
    private final HashMap<Object, Object> properties;

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

    public void addProperty(Object o, Object e){
        this.properties.put(o, e);
    }
}
