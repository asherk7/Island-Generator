package ca.mcmaster.cas.se2aa4.a2.generator.export;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.*;
import ca.mcmaster.cas.se2aa4.a2.io.*;

import java.util.*;

public class Exporter {

    public Exporter() {}

    public Structs.Mesh run(Mesh mesh) {
        Structs.Mesh.Builder result = Structs.Mesh.newBuilder();
        Map<Vertex, Integer> vertices = registerVertices(mesh, result);
        Map<PairOfVertex, Integer> segments = registerSegments(mesh, result, vertices);
        registerPolygons(mesh, result, segments, vertices);
        return result.build();
    }

    private void registerPolygons(Mesh mesh, Structs.Mesh.Builder result,
                                  Map<PairOfVertex, Integer> segments, Map<Vertex, Integer> vertices) {
        Map<Polygon, Integer> polygons = buildPolygonRegistry(mesh);
        for(Polygon p: mesh) {
            int centroidIdx = vertices.get(p.centroid());
            List<Integer> segmentIdxs = new ArrayList<>();
            for(PairOfVertex pov: p.hull()) {
                segmentIdxs.add(segments.get(pov));
            }
            List<Integer> neigbhoursIdx = new ArrayList<>();
            for(Polygon n: p.neighbours()){
                neigbhoursIdx.add(polygons.get(n));
            }
            Structs.Polygon exported = Structs.Polygon.newBuilder()
                    .setCentroidIdx(centroidIdx)
                    .addAllSegmentIdxs(segmentIdxs)
                    .addAllNeighborIdxs(neigbhoursIdx).build();
            result.addPolygons(exported);
        }
    }

    private Map<PairOfVertex, Integer> registerSegments(Mesh mesh, Structs.Mesh.Builder result,
                                                        Map<Vertex, Integer> vertices ) {
        Map<PairOfVertex, Integer> segments = buildSegmentRegistry(mesh);
        Structs.Segment[] exported = new Structs.Segment[segments.size()];
        for(PairOfVertex key: segments.keySet()){
            Vertex[] contents = key.contents();
            Structs.Segment s = Structs.Segment.newBuilder()
                    .setV1Idx(vertices.get(contents[0]))
                    .setV2Idx(vertices.get(contents[1])).build();
            exported[segments.get(key)] = s;
        }
        result.addAllSegments(List.of(exported));
        return segments;
    }
    private Map<Vertex, Integer>  registerVertices(Mesh mesh, Structs.Mesh.Builder result) {
        Map<Vertex, Integer> vertices = buildVertexRegistry(mesh);
        Structs.Vertex[] exported = new Structs.Vertex[vertices.size()];
        for(Vertex key: vertices.keySet()){
            Structs.Vertex v = Structs.Vertex.newBuilder().setX(key.x()).setY(key.y()).build();
            exported[vertices.get(key)] = v;
        }
        result.addAllVertices(List.of(exported));
        return vertices;
    }

    private Map<Vertex, Integer> buildVertexRegistry(Mesh mesh) {
        // Creating the set of all vertices
        Set<Vertex> vertices = new HashSet<>();
        for (Polygon p: mesh) {
            for(Vertex v: p) {
                vertices.add(v);
            }
            vertices.add(p.centroid());
        }
        // building the inverse structure (vertex -> index)
        Map<Vertex, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Vertex v: vertices) {
            registry.put(v, counter++);
        }
        return registry;
    }

    private Map<PairOfVertex, Integer> buildSegmentRegistry(Mesh mesh) {
        Set<PairOfVertex> segments = new HashSet<>();
        for(Polygon p: mesh){
            segments.addAll(p.hull());
        }
        Map<PairOfVertex, Integer> registry = new HashMap<>();
        int counter = 0;
        for(PairOfVertex pov: segments) {
            registry.put(pov, counter++);
        }
        return registry;
    }

    private Map<Polygon, Integer> buildPolygonRegistry(Mesh mesh) {
        Map<Polygon, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Polygon p: mesh) {
            registry.put(p, counter++);
        }
        return registry;
    }

}
