/**
 * Test on the SummaryMaker
 * @author Larissa
 * @version 1.0
 * @since 16-10-2024
 */
package objects;

import nl.bioinf.objects.LineSeparator;
import nl.bioinf.objects.SummaryMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SummaryMakerTest {
    private LineSeparator mockLineSeparator;


    @BeforeEach
    public void setup() {
        // Mock LineSeparator to simulate different values for testing
        mockLineSeparator = Mockito.mock(LineSeparator.class);
        when(mockLineSeparator.getSequenceId()).thenReturn("chr1");
        when(mockLineSeparator.getSource()).thenReturn("sourceA");
        when(mockLineSeparator.getFeatureType()).thenReturn("gene");
        when(mockLineSeparator.getStartIndex()).thenReturn(100);
        when(mockLineSeparator.getEndIndex()).thenReturn(200);
        when(mockLineSeparator.getAttributes()).thenReturn(new HashMap<>(Map.of("ID", "chr1", "Name","sourceA")));
    }

    @Test
    public void testMakeSummary() {
        // make the actual SummaryMaker object
        SummaryMaker testSummaryMaker = new SummaryMaker();
        testSummaryMaker.makeSummary(mockLineSeparator);

        // the expected SummaryMaker elements
        Map<String, Set<String>> tempAttributesTypesFile = Map.of(
                "gene", new HashSet<>(Set.of("ID", "Name")));

        Map<String, Integer> tempAmountNucPerFeature = Map.of(
                "gene", 101);

        Map<String, Integer> tempFeatureTypesFile = Map.of(
                "gene", 1);

        //test the expected with the actual
        assertEquals(testSummaryMaker.getAmountFeatures(), 1);
        assertEquals(testSummaryMaker.getAttributesTypesFile(),tempAttributesTypesFile );
        assertEquals(testSummaryMaker.getAmountNucPerFeature(), tempAmountNucPerFeature);
        assertEquals(testSummaryMaker.getFeatureTypesFile(), tempFeatureTypesFile);

    }


}
