package nl.bioinf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// bestand validator

public class InputFileChecker {

    private static final Logger logger = Logger.getLogger(InputFileChecker.class.getName());

    /**
     * Checks if the provided file is a GFF file based on its extension and format.
     *
     * @param filePath The path to the file to be checked.
     * @return true if the file is a GFF file, false otherwise.
     */
    public boolean isValidGFFFile(String filePath) {
        // Check file extension
        if (!filePath.endsWith(".gff")) {
            logger.warning("File is not a .gff file.");
            return false;
        }

        // Check the content format of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String firstLine = reader.readLine();

            if (firstLine != null && firstLine.startsWith("##gff-version")) {
                logger.info("File is a valid GFF file.");
                return true;
            } else {
                logger.warning("File is not a valid GFF file.");
                return false;
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reading the file: " + filePath, e);
            return false;
        }
    }

    public static void main(String[] args) {
        InputFileChecker checker = new InputFileChecker();
        String filePath = "/Users/cheyennebrouwer/Documents/24-25/JAVA/ncbi_dataset_gff/ncbi_dataset/data/GCA_000009045.1/genomic.gff";  // Replace with your file path

        if (checker.isValidGFFFile(filePath)) {
            logger.info("GFF file passed the validation check.");
        } else {
            logger.warning("GFF file failed the validation check.");
        }
    }
}