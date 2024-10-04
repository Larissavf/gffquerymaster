package nl.bioinf;

// commandline: ... file.gff filterstep

import picocli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Arrays;

@CommandLine.Command(name = "gff-filter", mixinStandardHelpOptions = true, version = "1.0")

public class ArgsProcessor implements Runnable {
    private static final Logger logger = LogManager.getLogger(ArgsProcessor.class.getName());

    @CommandLine.Option(names = {"-i", "--input"}, description = "input file")
    private String inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, description = "output file")
    private String outputFile;

    @CommandLine.Option(names = {"-f", "--filter-value"}, description = "Filter value")
    private String filterOptions;

    @CommandLine.Option(names = {"--summary"}, description = "Summary containing the following")
    private String summaryOptions;

    @CommandLine.Option(names = {"-v"}, description = "Verbose logging")
    private boolean[] verbose;

    // example marcel logging verbose
    @Override
    public void run() {
        // add check if inputfile has something

        InputFileChecker checker = new InputFileChecker();
        String filePath = "/Users/cheyennebrouwer/Documents/24-25/JAVA/ncbi_dataset_gff/ncbi_dataset/data/GCA_000009045.1/genomic.gff";  // Replace with your file path

        // add stop program if false
        if (checker.isValidGFFFile(filePath)) {
            logger.info("GFF file passed the validation check.");
        } else {
            logger.warning("GFF file failed the validation check.");
        }
        // if can be gone:)
        LineParser parser = new LineParser();
        if (checker.isValidGFFFile(filePath)) {
            parser.parseGFFFile(filePath);
        } else {
            logger.warning("Invalid GFF file. Cannot proceed with parsing.");
        }


    }
}



