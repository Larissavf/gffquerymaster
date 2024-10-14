package nl.bioinf;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ArgsProcessor()).execute(args);
        System.exit(exitCode);
    }



}