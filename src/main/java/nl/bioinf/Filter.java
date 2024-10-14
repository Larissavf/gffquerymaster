package nl.bioinf;
// een shitload aan filters, misschien sommige apart

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

import javax.sound.sampled.Line;
import java.util.Arrays;

public class Filter {

    private static final Logger logger = LogManager.getLogger(ArgsProcessor.class.getName());


    public static boolean ColumnName(LineSeparator readLine, String filterObject){
        // filter items
        String filterColumn = filterObject.split(" ")[0];
        String filterValue = filterObject.split(" ")[1];
        // possible filter values
        String[] possibleInput = {"sequenceID", "source", "featureType", "startAndStop"};
        // check if the given column name for filter is allowed and works in the file
        try {if (!Arrays.asList(possibleInput).contains(filterColumn)) {
            throw new Exception("The given column name is different from the options");}}
        catch (Exception e) {
            logger.error((Marker) e, "The given column name is different from the options, change it to the allowed options");
        }
        // returns a boolean if the value of the line is the same as the filter value
        return switch (filterColumn) {
            case "sequenceId" -> filterValue.equals(readLine.getSequenceId());
            case "source" -> filterValue.equals(readLine.getSource());
            case "featureType" -> filterValue.equals(readLine.getFeatureType());
            case "startAndStop" -> coordinates(filterValue);
            default -> false;
        };
    }

    // todo prio1 filterstap: welke features in x tot y coordinaat
    public static boolean coordinates(String filtervalue){
        return true;
    }
    // todo prio2 children and parents
    // todo prio2 attribute filterstap
    // todo prio3 summary

}
