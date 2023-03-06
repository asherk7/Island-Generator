package ca.mcmaster.cas.se2aa4.a2.generator.configuration;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public static final String WIDTH = "w";
    public static final String HEIGHT = "h";
    public static final String KIND = "k";
    public static final String NB_POLYGONS = "p";
    public static final String SIZE_SQUARES = "s";
    public static final String FILENAME = "o";
    public static final String RELAXATION = "r";
    public static final String DEMO = "d";
    public static final String HELP = "help";

    private CommandLine cli;

    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar generator.jar", options());
        System.exit(0);
    }

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(WIDTH, true, "Width of the Mesh"));
        options.addOption(new Option(HEIGHT, true, "Heigth of the Mesh"));
        options.addOption(new Option(FILENAME, true, "Output file name"));
        options.addOption(new Option(FILENAME, true, "Output file name"));
        options.addOption(new Option(KIND, true, "Kind: grid or irregular"));
        // Regular mesh
        options.addOption(new Option(SIZE_SQUARES, true, "Size of squares (if grid mesh)"));
        // Irregular mesh
        options.addOption(new Option(NB_POLYGONS, true, "Numbers of polygons (if irregular mesh"));
        options.addOption(new Option(RELAXATION, true, "Relaxation coefficient"));
        // Demo mode (filling the mesh with random properties
        options.addOption(new Option(DEMO, false, "activate DEMO mode"));
        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }

}
