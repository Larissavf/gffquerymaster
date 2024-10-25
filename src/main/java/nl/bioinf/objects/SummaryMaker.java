/**
 * for making an object that holds all the information necessary for a summary
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 19-10-2024
 */
package nl.bioinf.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The object containing all the information necessary to make a summary
 */
public class SummaryMaker {
    private int amountFeatures = 0;
    private final Map<String, Integer> featureTypesFile = new HashMap<>();
    private final Map<String, Integer> amountNucPerFeature = new HashMap<>();
    private final Set<String> sourceTypesFile = new HashSet<>();
    private final Map<String, Set<String>> attributesTypesFile = new HashMap<>();

    /**
     * The main collector for getting all the needed features per line
     * @param line LineSeparator object containing all the information organised
     */
    public void makeSummary(LineSeparator line) {
        // amount of total lines
        amountFeatures = amountFeatures + 1;

        // get feature amount
        countFeatures(line);

        //amount of nucleotide per feature
        countNucPerFeature(line);

        // types of sources
        sourceTypesFile.add(line.getSource());

        // attributes per feature
        typesAttributesFeatures(line);
    }

    /**
     * Gets all the names of the attributes saves it in a set attributesTypesFile
     * @param line LineSeparator object containing all the information organised
     */
    private void typesAttributesFeatures(LineSeparator line) {
        String feature = line.getFeatureType();
        Map<String, String> attributes = line.getAttributes();
        // new or existing attribute types
        if(!attributesTypesFile.containsKey(feature)) {
            attributesTypesFile.put(feature, new HashSet<>(attributes.keySet()));
        } else {
            // add a set to an existing set
            attributesTypesFile.get(feature).addAll(attributes.keySet());
        }

    }

    /**
     *Adds the amount of nucleotide in a feature, so the average will be calculated after
     * @param line LineSeparator object containing all the information organised
     */
    private void countNucPerFeature(LineSeparator line) {
        String feature = line.getFeatureType();
        int startIndex = line.getStartIndex();
        int endIndex = line.getEndIndex();
        // new or existing feature in map
        if (!amountNucPerFeature.containsKey(feature)) {
            amountNucPerFeature.put(feature, endIndex - startIndex + 1);
        } else {
            // add the amount to the existing
            amountNucPerFeature.compute(feature, (k, existingAmountNucleotide) -> existingAmountNucleotide + (endIndex - startIndex + 1)) ;
        }
    }

    /**
     * Counts the amount of each feature is present
     * @param line LineSeparator object containing all the information organised
     */
    private void countFeatures(LineSeparator line) {
        String feature = line.getFeatureType();
        // new or existing feature in map
        if (!featureTypesFile.containsKey(feature)) {
            featureTypesFile.put(feature, 1);
        } else {
            // add 1
            featureTypesFile.compute(feature, (k, currentValue) -> currentValue + 1);
        }
    }

    /**
     * get the total amount of features in the file
     * @return int containing the amount of total features
     */
    public int getAmountFeatures() {
        return this.amountFeatures;
    }

    /**
     * get the different types of features and how much they are present
     * @return map containing the amount and types of features
     */
    public Map<String, Integer> getFeatureTypesFile() {
        return featureTypesFile;
    }

    /**
     * get the amount of nucleotide per feature
     * @return map containing the amount of nucleotides per feature
     */
    public Map<String, Integer> getAmountNucPerFeature() {
        return amountNucPerFeature;
    }

    /**
     * get the different source types in the file
     * @return set containing the source types
     */
    public Set<String> getSourceTypesFile() {
        return sourceTypesFile;
    }

    /**
     * get the different attributes present in a feature
     * @return map containing the feature with the different attributes in a set
     */
    public Map<String, Set<String>> getAttributesTypesFile() {
        return attributesTypesFile;
    }
}
