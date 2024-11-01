/**
 * To write an output gff file line for line
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 15-10-2024
 */
package nl.bioinf.output;

import nl.bioinf.objects.SummaryMaker;

import java.util.Map;
import java.util.Set;

/**
 * The different grouped data from the summary added to the StringBuilder
 */
public class SummaryOutputfileMaker {
    /**
     * It adds the different types of attributes present in every feature to the StringBuilder
     * @param sb StringBuilder containing the summary object
     * @param summary SummaryMaker object containing all the collected information for the summary
     */
    public static void typesOfAttributesPerFeature(StringBuilder sb, SummaryMaker summary) {
        sb.append("\n" +
                "Different types of attributes per feature:\n");
        // get all features
        Map<String, Set<String>> attributesTypesFeatures = summary.getAttributesTypesFile();
        //add all features of the file
        for ( String featuresOfTheAttributes: attributesTypesFeatures.keySet()){
            // get the set of the attributes per features
            Set<String> attributeTypes = attributesTypesFeatures.get(featuresOfTheAttributes);
            sb.append(featuresOfTheAttributes).append("   ").append(attributeTypes).append("\n");
        }
    }
    /**
     * It adds the average amount of nucleotides per feature to the StringBuilder
     * @param sb StringBuilder containing the summary object
     * @param summary SummaryMaker object containing all the collected information for the summary
     */
    public static void averageAmountOfNucleotidesPerFeature(StringBuilder sb, SummaryMaker summary) {
        sb.append("\n" +
                "Average amount of nucleotides per feature:\n");
        // get all features
        Map<String, Integer> featuresNucAndCounts = summary.getAmountNucPerFeature();
        Map<String, Integer> featuresAndCounts = summary.getFeatureTypesFile();
        //add all features of the file
        for ( String featureNucTypeAndCount: featuresNucAndCounts.keySet()){
            // determine the average amount of nucleotides of a feature
            int countNucOfFeature = featuresNucAndCounts.get(featureNucTypeAndCount);
            float average = (float) countNucOfFeature / featuresAndCounts.get(featureNucTypeAndCount);
            sb.append(featureNucTypeAndCount).append("   ").append(Math.round(average)).append(" nucleotides   with a total of ").append(countNucOfFeature).append(" nucleotides\n");
        }
    }
    /**
     * It adds the amount of features per feature present in the file to the StringBuilder
     * @param sb StringBuilder containing the summary object
     * @param summary SummaryMaker object containing all the collected information for the summary
     */
    public static void totalAmountOfFeatures(StringBuilder sb, SummaryMaker summary) {
        int totalAmountFeatures = summary.getAmountFeatures();
        sb.append("\nIn total there are ").append(totalAmountFeatures).append(" features in this genome:\n");
        sb.append("\nAmount of a type feature present: \n");
        // get all features
        Map<String, Integer> featuresAndCounts = summary.getFeatureTypesFile();
        //add all features of the file
        for ( String featureTypeAndCount: featuresAndCounts.keySet()){
            //determine the procent of the whole amount of features
            int countOfFeature = featuresAndCounts.get(featureTypeAndCount);
            float percentage = (((float) countOfFeature /totalAmountFeatures)*100);
            sb.append(featureTypeAndCount).append("   ").append(Math.round(percentage)).append("%   ").append(countOfFeature).append("\n");
        }
    }
}
