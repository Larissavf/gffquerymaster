package nl.bioinf;

import java.util.HashMap;

public class LineSeparator {
// parent opslaan
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

    public LineSeparator(String[] featureStuff) {
        this.sequenceId = featureStuff[0];
        this.source = featureStuff[1];
        this.dataRegion = featureStuff[2];
        this.startIndex = Integer.parseInt(featureStuff[3]);
        this.endIndex = Integer.parseInt(featureStuff[4]);
        this.dot = featureStuff[5].charAt(0);
        this.strand = featureStuff[6].charAt(0);
        this.dot2 = featureStuff[7].charAt(0);

        makeFeatureInformation(featureStuff[8]);
    }

    private void makeFeatureInformation(String featureStuff) {
        String[] featureSplit = featureStuff.split("[;=]");
        for (int i = 0; i < featureSplit.length; i+=2) {
            featureInformation.put(featureSplit[i], featureSplit[i + 1]);
        }
    }



}

