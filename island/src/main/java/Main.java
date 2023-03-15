import java.io.IOException;

import com.google.protobuf.Empty;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class Main {
    
    public static void main(String[] args) throws IOException{
        String inputFileName = "";
        String outputFileName = "";
        String islandMode = "";
        Mesh aMesh;
        
        //Help with commands
        if (contains(args, "-h")){
            System.out.println("""
            Commands
            --------

            -i <input filename>     Takes in a mesh built by a generator
            -o <output filename>    Returns new mesh in specified output name
            --mode <island mode>    Based on island choosen, remakeMesh will build a new mesh of specified type

            Types of modes:
                - Lagoon
            """);
            return;
        }

        //First argument will be the input filename
        if (contains(args, "-i")){
            inputFileName = returnString(args, "-i");
        }
        try{
            aMesh = new MeshFactory().read(inputFileName);
        } catch (Exception e) {
            System.out.println("No such input file");
            return;
        }
        
        remakeMesh createNewMesh = new remakeMesh();

        //Second argument will be output filename
        if (contains(args, "-o")){
            outputFileName = returnString(args, "-o");
        }

        //Third argument will be island mode
        //Automatically configure lagoon. As more islands are updated, adjust code to accept different modes
        Mesh newMesh = createNewMesh.newMeshBuilder(aMesh);
        if (contains(args, "--mode")){
            if (returnString(args, "--mode").equals("lagoon")){
                System.out.println("Building Lagoon island");
            } else {
                System.out.println("Mode not found");
                return;
            }
        }
        //Create new mesh, and set to output file
        new MeshFactory().write(newMesh, outputFileName);
        
    }

    public static boolean contains(String[] args, String check){
        for (String arg: args){
            if (arg.equals(check)){
                return true;
            }
        } return false;
    }

    public static String returnString(String[] args, String command){
        for (int i = 0; i < args.length; i++){
            if (args[i].equals(command)){
                return args[i+1];
            }
        } return null;
    }

}
