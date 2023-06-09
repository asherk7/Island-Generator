package configuration;

import SoilProfiles.AbsProfile;
import SoilProfiles.DrySoil;
import SoilProfiles.Sand;
import SoilProfiles.WetSoil;
import biomes.Tundra;
import biomes.Biome;
import biomes.Canada;
import biomes.Tropical;
import shapes.*;
import ElevationProfiles.AltProfile;
import ElevationProfiles.Hills;
import ElevationProfiles.Plains;
import ElevationProfiles.Volcano;

import java.awt.geom.Path2D;

public class Configuration {
    public String inputFileName, outputFileName, islandMode;
    public AltProfile elevationType;
    public AbsProfile soilType;
    public Biome biome;
    public int lakes = 0;
    public int rivers = 0;
    public int aquifers = 0;
    public int cities = 0;
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

            -i <input filename>               Takes in a mesh built by a generator
            -o <output filename>              Returns new mesh in specified output name
            --mode <island mode>              Based on island choosen, remakeMesh will build a new mesh of specified type
            --shape <shape>                   Creates the shape of the island
            --altitude <elevation profile>    Creates the elevation profile of the island
            --lakes <number>                  Creates the max amount of lakes specified
            --rivers <number>                 Creates the number of rivers specified
            --aquifers <number>               Creates the number of aquifers specified
            --soil <absorption profile>       Creates the soil profile of the island
            --biomes <localization>           Creates an island based on the localization
            --cities <number>                 Creates the number of cities specified

            Types of modes:
                - lagoon
                - island
            Types of shapes:
                - circle
                - rectangle
                - triangle
                - star
            Types of altitude:
                - volcano
                - hills
                - plains
            Types of soil:
                - sand
                - wetsoil
                - drysoil
            Types of biomes:
                - tundra
                - tropical
                - canada
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
                } else if (returnString(args, "--shape").equals("star")) {
                    this.shape = new Star();
                }
            }
            //Fifth argument will be Altitude
            if (contains(args, "--altitude")) {
                if (returnString(args, "--altitude").equals("volcano")) {
                    this.elevationType = new Volcano();
                } else if (returnString(args, "--altitude").equals("hills")) {
                    this.elevationType = new Hills();
                } else if (returnString(args, "--altitude").equals("plains")) {
                    this.elevationType = new Plains();
                }
            }

            if (contains(args, "--soil")) {
                if (returnString(args, "--soil").equals("sand")) {
                    this.soilType = new Sand();
                }
                else if (returnString(args, "--soil").equals("drysoil")) {
                    this.soilType = new DrySoil();
                }
                else if (returnString(args, "--soil").equals("wetsoil")) {
                    this.soilType = new WetSoil();
                }
            }

            if (contains(args, "--biomes")) {
                if (returnString(args, "--biomes").equals("tundra")) {
                    this.biome = new Tundra();
                } else if (returnString(args, "--biomes").equals("tropical")) {
                    this.biome = new Tropical();
                } else if (returnString(args, "--biomes").equals("canada")) {
                    this.biome = new Canada();
                }
            }

            //Seventh argument will be lake amount
            if (contains(args, "--lakes")) {
                this.lakes = Integer.parseInt(returnString(args, "--lakes"));
            }
            if (contains(args, "--cities")) {
                this.cities = Integer.parseInt(returnString(args, "--cities"));
            }
            if (contains(args, "--rivers")) {
                this.rivers = Integer.parseInt(returnString(args, "--rivers"));
            }

            if (contains(args, "--aquifers")) {
                this.aquifers = Integer.parseInt(returnString(args, "--aquifers"));
            }
        }
    }
    public String outFile(){ return this.outputFileName; }
    public String inFile(){ return this.inputFileName; }
    public String islandType(){ return this.islandMode; }
    public Shape<Path2D> getShape(){ return this.shape; }
    public AltProfile altitude(){ return this.elevationType; }
    public AbsProfile getSoilType(){return this.soilType; }
    public Biome getBiome(){ return this.biome; }
    public int getLakes() { return this.lakes; }
    public int getRivers() { return this.rivers; }
    public int getAquifers() { return this.aquifers; }
    public int getCities(){ return this.cities; }

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
