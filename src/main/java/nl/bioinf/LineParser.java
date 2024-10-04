package nl.bioinf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Goes through all the lines, makes the gene or reference object
    //how to make a boom structuur
public class LineParser {

    private static final Logger logger = Logger.getLogger(LineParser.class.getName());

    /**
     * Parses through the GFF file line by line and extracts information.
     *
     * @param filePath The path to the GFF file to be parsed.
     */
    public void parseGFFFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");

                // Ensure there are at least 9 columns as per GFF3 format
                if (columns.length >= 9) {
                    logger.info("Sequence ID: " + columns[0]);
                    logger.info("Source: " + columns[1]);
                    logger.info("Feature Type: " + columns[2]);
                    logger.info("Start: " + columns[3]);
                    logger.info("End: " + columns[4]);
                    logger.info("Attributes: " + columns[8]);
                    logger.info("--------------------------------");
                } else {
                    logger.warning("Malformed line with insufficient columns: " + line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while parsing the GFF file: " + filePath, e);
        }
    }

    public static void main(String[] args) {
        LineParser parser = new LineParser();
        String filePath = "/Users/cheyennebrouwer/Documents/24-25/JAVA/ncbi_dataset_gff/ncbi_dataset/data/GCA_000009045.1/genomic.gff";  // Replace with your GFF file path

        InputFileChecker checker = new InputFileChecker();
        if (checker.isValidGFFFile(filePath)) {
            parser.parseGFFFile(filePath);
        } else {
            logger.warning("Invalid GFF file. Cannot proceed with parsing.");
        }
    }
}