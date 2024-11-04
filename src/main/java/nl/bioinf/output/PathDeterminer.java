package nl.bioinf.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Everything to do with the creation or deletion of the path to the output file
 */
public class PathDeterminer {
    private static final Logger logger = LogManager.getLogger(PathDeterminer.class.getName());
    Path dataPath;
    List<String> headers = new LinkedList<>();

    /**
     * Changes for linux or windows, but make the default outputPath if not given
     * by the user.
     *
     * @param inputPathString The path to the GFF file to be parsed.
     */
    public Path createDataPath(String inputPathString){
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
                logger.info("Output file created: {}", dataPath);
            } catch (IOException e) {
                logger.error("Something went wrong in creating the output file, check if you made the whole path");
                throw new UnsupportedOperationException("Something went wrong in creating the output file");
            }
        }else{ // delete the file if it exists
            try {
                Files.delete(dataPath);
                logger.info("File already exist, the old gets deleted on {}", dataPath);
            } catch (IOException e) {
                logger.error("Something went wrong in deleting the old output file");
                throw new UnsupportedOperationException("Something went wrong in deleting the old output file");
            }
            createTheFile();
        }

    }


    void setPathToSummary() {
        //set file path to summary and create
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            dataPath = Paths.get(dataPath.getParent() + "\\summary.txt");
        }
        else {
            dataPath = Paths.get(dataPath.getParent() + "/summary.txt");
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
