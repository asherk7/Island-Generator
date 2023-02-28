import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DotGen generator = new DotGen();
        /*  Mesh myMesh = generator.generate(); */
        Mesh myMesh =  generator.generateVoronoi();
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }
}

/*
 CLI Args:
    - Kind of mesh: grid or irregular
    - Number of polygons
    - Relaxation level
    - Help command
 
 */