package nl.bioinf;

public class Main {
    public static void main(String[] args) {
        // Create an instance of InputFileChecker
        InputFileChecker checker = new InputFileChecker();

        // Define the path to your GFF file
        String filePath = "/Users/cheyennebrouwer/Documents/24-25/JAVA/ncbi_dataset_gff/ncbi_dataset/data/GCA_000009045.1/genomic.gff";

        // Check if the file is valid
        if (checker.isValidGFFFile(filePath)) {
            System.out.println("File is a valid GFF file.");

            // Create an instance of LineParser
            LineParser parser = new LineParser();
            // Parse the file
            parser.parseGFFFile(filePath);
        } else {
            System.out.println("Invalid GFF file.");
        }
    }
}