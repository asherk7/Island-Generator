package ca.mcmaster.cas.se2aa4.a2.visualizer.configuration;

import org.apache.commons.cli.*;

public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String DEBUG = "x";

    private CommandLine cli;
    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.svg");
    }

    public boolean debug() { return this.cli.hasOption(DEBUG); }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (SVG)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(DEBUG, false, "Debug mode"));
        return options;
    }

}
