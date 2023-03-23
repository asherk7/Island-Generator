import java.io.IOException;

import adt.remakeMesh;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import configuration.Configuration;

public class Main {
    
    public static void main(String[] args) throws IOException{
        Mesh aMesh;
        Configuration config = new Configuration(args);
        try{
            aMesh = new MeshFactory().read(config.inFile());
        } catch (Exception e) {
            System.out.println("No such input file");
            return;
        }
        remakeMesh createNewMesh = new remakeMesh(config.islandType(), config.getShape(), config.altitude(), config.getLakes(), config.getRivers(), config.getAquifers());
        Structs.Mesh newMesh = createNewMesh.newMeshBuilder(aMesh);
        new MeshFactory().write(newMesh, config.outFile());
    }

}
