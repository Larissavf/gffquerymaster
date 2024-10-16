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
    // todo prio2 multiple columns
    @CommandLine.Option(names = {"-c", "--columnName"}, description = "-c <columnName>=<filterValue>\n" +
            "column name options: sequenceId, source, featureType, startAndStop\n" +
            "exception -> startAndStop=<start>-<stop> \n" +
            "Want it to accept multiple columns use the following syntax: " +
            "<columnName>=<filterValue>,<columnName>=<filterValue>")
    private String column;


    @CommandLine.Option(names = {"-a", "--attributes"}, description = "-a <attributeName>=<attributeValue> \n" +
            "attributes name options depend on which item you look for in your attributes, so get the option from your file", split = "=")
    private String[] attributes;

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
        // Create objects to read and check the file
        FileReader readFile = new FileReader();

        if (inheritance) {readFile.setExtended();}
        if (inputFile != null) {
            // check file
            if (InputFileChecker.isValidGFFFile(inputFile)) {
                logger.info("GFF file passed the validation check.");
            } else {
                logger.log(Level.FATAL, "GFF file failed the validation check.");
                System.exit(1);
            }
            //read and filter file
            if (column != null) {
                logger.info("The file gets filtered with the following command " + column);
                readFile.parseGFFFile(inputFile, column, outputFile);
            } else if(attributes != null) {
                logger.info("The file gets filtered with the following command " + attributes);
                readFile.parseGFFFile(inputFile, attributes, outputFile);
            } else if(column != null & attributes != null) {
                logger.info("The file gets filtered with the following command " + attributes + "and" + column);
                readFile.parseGFFFile(inputFile, attributes, column, outputFile);
            }
            else {
                logger.info("The file doesnt get filtered");
                readFile.parseGFFFile(inputFile, outputFile);
            }
        } else{
            logger.fatal("there's no input file given, please add your file");
            System.exit(1);
        }

        //if extende if gene remeber until next gene.
//
//        if (verbose.length > 1) {
//            // Set logging to DEBUG
//        } else if (verbose.length > 0) {
//            // Set logging to INFO
//        }

    }
}



