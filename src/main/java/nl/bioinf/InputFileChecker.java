/**
 * Checking if the given file has the correct setup to be parsed
 *
 *
 * @author Cheyenne & Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileNotFoundException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//todo prio1 log4j

// file validator

public class InputFileChecker {
    /**
     * Checks if the provided file is a GFF file based on its extension and format.
     *
     * @param filePath The path to the file to be checked.
     * @return true if the file is a GFF file, false otherwise.
     */
    private static final Logger logger = LogManager.getLogger(InputFileChecker.class.getName());
    public boolean isValidGFFFile(String filePath) {
        // Check file extension
        if (!filePath.endsWith(".gff")) {
            logger.warn("File is not a .gff file.");
            return false;
        }

        // Check the content format of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String firstLine = reader.readLine();

            if (firstLine != null && firstLine.startsWith("##gff-version")) {
                logger.info("File is a valid GFF file.");
                return true;
            } else {
                logger.warn("File is not a valid GFF file.");
                return false;
            }

        } catch (FileNotFoundException e) {
            // Handle file not found error gracefully
            logger.error("File not found: " + filePath);
            return false;
        } catch (IOException e) {
            // Handle IO error (e.g., read errors)
            logger.error("IO error while reading the file: " + filePath);
            return false;
        } catch (Exception e) {
            // Catch any other unexpected errors
            logger.fatal("An unexpected error occurred: " + filePath + " - " + e.getMessage());
            return false;
        }
    }
}