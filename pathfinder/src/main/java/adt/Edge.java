package adt;

import java.util.HashMap;

public class Edge {
    private final Node n1, n2;
    private final double weight;
    private final HashMap<Object, Object> properties;

    public Edge(Node n1, Node n2){
        this.n1 = n1;
        this.n2 = n2;
        this.weight = setWeight(n1, n2);
        this.properties = new HashMap<>();

    }
    public double setWeight(Node n1, Node n2){
        double x1 = n1.getCoord()[0];
        double y1 = n1.getCoord()[1];
        double x2 = n2.getCoord()[0];
        double y2 = n2.getCoord()[1];
        double distance = Math.sqrt(Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2));
        return distance;
    }

    public Boolean contains(Node n){
        if (n.equals(this.n1) || n.equals(this.n2)){
            return true;
        }
        return false;
    }

    public Node[] getNodes(){
        return new Node[]{this.n1, this.n2};
    }

    public double getWeight(){ return this.weight; }

    public void addProperty(Object o, Object e){
        this.properties.put(o, e);
    }
}
