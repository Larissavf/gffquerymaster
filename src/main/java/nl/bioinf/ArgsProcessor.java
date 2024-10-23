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
    //todo logging
    private static final Logger logger = LogManager.getLogger(ArgsProcessor.class.getName());

    @CommandLine.Option(names = {"-i", "--input"}, description = "The input file, it needs to be in the version 3 gff format")
    private String inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, description = "The location for the output file, it will be a version 3 gff format \n" +
            "When not specified it will use local direction")
    private String outputFile;

    //filter options
    @CommandLine.Option(names = {"-c", "--columnName"}, description = "-c <columnName>=<filterValue>\n" +
            "column name options: sequenceId, source, featureType, startAndStop\n" +
            "exception -> startAndStop=<start>-<stop> \n" +
            "Want it to accept multiple columns use the following syntax: " +
            "<columnName>=<filterValue>,<columnName>=<filterValue>")
    private String column;


    @CommandLine.Option(names = {"-a", "--attributes"}, description = "-a <attributeName>=<attributeValue> \n" +
            "attributes name options depend on which item you look for in your attributes, so get the option from your file")
    private String attributes;

    @CommandLine.Option(names = {"-I", "--inheritance"}, description = "The parents and children together in the output file")
    private boolean inheritance;

    @CommandLine.Option(names = {"--summary"}, description = "Summary containing the following items: \n" +
            "The amount of features per type, the amount of nucleotides per feature and the different type of attributes in a feature")
    private boolean summary;


    @CommandLine.Option(names = {"-v"}, description = "Verbose logging")
    private boolean[] verbose;

    @Override
    public void run() {
        // create file reader, file path gets determined
        FileReader readFile = new FileReader(inputFile, outputFile);

        // user asked for version with all the children with their parent
        if (inheritance) {
            readFile.setExtended();
            logger.info("The file gets written to the output with the childeren and parents of the correct lines");
        }
        // user asked for a summary
        if (summary) {
            readFile.setSummary();
            logger.info("The summary file gets written to the output");
        }
        // user has given 1 or multiple column filters
        if (column != null) {
            readFile.setColumn(column);
            logger.info("The file gets filtered with the following command " + column);
        }
        // user has given 1 or multiple attribute filters
        if (attributes != null) {
            readFile.setAttribute(attributes);
            logger.info("The file gets filtered with the following command " + attributes);
        }
        // Create objects to read and check the file
        if (inputFile != null) {
            // check file
            if (InputFileChecker.isValidGFFFile(inputFile)) {
                logger.info("GFF file passed the validation check.");
            } else { // Didn't pass the check system exit
                logger.fatal("GFF file failed the validation check.");
                System.exit(1);
            }
            //read and filter file
            readFile.parseGFFFile(inputFile);
        } else{
            //No input file system exit
            logger.fatal("there's no input file given, please add your file");
            System.exit(1);
        }

// todo verbosse
//        if (verbose.length > 1) {
//            // Set logging to DEBUG
//        } else if (verbose.length > 0) {
//            // Set logging to INFO
//        }

    }
}



