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
import org.apache.logging.log4j.core.config.Configurator;
import picocli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Set up commandline for use of picocli
 */
@CommandLine.Command(name = "gff-filter", mixinStandardHelpOptions = true, version = "1.0")

public class ArgsProcessor implements Runnable {
    private static final Logger logger = LogManager.getLogger(ArgsProcessor.class.getName());

    @CommandLine.Option(names = {"-i", "--input"}, description = "The input file, it needs to be in the version 3 gff format")
    private String inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, description = "The location for the output file, it will be a version 3 gff format \n" +
            "When not specified it will use local direction")
    private String outputFile;

    //filter options
    @CommandLine.Option(names = {"-c", "--columnName"}, description = """
            -c <columnName>=<filterValue>
            column name options: sequenceId, source, featureType, startAndStop
            exception -> startAndStop=<start>-<stop>\s
            Want it to accept multiple columns use the following syntax: \
            <columnName>=<filterValue>,<columnName>=<filterValue>""")
    private String column;


    @CommandLine.Option(names = {"-a", "--attributes"}, description = "-a <attributeName>=<attributeValue> \n" +
            "attributes name options depend on which item you look for in your attributes, so get the option from your file")
    private String attributes;

    @CommandLine.Option(names = {"-I", "--inheritance"}, description = "The parents and children together in the output file")
    private boolean inheritance;

    @CommandLine.Option(names = {"-E", "--exact"}, description = "The value needs to be exactly in or the same as the filter value")
    private boolean exactMatch;

    @CommandLine.Option(names = {"--summary"}, description = "Summary containing the following items: \n" +
            "The amount of features per type, the amount of nucleotides per feature and the different type of attributes in a feature")
    private boolean summary;


    @CommandLine.Option(names = {"-v"}, description = "Verbose logging")
    private boolean[] verbose = new boolean[0];

    /**
     * to start the application, putting the right information in the right spots
     */
    @Override
    public void run() {
        try{
            activateProgram();
        } catch (UnsupportedOperationException e) {
            logger.info("Check the log for the error");
        } catch (Exception e) {
            logger.fatal("there's been a unprepared error{}", e.getMessage());
        }

    }

    private void activateProgram() {
        if (inputFile == null) {
            //No input file system exit
            logger.fatal("there's no input file given, please add your file");
            throw new UnsupportedOperationException("No input file given");
        }
        // create file reader, file path gets determined
        FileReader readFile = new FileReader(inputFile, outputFile);

        //set all the possible given features
        possibleFilterSetters(readFile);

        // Create objects to read and check the file
        creatingFile(readFile);
    }

    /**
     * starts parsing the file
     * @param readFile FileReader object that's going to parse through the file
     */
    private void creatingFile(FileReader readFile) {
        // check file
        if (InputFileChecker.isValidGFFFile(inputFile)) {
            logger.info("GFF file passed the validation check.");
        } else { // Didn't pass the check system exit
            logger.fatal("GFF file failed the validation check.");
            throw new UnsupportedOperationException("GFF file failed the validation check.");
        }
        //read and filter file
        readFile.parseGFFFile(inputFile);


    }

    /**
     * Sets all the values of all the possible filters that van be given from the user.
     * @param readFile FileReader object that's going to parse through the file
     */
    private void possibleFilterSetters(FileReader readFile) {
        // user asked for version with all the children with their parent
        if (inheritance) {
            readFile.setExtended();
            logger.info("The file gets written to the output with the children and parents of the correct lines");
        }
        // user asked for a summary
        if (summary) {
            readFile.setSummary();
            logger.info("The summary file gets written to the output");
        }
        // user has given 1 or multiple column filters
        if (column != null) {
            readFile.setColumn(column);
            logger.info("The file gets filtered with the following command {}", column);
        }
        // user has given 1 or multiple attribute filters
        if (attributes != null) {
            readFile.setAttribute(attributes);
            logger.info("The file gets filtered with the following command {}", attributes);
        }
        // user wanted the exact values
        if (exactMatch) {
            readFile.setExactMatch();
        }
        // user wanted a wide logging
        if (verbose.length > 1) {
            // Set logging to DEBUG
            Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
        } else if (verbose.length > 0) {
            // Set logging to INFO
            Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);
        }
        }
}



