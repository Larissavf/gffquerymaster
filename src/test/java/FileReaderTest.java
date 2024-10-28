import nl.bioinf.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileReaderTest {
    private static final String TEST_INPUT_FILE = "test_input.gff";
    private static final String TEST_OUTPUT_FILE = "test_output.gff";
    private FileReader fileReader;

    @BeforeEach
    public void setup() throws IOException {
        // Create a FileReader instance
        fileReader = new FileReader();

        // Mocking the input GFF file
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_INPUT_FILE))) {
            writer.println("# This is a comment line");
            writer.println("chr1\tsource\tfeature\t100\t200\t.\t+\t.\tID=1;Name=GeneA");
            writer.println("chr1\tsource\tfeature\t300\t400\t.\t+\t.\tID=2;Name=GeneB");
        }
    }

    @Test
    public void testParseGFFFile_WithoutFiltering() {
        // Run parseGFFFile without filtering
        fileReader.parseGFFFile(TEST_INPUT_FILE, TEST_OUTPUT_FILE);

        // Validate output file content
        List<String> lines = readLines(TEST_OUTPUT_FILE);
        assertEquals(3, lines.size());  // 1 comment + 2 entries
        assertEquals("# This is a comment line", lines.get(0));
        assertTrue(lines.get(1).contains("GeneA"));
        assertTrue(lines.get(2).contains("GeneB"));
    }

    @Test
    public void testParseGFFFile_WithFiltering() {
        // Run parseGFFFile with a filter on the Name column for "GeneA"
        fileReader.parseGFFFile(TEST_INPUT_FILE, "Name=GeneA", TEST_OUTPUT_FILE);

        // Validate output file content
        List<String> lines = readLines(TEST_OUTPUT_FILE);
        assertEquals(2, lines.size());  // 1 comment + 1 filtered entry
        assertEquals("# This is a comment line", lines.get(0));
        assertTrue(lines.get(1).contains("GeneA"));
    }

    @Test
    public void testParseGFFFile_InvalidFilePath() {
        // Run parseGFFFile with an invalid file path
        String invalidPath = "invalid_path.gff";
        fileReader.parseGFFFile(invalidPath, TEST_OUTPUT_FILE);

        // Check for log output or exception handling (based on logger setup)
        // Here, verify that an exception is handled gracefully
        // This is usually done by checking if the output file is empty or not created
        assertFalse(Files.exists(Path.of(TEST_OUTPUT_FILE)));
    }

    @Test
    public void testParseGFFFile_FilteredButNoMatch() {
        // Run parseGFFFile with a filter that has no matches
        fileReader.parseGFFFile(TEST_INPUT_FILE, "Name=NonExistent", TEST_OUTPUT_FILE);

        // Validate output file content
        List<String> lines = readLines(TEST_OUTPUT_FILE);
        assertEquals(1, lines.size());  // Only comment line should be present
        assertEquals("# This is a comment line", lines.get(0));
    }

    private List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            fail("Failed to read lines from output file: " + e.getMessage());
            return null;
        }
    }
}

