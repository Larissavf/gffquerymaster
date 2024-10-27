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
import org.apache.logging.log4j.Marker;

import java.util.Arrays;

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
    public static boolean ColumnName(LineSeparator readLine, String filterObject) {
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
        try {
            if (!Arrays.asList(possibleInput).contains(filterColumn)) {
                throw new Exception("The given column name is different from the allowed options");
            }
        } catch (Exception e) {
            logger.error("The given column name is different from the options, change it to the allowed options");
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
    public static boolean coordinates(String filterValue, LineSeparator readLine, boolean exactMatch) {
        try {
            String[] range = filterValue.split("-");
            if (range.length != 2) {
                throw new IllegalArgumentException("Invalid range format. Expected format: start-end.");
            }

            int start = Integer.parseInt(range[0]); // User-specified start coordinate
            int end = Integer.parseInt(range[1]);   // User-specified end coordinate

            if (start > end) {
                throw new IllegalArgumentException("Start coordinate cannot be greater than end coordinate.");
            }

            // Get the feature's start and end from LineSeparator
            int featureStart = readLine.getStartIndex();
            int featureEnd = readLine.getEndIndex();

            if (exactMatch) {
                // Exact match logic: feature must exactly match the given coordinates
                return featureStart == start && featureEnd == end;
            } else {
                // Overlap logic: feature must lie within or overlap the given range
                return featureStart <= end && featureEnd >= start;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coordinates must be valid integers.", e);
        }
    }


    // TODO prio2: Filter step for children and parents
    // TODO prio2: Filter step for attributes
    // TODO prio3: Summary
}
