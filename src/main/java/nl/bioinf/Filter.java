package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

import java.util.Arrays;

public class Filter {

    private static final Logger logger = LogManager.getLogger(ArgsProcessor.class.getName());

    public static boolean ColumnName(LineSeparator readLine, String filterObject) {
        // Filter items
        String filterColumn = filterObject.split(" ")[0];
        String filterValue = filterObject.split(" ")[1];

        // Possible filter values
        String[] possibleInput = {"sequenceID", "source", "featureType", "startAndStop"};

        // Check if the given column name for filter is allowed
        try {
            if (!Arrays.asList(possibleInput).contains(filterColumn)) {
                throw new Exception("The given column name is different from the allowed options");
            }
        } catch (Exception e) {
            logger.error((Marker) e, "The given column name is different from the options, change it to the allowed options");
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

    // Prio1: Filter step to determine which features are within the x to y coordinate range
    public static boolean coordinates(LineSeparator readLine, String filterValue) {
        // Assuming filterValue is in the format "start-end", e.g., "1000-2000"
        String[] range = filterValue.split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);

        // Check if the feature is within the given coordinate range
        return readLine.getStartIndex() >= start && readLine.getEndIndex() <= end;
    }

    // TODO prio2: Filter step for children and parents
    // TODO prio2: Filter step for attributes
    // TODO prio3: Summary
}
