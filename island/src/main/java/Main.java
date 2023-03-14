import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class Main {
    
    public static void main(String[] args) throws IOException{
        //First argument will be the input filename
        Structs.Mesh aMesh = new MeshFactory().read(args[0]);
        remakeMesh createNewMesh = new remakeMesh();

        Mesh newMesh = createNewMesh.newMeshBuilder(aMesh);
        new MeshFactory().write(newMesh, "img/lagoon.mesh");
        
    }

}
