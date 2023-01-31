package ca.mcmaster.cas.se2aa4.a2.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MeshFactoryTest {

    private final MeshFactory factory = new MeshFactory();

    @Test
    public void writeMeshToFile() throws IOException {
        Path path = Files.createTempFile("mesh-",".mesh");
        assertEquals(Files.size(path),0);
        Structs.Mesh aMesh = buildRandomMesh();
        factory.write(aMesh, path.toAbsolutePath().toString());
        assertNotEquals(Files.size(path),0);
    }

    @Test
    public void writeAndRead() throws IOException {
        Path path = Files.createTempFile("mesh-",".mesh");
        Structs.Mesh aMesh = buildRandomMesh();
        factory.write(aMesh, path.toAbsolutePath().toString());
        Structs.Mesh fromDisk = factory.read(path.toAbsolutePath().toString());
        assertEquals(aMesh,fromDisk);
    }

    private Structs.Mesh buildRandomMesh() {
        Random bag = new Random();
        // random vertices:
        List<Structs.Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < bag.nextInt(10,50); i++){
            Structs.Vertex v = Structs.Vertex.newBuilder()
                                    .setX(bag.nextDouble(0.0, 1000.0))
                                    .setY(bag.nextDouble(0.0, 1000.0))
                                .build();
            vertices.add(v);
        }
        // random segments:
        List<Structs.Segment> segments = new ArrayList<>();
        for (int i = 0; i < bag.nextInt(10,50); i++) {
            int v1_idx = bag.nextInt(vertices.size());
            int v2_idx = bag.nextInt(vertices.size());
            Structs.Segment s = Structs.Segment.newBuilder().setV1Idx(v1_idx).setV2Idx(v2_idx).build();
            segments.add(s);
        }

        Structs.Mesh result = Structs.Mesh.newBuilder()
                                    .addAllVertices(vertices)
                                    .addAllSegments(segments)
                                .build();
        return result;
    }

}
