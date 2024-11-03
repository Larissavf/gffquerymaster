/**
 * Test for InputFilChecker
 * @author Cheyenne
 * @version 1.0
 * @since
 */

import nl.bioinf.InputFileChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class InputFileCheckerTest {
    private InputFileChecker inputFileChecker;
    private Path validGffFile;
    private Path invalidGffFile;
    private Path nonGffFile;
    private Path missingFile;

    @BeforeEach
    public void setup() throws IOException {
        // Initialize the InputFileChecker instance
        inputFileChecker = new InputFileChecker();

        // Create a temporary valid GFF file
        validGffFile = Files.createTempFile("valid", ".gff");
        Files.writeString(validGffFile, "##gff-version 3\nchr1\tsource\tgene\t100\t200\t.\t+\t.\tID=1;Name=GeneA");

        // Create an invalid GFF file without the required GFF version header
        invalidGffFile = Files.createTempFile("invalid", ".gff");
        Files.writeString(invalidGffFile, "chr1\tsource\tgene\t100\t200\t.\t+\t.\tID=1;Name=GeneA");

        // Create a non-GFF file with an incorrect extension
        nonGffFile = Files.createTempFile("non_gff", ".txt");
        Files.writeString(nonGffFile, "##gff-version 3\nchr1\tsource\tgene\t100\t200\t.\t+\t.\tID=1;Name=GeneA");

        // Path to a missing file (does not exist)
        missingFile = Path.of("non_existent.gff");
    }

    @Test
    public void testIsValidGFFFile_ValidGffFile() {
        // Test with a valid GFF file
        boolean result = inputFileChecker.isValidGFFFile(validGffFile.toString());
        assertTrue(result, "Expected the file to be identified as a valid GFF file.");
    }

    @Test
    public void testIsValidGFFFile_InvalidGffFile() {
        // Test with an invalid GFF file (missing GFF version header)
        boolean result = inputFileChecker.isValidGFFFile(invalidGffFile.toString());
        assertFalse(result, "Expected the file to be identified as an invalid GFF file due to missing header.");
    }

    @Test
    public void testIsValidGFFFile_NonGffFile() {
        // Test with a non-GFF file (wrong extension)
        boolean result = inputFileChecker.isValidGFFFile(nonGffFile.toString());
        assertFalse(result, "Expected the file to be identified as an invalid GFF file due to incorrect extension.");
    }

    @Test
    public void testIsValidGFFFile_FileNotFound() {
        // Test with a file that does not exist
        boolean result = inputFileChecker.isValidGFFFile(missingFile.toString());
        assertFalse(result, "Expected the function to handle file not found gracefully and return false.");
    }

    @Test
    public void testIsValidGFFFile_IOError() {
        // Test for handling IO exceptions by creating a file and then deleting it before checking
        try {
            Path tempFile = Files.createTempFile("tempfile", ".gff");
            Files.writeString(tempFile, "##gff-version 3\n");

            // Delete the file to simulate an IO error
            Files.delete(tempFile);

            // Attempt to check the deleted file
            boolean result = inputFileChecker.isValidGFFFile(tempFile.toString());
            assertFalse(result, "Expected the function to handle IO errors gracefully and return false.");
        } catch (IOException e) {
            fail("Failed to create or delete temporary file for IO error test.");
        }
    }
}

