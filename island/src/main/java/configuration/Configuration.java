package configuration;

import shapes.Circle;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Triangle;

import java.awt.geom.Path2D;

public class Configuration {
    public String inputFileName, outputFileName, islandMode;
    public Shape<Path2D> shape;
    public Configuration(String[] args) {
        this.inputFileName = "";
        this.outputFileName = "";
        this.islandMode = "";

        //Help with commands
        if (contains(args, "-h")){
            System.out.println("""
            Commands
            --------

            -i <input filename>     Takes in a mesh built by a generator
            -o <output filename>    Returns new mesh in specified output name
            --mode <island mode>    Based on island choosen, remakeMesh will build a new mesh of specified type
            --shape <shape>

            Types of modes:
                - lagoon
                - island
                
            Types of shapes:
                - circle
                - rectangle
                - triangle
            """);
            return;
        }

        //First argument will be the input filename
        if (contains(args, "-i")){
            this.inputFileName = returnString(args, "-i");
        }

        //Second argument will be output filename
        if (contains(args, "-o")){
            outputFileName = returnString(args, "-o");
        }

        //Third argument will be island mode
        if (contains(args, "--mode")){
            if (returnString(args, "--mode").equals("lagoon")){
                this.islandMode = "lagoon";
            } else if (returnString(args, "--mode").equals("island")){
                this.islandMode = "island";
            }
        }

        //Fourth argument will be shape
        if (this.islandMode.equals("island")) {
            if (contains(args, "--shape")) {
                if (returnString(args, "--shape").equals("circle")) {
                    this.shape = new Circle();
                } else if (returnString(args, "--shape").equals("rectangle")) {
                    this.shape = new Rectangle();
                } else if (returnString(args, "--shape").equals("triangle")) {
                    this.shape = new Triangle();
                }
            }
        }
    }

    public String outFile(){ return this.outputFileName; }
    public String inFile(){ return this.inputFileName; }
    public String islandType(){ return this.islandMode; }
    public Shape<Path2D> getShape(){ return this.shape; }

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