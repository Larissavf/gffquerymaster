/**
 * For separating a String array to the correct items. To have the information collected.
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class gets used to make for every line read in ReadFile to a usable object
 */
public class LineSeparator {
    private static final Logger logger = LogManager.getLogger(LineSeparator.class.getName());

    private String sequenceId;
    private String source;
    private String featureType;
    private int startIndex;
    private int endIndex;
    private char score;
    private char strand;
    private char frame;
    private final Map<String, String> attributes = new LinkedHashMap<>();

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
        String[] featureSplit = split(featureStuff,';','=');
        // put the first item as key and the second as value
       try{ for (int i = 0; i < featureSplit.length; i+=2) {
           attributes.put(featureSplit[i], featureSplit[i + 1]);
       }
    }catch(ArrayIndexOutOfBoundsException e) {
           logger.fatal("The  file misses 1 or multiple columns, check the tabs");
       }
    }

    /**
     * Alternative for the method Java.lang.String.split, this is a logic based method made by chatGPT.
     * produced to test a better version for method Java.lang.String.split.
     * @param input String containing the line that needs to be split
     * @param delimiter1 char with the first split object
     * @param delimiter2 char with the second split object
     * @return string array containing all the elements
     */
    public static String[] split(String input, char delimiter1, char delimiter2) {
        // Handle null or empty input
        if (input == null || input.isEmpty()) {
            return new String[0];
        }

        char[] chars = input.toCharArray();
        StringBuilder currentToken = new StringBuilder();
        List<String> resultList = new ArrayList<>();

        for (char c : chars) {
            if (c == delimiter1 || c == delimiter2) {
                if (currentToken.length() > 0) {
                    resultList.add(currentToken.toString());
                    currentToken.setLength(0); // Reset the StringBuilder
                }
            } else {
                currentToken.append(c);
            }
        }

        // Add the last token if it exists
        if (currentToken.length() > 0) {
            resultList.add(currentToken.toString());
        }

        // Convert List to String[]
        return resultList.toArray(new String[0]);
    }



    /**
     * Get the sequence id
     * @return String containing the sequenceId
     */
    public String getSequenceId() {
        return sequenceId;
    }

    /**
     * get the source
     * @return String containing the source
     */
    public String getSource() {
        return source;
    }

    /**
     * get the feature type
     * @return String containing the featureType
     */
    public String getFeatureType() {
        return featureType;
    }

    /**
     * get the start index
     * @return int containing the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * get the end index
     * @return int containing the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * get the score
     * @return char containing the score
     */
    public char getScore() {
        return score;
    }

    /**
     * get the strand
     * @return char containing the strand
     */
    public char getStrand() {
        return strand;
    }

    /**
     * get the frame
     * @return char containing the frame
     */
    public char getFrame() {
        return frame;
    }

    /**
     * get the attributes
     * @return linkedHashMap containing the attributes in keys and values
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * tostring that al the items will be written back perfectly like the original input
     * @return String formatted containing the every column from the original input in the same order
     */
    @Override
    public String toString() {
        String format = "%s %s  %s  %d  %d  %c  %c  %c  %s";
        return String.format(format, sequenceId, source, featureType, startIndex, endIndex, score, strand, frame, formatAttributes(attributes));
    }

    /**
     * formating the attributes hashmap in the correct way so it can be split on "=" and ";"
     * @param attributes linkedHashMap containing the attributes in keys and values
     * @return String containing the new correct formatted Map
     */
    private String formatAttributes(Map<String, String> attributes) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

}

