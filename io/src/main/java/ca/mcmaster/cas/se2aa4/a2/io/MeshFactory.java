package ca.mcmaster.cas.se2aa4.a2.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MeshFactory {

    public Structs.Mesh read(String filename) throws IOException {
        return Structs.Mesh.parseFrom(new FileInputStream(filename));
    }

    public void write(Structs.Mesh m, String fileName) throws IOException {
        m.writeTo(new FileOutputStream(fileName));
    }

}