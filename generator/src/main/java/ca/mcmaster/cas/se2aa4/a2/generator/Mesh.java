package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Mesh {
    private Structs.Mesh mesh;

    public Mesh(Structs.Mesh mesh){
        this.mesh = mesh;
    }

    public Mesh(String filename) throws IOException {
        this.mesh = Structs.Mesh.parseFrom(new FileInputStream(filename));
    }

    //need method to check precision

    //Enter debug mode, segments + polygons change to black, vertices to red
    public void debug() throws IOException {
        for (Structs.Segment s: this.mesh.getSegmentsList()) {
            setColor(s.getPropertiesList(), "255,255,255");
        }
        for (Structs.Polygon p: this.mesh.getPolygonsList()) {
            setColor(p.getPropertiesList(), "255,255,255");
        }
        for (Structs.Vertex v: this.mesh.getVerticesList()) {
            setColor(v.getPropertiesList(), "255,0,0");
        }
        //update the mesh file, in same fashion as the meshfactory
        update("sample.mesh");
    }

    private void setColor(List<Structs.Property> properties, String colorCode) {
        for (Structs.Property p: properties) {
            if (p.getKey() == "rgb_color")
                p.toBuilder().setValue(colorCode).build();
        }
    }

    private void update(String fileName) throws IOException {
        this.mesh.writeTo(new FileOutputStream(fileName));
    }
}

