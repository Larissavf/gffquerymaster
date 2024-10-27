/**
 * Processing of the arguments through picocli.
 * Running the app.
 *
 * @author Cheyenne & Larissa
 * @version 1.0
 * @since 25-09-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.Level;
import picocli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CommandLine.Command(name = "gff-filter", mixinStandardHelpOptions = true, version = "1.0")

public class ArgsProcessor implements Runnable {
    private static final Logger logger = LogManager.getLogger(ArgsProcessor.class.getName());

    @CommandLine.Option(names = {"-i", "--input"}, description = "The input file, it needs to be in the version 3 gff format")
    private String inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, description = "The location for the output file, it will be a version 3 gff format \n" +
            "When not specified it will use local direction")
    private String outputFile;

    //filter options
    @CommandLine.Option(names = {"-c", "--columnName"}, description = "-c <columnName>=<filterValue>\n" +
            "column name options: sequenceId, source, featureType, startAndStop\n" +
            "startAndStop=<start>-<stop>")
    private String columnName;


    // todo prio2 -a --attributes
    @CommandLine.Option(names = {"-a", "--attributes"}, description = "-a <attributeName> <attributeValue> \n" +
            "attributes name options: ....")
    private String attributes;

    // todo prio2 -I --inheritance
    @CommandLine.Option(names = {"-I", "--inheritance"}, description = "The parents and children together")
    private boolean inheritance;

    // todo prio3 --summary
    @CommandLine.Option(names = {"--summary"}, description = "Summary containing the following")
    private String summaryOptions;


    @CommandLine.Option(names = {"-v"}, description = "Verbose logging")
    private boolean[] verbose;

    @Override
    public void run() {
        // todo prio1 configfile
        if (inputFile != null) {
            InputFileChecker checker = new InputFileChecker();
            // check file
            if (checker.isValidGFFFile(inputFile)) {
                logger.info("GFF file passed the validation check.");
            } else {
                logger.log(Level.FATAL, "GFF file failed the validation check.");
                System.exit(1);
            }
            //read and filter file
            FileReader readFile = new FileReader();
            if (columnName != null) {
                logger.info("The file gets filtered with the following command " + columnName);
                readFile.parseGFFFile(inputFile, columnName, outputFile);
            } else {
                logger.info("The file doesnt get filtered");
                readFile.parseGFFFile(inputFile, outputFile);
            }
        } else{
            logger.fatal("there's no input file given, please add your file");
            System.exit(1);
        }
//
//        if (verbose.length > 1) {
//            // Set logging to DEBUG
//        } else if (verbose.length > 0) {
//            // Set logging to INFO
//        }

    }
}



