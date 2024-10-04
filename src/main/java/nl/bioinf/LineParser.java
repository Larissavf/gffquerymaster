package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


// Goes through all the lines, makes the gene or reference object
//how to make a boom structuur
public class LineParser {

    /**
     * Parses through the GFF file line by line and extracts information.
     *
     * @param filePath The path to the GFF file to be parsed.
     */
    private static final Logger logger = LogManager.getLogger(LineParser.class.getName());
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
}