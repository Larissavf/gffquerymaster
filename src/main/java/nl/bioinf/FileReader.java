/**
 * This is a JavaDoc comment for a file
 *
 * This file contains the main application logic for our program.
 *
 * @author Your Name
 * @version 1.0
 * @since 2023-02-20
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


// Goes through all the lines makes feature object witch gets combined in a linked list
// extra list with the heridatory of the objects
public class FileReader {

    /**
     * Parses through the GFF file line by line and extracts information.
     *
     * @param filePath The path to the GFF file to be parsed.
     */
    private static final Logger logger = LogManager.getLogger(FileReader.class.getName());
    private List<LineSeparator> gffFile;
    public void parseGFFFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);
                gffFile.add(separatedRow);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while parsing the GFF file: " + filePath, e);
        }
    }
