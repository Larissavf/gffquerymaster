/**
 * Test on the Extendiator
 *
 * @author Larissa
 * @version 1.0
 * @since 1-11-2024
 */
package functionality;

import nl.bioinf.functionality.Extendiator;
import nl.bioinf.objects.LineSeparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ExtendiatorTest {
    Extendiator testExtendiator;
    LineSeparator mockLineSeparatorGene;
    LineSeparator mockLineSeparatorRNA;

    @BeforeEach
    public void setUp() {
        testExtendiator = new Extendiator();
        // Mock LineSeparator to simulate different values for testing
        mockLineSeparatorGene = Mockito.mock(LineSeparator.class);
        when(mockLineSeparatorGene.getFeatureType()).thenReturn("gene");

        mockLineSeparatorRNA = Mockito.mock(LineSeparator.class);
        when(mockLineSeparatorRNA.getFeatureType()).thenReturn("rna");

    }

    @Test
    public void testWholeObject(){
        // add an item to the list
        testExtendiator.wholeObject(mockLineSeparatorGene);
        // has it been added to the correct
        assertEquals(testExtendiator.getExtendedLines(), List.of(mockLineSeparatorGene));
    }

    @Test
    public void testCheckChildren(){
        List<LineSeparator> expectedList = List.of(mockLineSeparatorRNA, mockLineSeparatorRNA);

        // something has been passed to the output so children need to be checked
        testExtendiator.possibleChild();
        assertTrue(testExtendiator.isChildAdded());
        // add another one to the list
        testExtendiator.wholeObject(mockLineSeparatorRNA);
        // get the complete item set and compare
        assertEquals(expectedList,testExtendiator.checkChildren(mockLineSeparatorRNA));
        // if the children are accurate
        assertEquals(List.of(mockLineSeparatorRNA,mockLineSeparatorRNA), testExtendiator.getExtendedLines());
        testExtendiator.wholeObject(mockLineSeparatorGene);
        assertFalse(testExtendiator.isChildAdded());
    }

}
