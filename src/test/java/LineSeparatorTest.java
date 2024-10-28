import nl.bioinf.LineSeparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LineSeparatorTest {
    private String[] validFeatureLine;
    private String[] incompleteFeatureLine;
    private String[] lineWithInvalidColumns;

    @BeforeEach
    public void setup() {
        // Setup test data for various test cases
        validFeatureLine = new String[]{"chr1", "source", "gene", "100", "200", ".", "+", "0", "ID=gene1;Name=GeneA"};
        incompleteFeatureLine = new String[]{"chr1", "source", "gene", "100", "200", ".", "+", "0"};
        lineWithInvalidColumns = new String[]{"chr1", "source", "gene", "abc", "200", ".", "+", "0", "ID=gene1;Name=GeneA"};
    }

    @Test
    public void testConstructor_ValidFeatureLine() {
        // Test LineSeparator constructor with valid feature line
        LineSeparator lineSeparator = new LineSeparator(validFeatureLine);

        assertEquals("chr1", lineSeparator.getSequenceId());
        assertEquals("source", lineSeparator.getSource());
        assertEquals("gene", lineSeparator.getFeatureType());
        assertEquals(100, lineSeparator.getStartIndex());
        assertEquals(200, lineSeparator.getEndIndex());
        assertEquals('.', lineSeparator.getScore());
        assertEquals('+', lineSeparator.getStrand());
        assertEquals('0', lineSeparator.getFrame());

        // Check parsed attributes
        Map<String, String> attributes = lineSeparator.getAttributes();
        assertEquals(2, attributes.size());
        assertEquals("gene1", attributes.get("ID"));
        assertEquals("GeneA", attributes.get("Name"));
    }

    @Test
    public void testConstructor_IncompleteFeatureLine() {
        // Test LineSeparator constructor with an incomplete feature line
        LineSeparator lineSeparator = new LineSeparator(incompleteFeatureLine);

        assertNull(lineSeparator.getAttributes().get("ID"));
        assertNull(lineSeparator.getAttributes().get("Name"));
    }

    @Test
    public void testConstructor_InvalidStartIndex() {
        // Test LineSeparator constructor with invalid start index (non-numeric)
        assertThrows(NumberFormatException.class, () -> new LineSeparator(lineWithInvalidColumns),
                "Expected NumberFormatException for non-numeric start index");
    }

    @Test
    public void testMakeFeatureInformation_ValidAttributes() {
        // Test private method makeFeatureInformation with valid attributes
        LineSeparator lineSeparator = new LineSeparator(validFeatureLine);

        Map<String, String> attributes = lineSeparator.getAttributes();
        assertEquals("gene1", attributes.get("ID"));
        assertEquals("GeneA", attributes.get("Name"));
    }

    @Test
    public void testMakeFeatureInformation_InvalidAttributes() {
        // Test makeFeatureInformation with uneven number of split attributes
        String[] invalidAttributesLine = {"chr1", "source", "gene", "100", "200", ".", "+", "0", "ID=gene1;Name"};

        LineSeparator lineSeparator = new LineSeparator(invalidAttributesLine);

        assertTrue(lineSeparator.getAttributes().containsKey("ID"));
        assertNull(lineSeparator.getAttributes().get("Name"), "Expected null due to incomplete attribute pair.");
    }

    @Test
    public void testToStringMethod() {
        // Test toString method
        LineSeparator lineSeparator = new LineSeparator(validFeatureLine);
        String expectedString = "chr1 source  gene  100  200  .  +  0  ID=gene1;Name=GeneA;";
        assertEquals(expectedString, lineSeparator.toString());
    }
}

