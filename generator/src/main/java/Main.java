import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import java.util.Scanner;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DotGen generator = new DotGen();
        Scanner my_Scanner = new Scanner(System.in);
        Mesh myMesh;
        int num_of_polygons = 50;
        int relaxation_level = 1000;
        System.out.println("Enter command -H for help with other commands");
        if (contains("-H", args)){
            System.out.println("""
            Commands
            --------
            -N, number of polygons
            Users will be able to determine how many polygons they wish to add to the irregular mesh

            -RL, number of relaxation levels
            Users will be able to determine the number of levels the voronoi diagram will pass through

            -IR, irregular mesh
            By default, the regular mesh will be selected. To switch, type said command

            """);
        }

        if (contains("-N", args)) {
            System.out.println("How many polygons do you wish to enter? ");
            String result1 = my_Scanner.nextLine();
            try{
                num_of_polygons = Integer.valueOf(result1);
            } catch (Exception e){
                System.out.println("Not an integer! Default will be used");
            }
        }

        if (contains("-RL", args)){
            System.out.println("How many relaxation levels do you wish to enter? ");
            String result2 = my_Scanner.nextLine();
            try{
                relaxation_level = Integer.valueOf(result2);
            } catch (Exception e){
                System.out.println("Not an integer! Default will be used");
            }

        }

        if (contains("-IR", args)){
            myMesh =  generator.generateVoronoi(num_of_polygons, relaxation_level);
        } else{
            myMesh = generator.generate();
        }

        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }

    public static boolean contains(String compare, String[] args){
        for (int i = 0; i < args.length; i++){
            if (compare.equals(args[i])){
                return true;
            }
        }
        return false;
    }
}

/*
 CLI Args:
    - Kind of mesh: grid or irregular DONE
    - Number of polygons: Done
    - Relaxation level
    - Help command
 
 */