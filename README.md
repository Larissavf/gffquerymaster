# GffQueryMaster
### Introduction
A commandline tool for linux and windows filtering a gff file version 3.
You can extract the correct lines that include certain information you are looking
for in the columns and/or in the attributes. The output will be a ggf file version 3.  

Extra functionality:  
There's the possibility to take the heritage in consideration present in a gff file.
This will take the parents and the children who are linked in the attributes. Together 
to the output file if one of them is correct to the wanted information.

You can also choose to make a summary of the gff file.  
This will contain:
- The different source types for this genome.
- The total features in this genome.
- The amount of every feature present.
- The average amount of nucleotides present per feature.
- The different types of attributes present per feature.

This tool makes use of the packages [picocli](https://picocli.info/) and [log4j](https://logging.apache.org/log4j/2.x/index.html).

#### Purpose
For filtering of a version 3 gff file in the columns or attributes.   
Extracting the wanted data. See for the necessary gff version 3 [format](https://www.ensembl.org/info/website/upload/gff.html?redirect=no).

### Getting started
Clone the git repo.

```
git clone https://github.com/Larissavf/gffquerymaster.git
```
You can grab a gff version 3 file from [NCBI](https://www.ncbi.nlm.nih.gov/datasets/)

### How to work with it
Run the following on the commandline when the repo is cloned:

```
./gradlew build
java -jar ./build/libs/gffquerymaster-1.0-SNAPSHOT.jar -i <your/path/to/the/file>
```
##### Possible options
The possible options for the tool work as the following:  
You'll need to give the key and the wanted value that is equal to the value in your file.
You can filter on multiple values

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

The attribute depends on the file itself cause attribute names can change. You can check this
using the summary option.

The Inheritance is a flag. when provided if a feature item contains the wanted 
filter value. All the other children and the parent will be written to the output.

When there isn't given any output path, a general path will be created.
If the given output path already has the existing file, it will be deleted and rewritten.

### Project structure

It's a standard gradle structure, see [gradle](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html) for the rules.

### FAQ
For extra help you can contact us:  

Cheyenne e.h.b.brouwer@st.hanze.nl  
Larissa l.voshol@st.hanze.nl

