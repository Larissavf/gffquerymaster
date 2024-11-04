# GffQueryMaster
### Introduction
**GffQueryMaster** is a command-line tool for filtering GFF (General Feature Format) version 3 files on both Linux and Windows. It allows users to extract lines containing specific information from the columns and/or attributes of a GFF file, with the output also in GFF version 3 format.

## Features  
- Extract lines based on specific information in columns and/or attributes.
- Consider the heritage present in a GFF file, taking into account parents and children linked in the attributes.
- Generate a summary of the GFF file, including:
  - Different source types for the genome.
  - Total features in the genome.
  - Count of each feature present.
  - Average number of nucleotides per feature.
  - Different types of attributes present per feature.

This tool makes use of the packages [picocli](https://picocli.info/) version 4.6.3 and [log4j](https://logging.apache.org/log4j/2.x/index.html) version 2.24.1.
In [Java version 22.0.2](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)

#### Purpose
For filtering of a version 3 gff file in the columns or attributes.   
Extracting the wanted data. See for the necessary gff version 3 [format](https://www.ensembl.org/info/website/upload/gff.html?redirect=no).

## Purpose
The purpose of this tool is to filter a GFF version 3 file by extracting lines that contain specific information in the columns or attributes, allowing users to obtain the desired data based on their filtering criteria.

### Getting started
Before you begin, ensure you have Git installed on your machine. If you haven't installed it yet, you can download it from [Git](https://git-scm.com/).

1. Clone the Git repository:
```bash
   git clone https://github.com/Larissavf/gffquerymaster.git
```
2. Obtain a GFF version 3 file from [NCBI](https://www.ncbi.nlm.nih.gov/datasets/)

### How to Work with It
1. After cloning the repository, run the following command:
- Use either Gradle or open the project with IntelliJ IDEA (which has Gradle built-in), and then run the following command in the IntelliJ command line:

```
./gradlew build
java -jar ./build/libs/gffquerymaster-1.0-SNAPSHOT.jar -i <your/path/to/the/file>
```
#### Possible options
The tool provides the following options:

    -i, -input          location of the input file
    -o, --output        location of the output file, 
                        when not specified will use the local directory
    -c, --columnName    -c <columnName>=<filterValue>
                        options columnName: sequenceID, source, 
                        featureType and startAndStop
                        exception: startAndStop=<start>-<stop>
                        Want it to accept multiple columns use the following syntax: 
                        <columnName>=<filterValue>,<columnName>=<filterValue>
    -a, --attribute     -a <attributeName><filterValue>  
                        To accept multiple atribute values separate it with a comma like column.
    -I, --inheritance   If you want the parent with the children of the 
                        succeeded filter step.
    -s, --summary       For the summary

### Notes

- The attribute names depend on the file itself, as they can change. You can check the attribute names by using the summary option.
- The Inheritance option is a flag. When provided, if a feature item contains the desired filter value, all other children and the parent will be written to the output file.
- If no output path is specified, a general path will be created. If the given output path already has an existing file, it will be deleted and overwritten.

### Project structure

It's a standard gradle structure, see [gradle](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html) for the rules.

### FAQ
For extra help you can contact us:  

Cheyenne e.h.b.brouwer@st.hanze.nl  
Larissa l.voshol@st.hanze.nl

