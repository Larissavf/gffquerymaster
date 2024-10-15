/**
 * to read a file line for line, filter the line, and write it to the output file
 *
 *
 * @author Cheyenne & Larissa
 * @version 1.0
 * @since 5-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileReader {
    private static final Logger logger = LogManager.getLogger(FileReader.class.getName());
    private List<LineSeparator> gffFile = new ArrayList<>();
    private List<String> startGffFile = new ArrayList<>();

    /**
     * Parses through the GFF file line by line and extracts information.
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     * @param outputFilePath the path to the gff file needs to be stored
     */
    //todo prio2 overload constructor with just the different combinations with the filtering
    public void parseGFFFile(String inputFilePath, String outputFilePath) {
        // create end write product
        OutputWriter product = new OutputWriter(inputFilePath, outputFilePath);
        product.createTheFile();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);
                product.writeOutput(separatedRow);
            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: " + inputFilePath, e);
        }
    }
    /**
     * Parses through the GFF file line by line and extracts information.
     * Filters the line based on the column name and what value it should be equal to.
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     * @param columnName the object with the column name and the value it's supposed to equal to
     * @param outputFilePath The path the gff file will be written to
     */

    public void parseGFFFile(String inputFilePath, String columnName, String outputFilePath) {
        // create end write product
        OutputWriter product = new OutputWriter(inputFilePath, outputFilePath);
        product.createTheFile();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);
                // if line is correct to the filtered value
                if (Filter.ColumnName(separatedRow, columnName)){
                    product.writeOutput(separatedRow);
                }

            }
            logger.info("The file is read and filtered");
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: " + inputFilePath, e);
        }
    }
}
