/**
 * For separating a String array to the correct items. To have the information collected.
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LineSeparator {
    private static final Logger logger = LogManager.getLogger(LineSeparator.class.getName());

//TODO parent opslaan
// child opslaan als parent in dict staat
    private String sequenceId;
    private String source;
    private String featureType;
    private int startIndex;
    private int endIndex;
    private char score;
    private char strand;
    private char frame;
    private Map<String, String> attributes = new HashMap<>();

    /**
     * It separates the given String array in to the correct variables
     *
     * @param featureStuff The String array holding the different items in the
     *                    line from the FileReader
     */
    public LineSeparator(String[] featureStuff) {
        // set the data to the correct variable
        try { this.sequenceId = featureStuff[0];
        this.source = featureStuff[1];
        this.featureType = featureStuff[2];
        this.startIndex = Integer.parseInt(featureStuff[3]);
        this.endIndex = Integer.parseInt(featureStuff[4]);
        this.score = featureStuff[5].charAt(0);
        this.strand = featureStuff[6].charAt(0);
        this.frame = featureStuff[7].charAt(0);
        // split the last column for the correct information
        makeFeatureInformation(featureStuff[8]);
        } catch(ArrayIndexOutOfBoundsException e) {
            logger.fatal("The  file misses 1 or multiple columns, check the tabs");
        }
    }

    /**
     * It separates the last column into more elements
     *
     * @param featureStuff Is a String holding information that needs to be split
     * **/

    private void makeFeatureInformation(String featureStuff) {
        // split the String on ; and =
        String[] featureSplit = featureStuff.split("[;=]");
        // put the first item as key and the second as value
       try{ for (int i = 0; i < featureSplit.length; i+=2) {
           attributes.put(featureSplit[i], featureSplit[i + 1]);
       }
    }catch(ArrayIndexOutOfBoundsException e) {
           logger.fatal("The  file misses 1 or multiple columns, check the tabs");
       }
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public String getSource() {
        return source;
    }

    public String getFeatureType() {
        return featureType;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public char getScore() {
        return score;
    }

    public char getStrand() {
        return strand;
    }

    public char getFrame() {
        return frame;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        String format = "%s %s  %s  %d  %d  %c  %c  %c  %s";
        return String.format(format, sequenceId, source, featureType, startIndex, endIndex, score, strand, frame, formatAttributes(attributes));
    }

    private String formatAttributes(Map<String, String> attributes) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

}

