/**
 * to write a output gff file line for line
 *
 *
 * @author Cheyenne & Larissa
 * @version 1.0
 * @since 5-10-2024
 */
package nl.bioinf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;

public class OutputWriter {
    private static final Logger logger = LogManager.getLogger(OutputWriter.class.getName());
    private final Path dataPath;
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
        //create file
        if(! Files.exists(dataPath)) {
            try {
                Files.createFile(dataPath);
                logger.info("Output file created: " + dataPath);
            } catch (IOException e) {
                logger.error("Something went wrong in creating the output file, check if you made the whole path", e);
            }
        }else{
            try {
                Files.delete(dataPath);
                logger.info("File already exist, the old gets deleted on " + dataPath);
            } catch (IOException e) {
                logger.error("Something went wrong in deleting the old output file", e);
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
    }
}
