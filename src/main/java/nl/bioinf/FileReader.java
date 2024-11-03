/**
 * to read a file line for line, filter the line, and write it to the output file
 *
 *
 * @author Cheyenne & Larissa
 * @version 2.0
 * @since 5-10-2024
 */
package nl.bioinf;

import nl.bioinf.functionality.Extendiator;
import nl.bioinf.functionality.Filter;
import nl.bioinf.objects.LineSeparator;
import nl.bioinf.objects.SummaryMaker;
import nl.bioinf.output.OutputWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class goes through all the lines, connects it to all the needed
 * functionality asked by the user and gets eventually written to the output file.
 */
public class FileReader {
    private static final Logger logger = LogManager.getLogger(FileReader.class.getName());
    // constructor values
    OutputWriter product;

    // possible values
    Extendiator inheritance;
    SummaryMaker summaryWriter;
    boolean extended = false;
    boolean summary = false;
    boolean column = false;
    private String columnName;
    boolean attribute = false;
    private String attributeName;
    private boolean exactMatch = false;

    /**
 * If the option inheritance is chosen the file needs to make use of the
 * extended lines that contains all the sub features from a gene.
 * **/
    public void setExtended() {
        this.extended = true;
        this.inheritance = new Extendiator();
    }

    /**
     * If the option summary is chosen the file needs to make use of the
     * summaryMaker that extracts all the necessary information necessary for the summary
     * **/
    public void setSummary() {
        this.summary = true;
        this.summaryWriter = new SummaryMaker();
    }

    /**
     * If chosen to filter on a column item
     * @param columnName String containing one or multiple filter items and values
     */
    public void setColumn(String columnName) {
        this.column = true;
        this.columnName = columnName;
    }

    /**
     * If chosen to filter on the attributes values
     * @param attribute String containing one or multiple attribute names and values
     */
    public void setAttribute(String attribute) {
        this.attribute = true;
        this.attributeName = attribute;
    }

    /**
     * if chosen for the option exact match
     * the values will be exactly as the filter value, or they fit in the filter value
     */
    public void setExactMatch(){
        this.exactMatch = true;
    }

    /**
     * Constructor to make the necessary objects for creating the end product. Like the outputWriter.
     * @param inputFilePath String containing the path to the input file
     * @param outputFilePath String containing the path to the output file
     */
    public FileReader(String inputFilePath, String outputFilePath) {
        // create end write product
        this.product = new OutputWriter(inputFilePath, outputFilePath);
    }

    /**
     * Parses through the GFF file line by line and extracts information.
     * Filters the line.
     * When the line is correctly compared, pass it to the writer
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     */
    public void parseGFFFile(String inputFilePath) {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            //create the output file
            product.createTheFile();


            while ((line = br.readLine()) != null) {
                // comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);
                // start reading the file according to the right settings
                boolean passedFilter = false;
                if (column & attribute & summary) { //if a summary needed, and the column and attribute filters have been set
                    summaryWriter.makeSummary(separatedRow);
                    if (readLineOnColumn(separatedRow) &
                            readLineOnAttribute(separatedRow)) {
                        passedFilter = true;
                    }
                }else if (column & attribute) { // if the column an attribute filters have been set
                    if (readLineOnColumn(separatedRow) &
                            readLineOnAttribute(separatedRow)) {
                        passedFilter = true;
                    }
                }else if(column & summary) {//if a summary needed, and the column filters have been set
                    summaryWriter.makeSummary(separatedRow);
                    if(readLineOnColumn(separatedRow)){passedFilter = true;}
                }else if (attribute & summary) {//if a summary needed, and attribute filters have been set
                    summaryWriter.makeSummary(separatedRow);
                    if(readLineOnAttribute(separatedRow)){passedFilter = true;}
                }else if (column){// just the column filters set
                    if(readLineOnColumn(separatedRow)){passedFilter = true;}
                } else if (attribute){// just the attribute filters set
                    if(readLineOnAttribute(separatedRow)){passedFilter = true;}
                } else if (summary){// just a summary needed
                    summaryWriter.makeSummary(separatedRow);
                    product.deleteFileOutput();
                }
                // checking if the option Inheritance has been given and the extended version is needed
                if (passedFilter & extended){//write part one to the output file
                    inheritance.possibleChild();
                    product.writeOutput(inheritance.checkChildren(separatedRow));
                } else if (extended){ // safe the possible more children
                inheritance.wholeObject(separatedRow);
                } else if (passedFilter){// just write the output out to the output file
                    product.writeOutput(separatedRow);
                } else {
                    product.writeOutput(separatedRow);
                }
            }
            // write the summary object
            if(summary){
                product.writeSummary(summaryWriter);
            }
            System.out.println("Done");
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: {}", inputFilePath, e);
        }
    }
    /**
     * Filters the line based on the attribute name and what value it should be equal to.
     *
     * @param separatedRow the parsed line
     */
    private boolean readLineOnAttribute(LineSeparator separatedRow) {
        // for the possibility of multiple input
        String tempAttributeName = attributeName + ",";
        String[] attributeNames = tempAttributeName.split(",");

        //do this until all possible input has been passed
        int i = 0;
        boolean passedFilter;
        do {
            passedFilter = Filter.attributesName(separatedRow, attributeNames[i], exactMatch);
            i++;
        }
        while (passedFilter & attributeNames.length - 1 > i);
        // return the state of the filter step
        return passedFilter;

    }
    /**
     * Filters the line based on the column name and what value it should be equal to.
     *
     * @param separatedRow the parsed line
     */
    private boolean readLineOnColumn(LineSeparator separatedRow) {
        // for the possibility of multiple input
        String tempColumnName = columnName + ",";
        String[] columnNames = tempColumnName.split(",");

        //do this until all possible input has been passed
        int i = 0;
        boolean passedFilter;
        do {
            passedFilter = Filter.columnName(separatedRow, columnNames[i], exactMatch);
            i++;
        }
        while (passedFilter & columnNames.length - 1 > i);
        // return the state of the filter step
        return passedFilter;
    }

}
