/**
 * to read a file line for line, filter the line, and write it to the output file
 *
 *
 * @author Cheyenne & Larissa
 * @version 1.0
 * @since 5-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;


public class FileReader {
    private static final Logger logger = LogManager.getLogger(FileReader.class.getName());

    boolean extended = false;

/**
 * If for the option inheritance is chosen the file needs to make use of the
 * extended lines that contains all the subfeatures from a gene.
 * **/
    public void setExtended() {
        extended = true;
    }

    /**
     * Parses through the GFF file line by line and extracts information.
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     * @param outputFilePath the path to the gff file needs to be stored
     */
    //todo prio2 overload constructor with just the different combinations with the filtering
    public void parseGFFFile(String inputFilePath, String outputFilePath) {
        // create end write product
        OutputWriter product = new OutputWriter(inputFilePath, outputFilePath);
        product.createTheFile();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);
                product.writeOutput(separatedRow);
            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: {}", inputFilePath, e);
        }
    }
    /**
     * Parses through the GFF file line by line and extracts information.
     * Filters the line based on the column name and what value it should be equal to.
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     * @param columnName the object with the column name and the value it's supposed to equal to
     * @param outputFilePath The path the gff file will be written to
     */

    public void parseGFFFile(String inputFilePath, String columnName, String outputFilePath) {
        // create end write product
        OutputWriter product = new OutputWriter(inputFilePath, outputFilePath);
        product.createTheFile();

        Extendiator inheritance = new Extendiator();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);

                // if line is correct to the filtered value
                if (Filter.ColumnName(separatedRow, columnName)) {
                    product.writeOutput(separatedRow);
                // if the option inheritance was requested safe every gene feature with their children
                } else if (extended) {
                    inheritance.wholeObject(separatedRow);
                // accepted feature to the filter value, now parent and children will be added to the output
                } else if (extended & Filter.ColumnName(separatedRow, columnName)) {
                    inheritance.possibleChild();
                    product.writeOutput(inheritance.checkChildren());
                }

            }
            logger.info("The file is read and filtered on column");
            if (extended) {
                logger.info("The file is read and filtered on column with the gene features and all children" +
                        "in what at least one of the items holds the wanted value");
            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: {}", inputFilePath, e);
        }
    }
    /**
     * Parses through the GFF file line by line and extracts information.
     * Filters the line based on the attribute name and what value it should be equal to.
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     * @param attributes the object with the attributes name and the value it's supposed to equal to
     * @param outputFilePath The path the gff file will be written to
     */
    public void parseGFFFile(String inputFilePath, String[] attributes, String outputFilePath) {
        // create end write product
        OutputWriter product = new OutputWriter(inputFilePath, outputFilePath);
        product.createTheFile();

        Extendiator inheritance = new Extendiator();


        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);

                // if line is correct to the filtered value
                if (Filter.attributesName(separatedRow, attributes)) {
                    product.writeOutput(separatedRow);
                    // if the option inheritance was requested safe every gene feature with their children
                } else if (extended) {
                    inheritance.wholeObject(separatedRow);
                    // accepted feature to the filter value, now parent and children will be added to the output
                } else if (extended & Filter.attributesName(separatedRow, attributes)) {
                    inheritance.possibleChild();
                    product.writeOutput(inheritance.checkChildren());
                }
                product.writeOutput(separatedRow);
            }
            logger.info("The file is read and filtered on attributes");
            if (extended) {
                logger.info("The file is read and filtered on attributes with the gene features and all children" +
                        "in what at least one of the items holds the wanted value");
            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: {}", inputFilePath, e);
        }
    }

    /**
     * Parses through the GFF file line by line and extracts information.
     * Filters the line based on the attribute name and what value it should be equal to.
     * And the column name and what value it should be equal to.
     *
     * @param inputFilePath The path to the GFF file to be parsed.
     * @param attributes the object with the attributes name and the value it's supposed to equal to
     * @param columnName the object with the column name and the value its supposed to equal to
     * @param outputFilePath The path the gff file will be written to
     */
    public void parseGFFFile(String inputFilePath, String[] attributes, String columnName, String outputFilePath) {
        // create end write product
        OutputWriter product = new OutputWriter(inputFilePath, outputFilePath);
        product.createTheFile();

        Extendiator inheritance = new Extendiator();


        try (BufferedReader br = new BufferedReader(new java.io.FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip comment lines and headers
                if (line.startsWith("#")) {
                    product.writeOutput(line);
                    continue;
                }

                // Split the line into columns
                String[] columns = line.split("\t");
                LineSeparator separatedRow = new LineSeparator(columns);

                // if line is correct to the filtered value
                if (Filter.attributesName(separatedRow, attributes) & Filter.ColumnName(separatedRow, columnName)) {
                    product.writeOutput(separatedRow);
                    // if the option inheritance was requested safe every gene feature with their children
                } else if (extended) {
                    inheritance.wholeObject(separatedRow);
                    // accepted feature to the filter value, now parent and children will be added to the output
                } else if (extended & Filter.attributesName(separatedRow, attributes) & Filter.ColumnName(separatedRow, columnName)) {
                    inheritance.possibleChild();
                    product.writeOutput(inheritance.checkChildren());
                }
                product.writeOutput(separatedRow);
            }
            logger.info("The file is read and filtered on attributes and column");
            if (extended) {
                logger.info("The file is read and filtered on attributes and column with the gene features and all children" +
                        "in what at least one of the items holds the wanted value");
            }
        } catch (IOException e) {
            logger.fatal("An error occurred while parsing the GFF file: {}", inputFilePath, e);
        }
    }

}
