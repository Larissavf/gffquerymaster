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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


// Goes through all the lines makes feature object witch gets combined in a linked list
// extra list with the hereditary of the objects
public class FileReader {

    /**
     * Parses through the GFF file line by line and extracts information.
     *
     * @param filePath The path to the GFF file to be parsed.
     */
    private static final Logger logger = LogManager.getLogger(FileReader.class.getName());
    private List<LineSeparator> gffFile;
    //todo prio2 overload constructor with just the different combinations with the filtering
    public List<LineSeparator> parseGFFFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    continue;
                    //todo prio1 onthoud voor output
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);

                gffFile.add(separatedRow);
            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: " + filePath, e);
        }
        return gffFile;
    }

    public List<LineSeparator> parseGFFFile(String filePath, String columnName) {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    continue;
                    //todo prio1 onthoud voor output
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);
                // if line is correct to the filtered value
                if (Filter.ColumnName(separatedRow, columnName)){
                    gffFile.add(separatedRow);
                }

            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: " + filePath, e);
        }
        return gffFile;
    }
}
