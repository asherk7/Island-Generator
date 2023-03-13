import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.util.ArrayList;
import java.util.List;

public class remakeMesh {

    public Mesh newMeshBuilder(Mesh aMesh){
        
        Mesh.Builder newMesh = Mesh.newBuilder();
        makeVertices(aMesh, newMesh);
        makeSegments(aMesh, newMesh);
        makePolygons(aMesh, newMesh);
        
        return newMesh.build();
    }

    public void makePolygons(Mesh aMesh, Mesh.Builder newMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        List<Polygon> polygonList = aMesh.getPolygonsList();
        for (Polygon p: polygonList){
            Polygon.Builder polygon = Polygon.newBuilder();
            //neighbour, centroid, segments
        }

        newMesh.addAllPolygons(newPolygons);
    }

    public void makeSegments(Mesh aMesh, Mesh.Builder newMesh){

        newMesh.addAllSegments();
    }

    public void makeVertices(Mesh aMesh, Mesh.Builder newMesh){

        newMesh.addAllVertices();
    }

}
