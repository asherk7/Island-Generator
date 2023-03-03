import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // Extracting command line parameters
        String input = args[0];
        String output = args[1];
        //Debug mode
        Boolean debug_mode = false;
        Boolean regular_grid = true;
     
        if (contains("-X", args)){
            debug_mode = true;
        }

        if (contains("-IR", args)){
            regular_grid = false;
        }

        System.out.println("Enter command -H for help with other commands");
        if (contains("-H", args)){
            System.out.println("""
            Commands
            --------
            -IR, irregular mesh
            By default, the regular mesh will be selected. To switch, type said command

            """);
        }

        // Getting width and height for the canvas
        Structs.Mesh aMesh = new MeshFactory().read(input);
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: aMesh.getVerticesList()) {
            // max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            // max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
            max_x = 1000.0;
            max_y = 1000.0;
        }
        // Creating the Canvas to draw the mesh
        Graphics2D canvas = SVGCanvas.build((int) Math.ceil(max_x), (int) Math.ceil(max_y));
        GraphicRenderer renderer = new GraphicRenderer();
        // Painting the mesh on the canvas
        renderer.render(aMesh, canvas, debug_mode, regular_grid);
        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
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
