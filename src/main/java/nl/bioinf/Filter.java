/**
 * For filtering the file, by checking the object holding the line if it contains the wanted value
 *
 *
 * @author Cheyenne & Larissa
 * @version 1.0
 * @since 10-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;

/**
 * This class is needed when the user has given a filter step in either the column or the attribute
 */
public class Filter {
    private static final Logger logger = LogManager.getLogger(Filter.class.getName());

    /**
     * Checks if the line has the same value as been asked to filter on
     *
     * @param readLine the object made from the line from the file
     * @param filterObject the String that has the filtercolumn and filtervalue seperated on a "="
     *
     * @return true if the line is equal to the asked value and needs to be used for the output
     */
    public static boolean columnName(LineSeparator readLine, String filterObject) {
       String filterColumn= "";
       String filterValue = "";

        // extract filter items
        try {
            filterColumn = filterObject.split("=")[0];
            filterValue = filterObject.split("=")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle the case where "=" is missing or the format is invalid
            logger.fatal("Invalid format: expected 'key=value', but got: " + filterObject);
            System.exit(1);
        } catch (NullPointerException e) {
            // Handle the case where filterObject is null
            logger.fatal("Error: The filterObject is null.");
            System.exit(1);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            logger.fatal("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }

        // Possible filter values
        String[] possibleInput = {"sequenceID", "source", "featureType", "startAndStop"};
        // Check if the given column name for filter is allowed
        if (!Arrays.asList(possibleInput).contains(filterColumn)) {
                logger.fatal("The given column name is different from the allowed options");
                System.exit(1);
        }


        // Returns a boolean if the value of the line is the same as the filter value
        return switch (filterColumn) {
            case "sequenceId" -> filterValue.equals(readLine.getSequenceId());
            case "source" -> filterValue.equals(readLine.getSource());
            case "featureType" -> filterValue.equals(readLine.getFeatureType());
            case "startAndStop" -> coordinates(readLine, filterValue);
            default -> false;
        };
    }
    /**
     * Checks if the coordinate values have at least a single nucleotide in the wanted region
     *
     * @param readLine the object made from the line from the file
     * @param filterValue the String that has the filtercolumn and filtervalue seperated on a "="
     * @return true if the line has coordinates fitting in the asked region
     */
    // Prio1: Filter step to determine which features are within the x to y coordinate range
    public static boolean coordinates(LineSeparator readLine, String filterValue) {
        // Assuming filterValue is in the format "start-end", e.g., "1000-2000"
        String[] range = filterValue.split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);

        // Check if the feature is within the given coordinate range
        return readLine.getStartIndex() >= start && readLine.getEndIndex() <= end;
    }


    /**
     *Filters on all the attribute items and checks if the attribute according to the filter name has the filter value
     *
     * @param readLine is a LineSeparator object holding the feature
     * @param filterObject is a String array with the filter name and the filter value
     * @return a boolean if the feature has the correct value
     */
    public static boolean attributesName(LineSeparator readLine, String filterObject) {
        String filterName= "";
        String filterValue = "";

        // extract filter items
        try {
            filterName = filterObject.split("=")[0];
            filterValue = filterObject.split("=")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle the case where "=" is missing or the format is invalid
            logger.fatal("Invalid format: expected 'key=value', but got: " + filterObject);
            System.exit(1);
        } catch (NullPointerException e) {
            // Handle the case where filterObject is null
            logger.fatal("Error: The filterObject is null.");
            System.exit(1);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            logger.fatal("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }

        Map<String, String> attributes = readLine.getAttributes();
        // check if the name is in the keys
        if (attributes.containsKey(filterName)){
            String attributesValue = attributes.get(filterName);
            // check if the value is the same
            return attributesValue.contains(filterValue);
        }
        return false;

    }
}
