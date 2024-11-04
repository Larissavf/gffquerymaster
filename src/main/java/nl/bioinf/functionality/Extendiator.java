/**
 * if the user chose for the option inheritance
 * when a features passes the filter all the features above what is the parents or has the same parent
 * will be sent to the OutputWriter. All the features after the filtered feature will be saved
 * and sent to the OutputWriter with the next correct feature.
 *
 * @author Larissa
 * @version 1.0
 * @since 16-10-2024
 */
package nl.bioinf.functionality;

import nl.bioinf.objects.LineSeparator;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is used when the user has asked for the functionality of inheritance
 */
public class Extendiator {
    List<LineSeparator> extendedLines = new ArrayList<>();
    List<LineSeparator> children = null;
    boolean childAdded = false;
    LineSeparator regionFeature = null;

    /**
     * feature add to the list with LineSeparator objects
     * it only contains the features from gene to the next gene, then it will reset.
     * If necessary children will be saved.
     *
     * @param separatedRow is a LineSeparator object made from a feature
     *
     * **/
    public void wholeObject(LineSeparator separatedRow) {
        // object goes from gene to gene so starts from gene
        if (separatedRow.getFeatureType().equals("gene")) {
            // if one of the elements in the gene before has been added to the output file remember the rest of the gene feature
            if (childAdded){
                // adding the region item
                if(regionFeature != null){
                    children = extendedLines;
                    children.add(regionFeature);
                } else {
                children = extendedLines;
                }
            }
            //reset for the new gene feature
            extendedLines.clear();
            childAdded = false;
        } // region item present
        if (separatedRow.getFeatureType().equals("region")) {
            regionFeature = separatedRow;
        } else {
        //add the element to the list to be possibly added
        extendedLines.add(separatedRow);
        }
    }

    /**
     * If there have been made an object containing children of the last filter passed feature
     * combine the old features with the new features, of a feature that has passed the filter
     * @param separatedRow LineSeparator object containing the elements of the line
     * @return List from LineSeparator objects that need to be in the output file
     * */
    public List<LineSeparator> checkChildren(LineSeparator separatedRow) {
        //are there some lines been processed since the last add to the output file?
        if (children != null){
            List<LineSeparator> newChildren = new ArrayList<>(children);
            //combine the children of the last element and the stuff of the new element
            newChildren.addAll(extendedLines);
            newChildren.add(separatedRow);
            // reset children for next  added element
            children = null;
            return newChildren;
        }
        extendedLines.add(separatedRow);
        return extendedLines;
    }


    /**
     * If a feature has passed the filter the next possible following features that are
     * children should be saved.
     * **/
    public void possibleChild() {
        // to check if there are more children to that gene feature
        childAdded = true;
        extendedLines.clear();
    }

    /**
     * Get the extendedLines object
     * @return is a list of LineSeparators that all possibly need to be used in the output
     */
    public List<LineSeparator> getExtendedLines() {
        return extendedLines;
    }

    /**
     * get the children object
     * @return  is a list of LineSeparators that all are related to the past item that had been used in the output
     */
    public List<LineSeparator> getChildren() {
        return children;
    }

    /**
     * get the regionFeature object
     * @return LineSeparator holding the feature of the region
     */
    public LineSeparator getRegionFeature() {
        return regionFeature;
    }

    /**
     * get the childAdded
     * @return is a boolean if the item before had been passed and possible children need to be added
     */
    public boolean isChildAdded() {
        return childAdded;
    }
}
