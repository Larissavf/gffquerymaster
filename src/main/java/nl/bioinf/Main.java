/**
 * starting point of the app
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ArgsProcessor()).execute(args);
        System.exit(exitCode);
    }



}