/**
 * To write an output gff file line for line
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 15-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputWriter {
    private static final Logger logger = LogManager.getLogger(OutputWriter.class.getName());
    private Path dataPath;
    private List<String> headers = new LinkedList<String>();
    /**
     * determines the dataPath needed to write the document to it
     *
     * @param inputPath The path to the GFF file to be parsed.
     * @param outputPath the path to the gff file needs to be stored
     */
    public OutputWriter(String inputPath, String outputPath){
        // check if there has been given a outputPath
        if (outputPath == null) {
        dataPath = createDataPath(inputPath);
        logger.info("No path was given the default output path will be used " + dataPath.toString());
        } else {
            dataPath = Paths.get(outputPath);
        }
    }

    /**
     * Changes for linux or windows, but make the default outputPath if not given
     * by the user.
     *
     * @param inputPathString The path to the GFF file to be parsed.
     */
    private Path createDataPath(String inputPathString){
        // set the default outputPath
        String currentDirectory = System.getProperty("user.dir");
        Path inputPath = Paths.get(inputPathString);


        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            Path dirPath = Paths.get(currentDirectory + "\\output\\");
            if (!Files.exists(dirPath)) {
            try{Files.createDirectory(dirPath);}
            catch(IOException e){ logger.error("Something went wrong in creating of directory",e); }
            logger.info("Output directory created: " + dataPath);}
            return Paths.get(currentDirectory + "\\output\\" + inputPath.getFileName());

        }else{
            Path dirPath = Paths.get(currentDirectory + "/output/");
            if (!Files.exists(dirPath)){
            try{Files.createDirectory(dirPath);}
            catch(IOException e){ logger.error("Something went wrong in creating of directory",e); }
            logger.info("Output directory created: " + dataPath);}
            return Paths.get(currentDirectory + "/output/" + inputPath.getFileName());

        }


    }
    /**
     * Create the file with the determined dataPath
     *
     */
    public void createTheFile(){
        //create file if it doesn't already exist
        if(! Files.exists(dataPath)) {
            try {
                Files.createFile(dataPath);
                logger.info("Output file created: " + dataPath);
            } catch (IOException e) {
                logger.error("Something went wrong in creating the output file, check if you made the whole path");
                System.exit(1);
            }
        }else{ // delete the file if it exists
            try {
                Files.delete(dataPath);
                logger.info("File already exist, the old gets deleted on " + dataPath);
            } catch (IOException e) {
                logger.error("Something went wrong in deleting the old output file");
                System.exit(1);
            }
            createTheFile();
        }

    }

    /**
     * Write the file with the determined dataPath with a lineSeparator as line
     *
     * @param line is a LineSeparator object a exact copy of the line of the original file
     */

    public void writeOutput(LineSeparator line){
        // write to file
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardOpenOption.APPEND)) {
            writer.write(line.toString());
            writer.newLine();
        } catch (NoSuchFileException e) {
            logger.error("File doesn't exist " + dataPath);
            System.exit(1);
        } catch (IOException e) {
            logger.error("Something went wrong in writing the output file", e);
            System.exit(1);
        }
    }
    /**
     * Write the file with the determined dataPath with a lineSeparator as line
     *
     * @param line is a String with the documentation of the original file
     */
    public void writeOutput(String line){
        // write to file
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardOpenOption.APPEND)) {
            writer.write(line);
            writer.newLine();
        } catch (NoSuchFileException e) {
            logger.error("File doesn't exist " + dataPath);
            System.exit(1);
        } catch (IOException e) {
            logger.error("Something went wrong in writing the output file", e);
            System.exit(1);
        }
        // it's a header
        headers.add(line + "\n");
    }

    /**
     * writing a list of LineSeparators to the output file
     * @param lines list of LineSeparator object containing all the information organised
     */
    public void writeOutput(List<LineSeparator> lines)
    {
        for (LineSeparator line : lines) {
            writeOutput(line);
        }
    }

    /**
     * write the summary output file
     * @param summary an object containing the needed information to make the summary outputfile
     */
    public void writeOutput(SummaryMaker summary){
    // stringbuilder
    StringBuilder sb = new StringBuilder();

    sb.append("The summary of: \n");
    sb.append(headers.toString());
    sb.append("\nThe source types for this genome:\n").append(summary.getSourceTypesFile().toString()).append("\n");


    int totalAmountFeatures = summary.getAmountFeatures();
    sb.append("\nIn total there are ").append(totalAmountFeatures).append(" features in this genome:\n");
    sb.append("\nAmount of a type feature present: \n");
    // get all features
    Map<String, Integer> featuresAndCounts = summary.getFeatureTypesFile();
    //add all features of the file
    for ( String featureTypeAndCount: featuresAndCounts.keySet()){
        //determine the procent of the whole amount of features
        int countOfFeature = featuresAndCounts.get(featureTypeAndCount);
        int percentage = ((countOfFeature/totalAmountFeatures)*100);
        sb.append(featureTypeAndCount).append("   ").append(percentage).append("%   ").append(countOfFeature).append("\n");
    }

    sb.append("\n" +
            "Average amount of nucleotides per feature:\n");
    // get all features
    Map<String, Integer> featuresNucAndCounts = summary.getAmountNucPerFeature();
    //add all features of the file
    for ( String featureNucTypeAndCount: featuresNucAndCounts.keySet()){
        // determine the average amount of nucleotides of a feature
        int countNucOfFeature = featuresAndCounts.get(featureNucTypeAndCount);
        int average = countNucOfFeature/featuresAndCounts.get(featureNucTypeAndCount);
        sb.append(featureNucTypeAndCount).append("   ").append(average).append(" nucleotides   with a total of ").append(countNucOfFeature).append("\n");
    }

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


    //set file path to summary and create
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
        dataPath = Paths.get(dataPath.getParent() + "\\summary.txt");
    }
    else {
        dataPath = Paths.get(dataPath.getParent() + "/summary.txt");
    }
    createTheFile();

        // write to file
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardOpenOption.APPEND)) {
            writer.write(sb.toString());
        } catch (NoSuchFileException e) {
            logger.error("File doesn't exist " + dataPath);
            System.exit(1);
        } catch (IOException e) {
            logger.error("Something went wrong in writing the output file", e);
            System.exit(1);
        }
    }

    /**
     * When the file is made during the process but not needed eventually
     */
    public void deleteFileOutput() {
        try {
            Files.deleteIfExists(dataPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
