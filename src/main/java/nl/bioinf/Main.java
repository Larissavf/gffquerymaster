/**
 * starting point of the app
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

/**
 * Main
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    /**
     * the start of it all
     * @param args String array containing the arguments put in by the user
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new ArgsProcessor()).execute(args);
            System.exit(exitCode);

    }



}