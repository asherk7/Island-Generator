package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.*;

public class Hull implements Iterable<Structs.Vertex> {
    private Deque<Structs.Vertex> vertices;

    public Hull() {
        this.vertices = new LinkedList<>();
    }

    public void add(Structs.Segment v, Structs.Mesh mesh) {
        Structs.Vertex v1 = mesh.getVertices(v.getV1Idx());
        Structs.Vertex v2 = mesh.getVertices(v.getV2Idx());
        if(this.vertices.isEmpty()) {
            this.vertices.add(v1);
            this.vertices.add(v2);
        } else if(v1.equals(vertices.getFirst())) {
            vertices.addFirst(v2);
        } else if (v1.equals(vertices.getLast())){
            vertices.addLast(v2);
        } else if (v2.equals(vertices.getFirst())) {
            vertices.addFirst(v1);
        } else if (v2.equals(vertices.getLast())) {
            vertices.addLast(v1);
        } else {
            throw new IllegalArgumentException("Non consecutive hull");
        }
    }

    @Override
    public Iterator<Structs.Vertex> iterator() {
        return this.vertices.iterator();
    }
}
