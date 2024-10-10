/**
 * For separating a String array to the correct items. To have the information collected.
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf;

import java.util.HashMap;

public class LineSeparator {

//TODO parent opslaan
// child opslaan als parent in dict staat
    private String sequenceId;
    private String source;
    private String dataRegion;
    private int startIndex;
    private int endIndex;
    private char dot;
    private char strand;
    private char dot2;
    private HashMap<String, String> featureInformation;

    /**
     * It separates the given String array in to the correct variables
     *
     * @param featureStuff The String array holding the different items in the
     *                    line from the FileReader
     */
    public LineSeparator(String[] featureStuff) {
        // set the data to the correct variable
        this.sequenceId = featureStuff[0];
        this.source = featureStuff[1];
        this.dataRegion = featureStuff[2];
        this.startIndex = Integer.parseInt(featureStuff[3]);
        this.endIndex = Integer.parseInt(featureStuff[4]);
        this.dot = featureStuff[5].charAt(0);
        this.strand = featureStuff[6].charAt(0);
        this.dot2 = featureStuff[7].charAt(0);
        // split the last column for the correct information
        makeFeatureInformation(featureStuff[8]);
        //todo een error als buiten iets gaat indexen?? of dit doen in de check
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
        for (int i = 0; i < featureSplit.length; i+=2) {
            featureInformation.put(featureSplit[i], featureSplit[i + 1]);
        }//TODO geef een error als buiten lijst wordt gesneden
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public String getSource() {
        return source;
    }

    public String getDataRegion() {
        return dataRegion;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public char getDot() {
        return dot;
    }

    public char getStrand() {
        return strand;
    }

    public char getDot2() {
        return dot2;
    }

    public HashMap<String, String> getFeatureInformation() {
        return featureInformation;
    }
}

