/**
 * To write an output gff file line for line
 *
 *
 * @author Larissa
 * @version 1.0
 * @since 15-10-2024
 */
package nl.bioinf.output;

import nl.bioinf.objects.LineSeparator;
import nl.bioinf.objects.SummaryMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * This class gets writes all the output to the output file
 */
public class OutputWriter extends PathDeterminer {
    private static final Logger logger = LogManager.getLogger(OutputWriter.class.getName());
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
        if (!line.startsWith("##")){
            headers.add(line + "\n");}
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
     * @param sb an object containing the StringBuilder holding information to make the summary outputfile
     */
    public void writeOutput(StringBuilder sb){
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
     * make the summary output file
     * @param summary an object containing the needed information to make the summary outputfile
     */
    public void writesummary(SummaryMaker summary){
        // stringbuilder
        StringBuilder sb = new StringBuilder();
        //intro
        sb.append("The summary of: \n");
        sb.append(headers.toString());
        //source
        sb.append("\nThe source types for this genome:\n").append(summary.getSourceTypesFile().toString()).append("\n");

        //total amount of features
        SummaryOutputfileMaker.totalAmountOfFeaturesSB(sb, summary);

        //average amount of nucleotides per features
        SummaryOutputfileMaker.averageAmountOfNucleotidesPerFeature(sb, summary);

        //types of attributes per feature
        SummaryOutputfileMaker.typesOfAttributesPerFeature(sb, summary);

        //set file path to summary and create
        setPathToSummary();

        // create the file
        createTheFile();

        // write to the file
        writeOutput(sb);
    }


}
