package functionality;

import nl.bioinf.functionality.Filter;
import nl.bioinf.objects.LineSeparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilterTest {
    private LineSeparator mockLineSeparator;
    private boolean exactMatch = true;

    @BeforeEach
    public void setup() {
        // Mock LineSeparator to simulate different values for testing
        mockLineSeparator = Mockito.mock(LineSeparator.class);
        when(mockLineSeparator.getSequenceId()).thenReturn("chr1");
        when(mockLineSeparator.getSource()).thenReturn("sourceA");
        when(mockLineSeparator.getFeatureType()).thenReturn("gene");
        when(mockLineSeparator.getStartIndex()).thenReturn(100);
        when(mockLineSeparator.getEndIndex()).thenReturn(200);
    }

    @Test
    public void testColumnName_ValidFilter_MatchesSequenceId() {
        // Test filtering by sequenceId with a matching value
        boolean result = Filter.columnName(mockLineSeparator, "sequenceId=chr1", exactMatch);
        assertTrue(result, "Filter should match the sequenceId 'chr1'");
    }

    @Test
    public void testColumnName_ValidFilter_NoMatchForSource() {
        // Test filtering by source with a non-matching value
        boolean result = Filter.columnName(mockLineSeparator, "source=nonMatchingSource", exactMatch);
        assertFalse(result, "Filter should not match a different source value");
    }

    @Test
    public void testColumnName_InvalidFilterFormat() {
        // Test filtering with an invalid format (missing '=')
        assertThrows(IllegalArgumentException.class, () -> Filter.columnName(mockLineSeparator, "sequenceId", exactMatch));
    }

    @Test
    public void testColumnName_InvalidFilterColumn() {
        // Test filtering with an invalid column name
        boolean result = Filter.columnName(mockLineSeparator, "invalidColumn=value", exactMatch);
        assertFalse(result, "Filter should return false for an unsupported column name");
    }

    @Test
    public void testCoordinates_ExactMatch() {
        // Test exact coordinate match
        boolean result = Filter.coordinates("100-200",mockLineSeparator, exactMatch);
        assertTrue(result, "Filter should match the exact coordinates (100-200)");
    }

    @Test
    public void testCoordinates_OverlapWithinRange() {
        // Test coordinate range where the feature overlaps with the specified range
        boolean result = Filter.coordinates("150-250", mockLineSeparator, false);
        assertTrue(result, "Filter should match since the coordinates overlap with the range (150-250)");
    }

    @Test
    public void testCoordinates_NoOverlap() {
        // Test coordinate range where the feature does not overlap
        boolean result = Filter.coordinates("300-400", mockLineSeparator, false);
        assertFalse(result, "Filter should not match since the coordinates (100-200) do not overlap with (300-400)");
    }

    @Test
    public void testCoordinates_InvalidRangeFormat() {
        // Test invalid coordinate range format
        assertThrows(IllegalArgumentException.class, () -> Filter.coordinates("100_to_200", mockLineSeparator, false));
    }

    @Test
    public void testCoordinates_StartGreaterThanEnd() {
        // Test invalid range where start is greater than end
        assertThrows(IllegalArgumentException.class, () -> Filter.coordinates("200-100", mockLineSeparator, false));
    }
}

