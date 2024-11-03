package output;

import nl.bioinf.objects.SummaryMaker;
import nl.bioinf.output.SummaryOutputFileMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class SummaryOutputFileMakerTest {
    private SummaryMaker mockLineSummary;

    // make placeholders for the mock version of a SummaryMaker object
    private Map<String, Set<String>> tempAttributesTypesFile = Map.of(
            "gene", new HashSet<>(Set.of("ID", "Name")));

    private Map<String, Integer> tempAmountNucPerFeature = Map.of(
            "gene", 400);

    private Map<String, Integer> tempFeatureTypesFile = Map.of(
            "gene", 1);


    @BeforeEach
    public void setup() {
        // Mock SummaryMaker to simulate different values for testing
        mockLineSummary = Mockito.mock(SummaryMaker.class);
        when(mockLineSummary.getAttributesTypesFile()).thenReturn(tempAttributesTypesFile);
        when(mockLineSummary.getAmountNucPerFeature()).thenReturn(tempAmountNucPerFeature);
        when(mockLineSummary.getAmountFeatures()).thenReturn(1);
        when(mockLineSummary.getFeatureTypesFile()).thenReturn(tempFeatureTypesFile);
    }

    // checking if the expected output is the same as that's being shown
    @Test
    public void testTypesOfAttributesPerFeature(){
        StringBuilder sb = new StringBuilder("This is a testfile");
        //make the actual output
        SummaryOutputFileMaker.typesOfAttributesPerFeature(sb, mockLineSummary);

        assertEquals(sb.toString(), "This is a testfile\nDifferent types of attributes per feature:\ngene   [ID, Name]\n");
    }

    // checking if the expected output is the same as that's being shown
    @Test
    public void testAverageAmountOfNucleotidesPerFeature() {
        StringBuilder sb = new StringBuilder("This is a testfile");
        //make the actual output
        SummaryOutputFileMaker.averageAmountOfNucleotidesPerFeature(sb, mockLineSummary);

        assertEquals(sb.toString(), "This is a testfile\nAverage amount of nucleotides per feature:\ngene   400 nucleotides   with a total of 400 nucleotides\n");
    }

    // checking if the expected output is the same as that's being shown
    @Test
    public void testTotalAmountOfFeatures() {
        StringBuilder sb = new StringBuilder("This is a testfile");
        //make the actual output
        SummaryOutputFileMaker.totalAmountOfFeatures(sb, mockLineSummary);

        assertEquals(sb.toString(), "This is a testfile\nIn total there are 1 features in this genome:\n\nAmount of a type feature present: \ngene   100%   1\n");
    }
}
