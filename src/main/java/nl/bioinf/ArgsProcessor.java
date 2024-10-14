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

    @CommandLine.Option(names = {"-o", "--output"}, description = "The location for the output file, it will be a version 3 gff format")
    private String outputFile;

    //filter options
    @CommandLine.Option(names = {"-c", "--columnName"}, description = "-c <columnName> <filterValue>\n" +
            "column name options: sequenceId, source, featureType, startAndStop\n" +
            "startAndStop <start>,<stop>")
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
        InputFileChecker checker = new InputFileChecker();
        String filePath = "/Users/cheyennebrouwer/Documents/24-25/JAVA/ncbi_dataset_gff/ncbi_dataset/data/GCA_000009045.1/genomic.gff";  // Replace with your file path
        // check file
        if (checker.isValidGFFFile(filePath)) {
            logger.info("GFF file passed the validation check.");
        } else {
            logger.log(Level.FATAL, "GFF file failed the validation check.");
            System.exit(1);
        }
        //read and filter file
        FileReader readFile = new FileReader();
        if (columnName != null) {
            logger.info("The file gets filtered with the following command " + columnName);
            readFile.parseGFFFile(filePath, columnName);
        }else {
            logger.info("The file doesnt get filtered");
            readFile.parseGFFFile(filePath);}

//
//        if (verbose.length > 1) {
//            // Set logging to DEBUG
//        } else if (verbose.length > 0) {
//            // Set logging to INFO
//        }

    }
}



