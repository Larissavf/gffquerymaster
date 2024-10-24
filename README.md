# Aquarium
### Introduction
A commandline tool for linux and windows filtering a gff version 3 file.
You can extract the correct lines that include certain information 
that you are looking for. The output will be a ggf version 3 file.

You can apply the filter in the columns and the attributes(will come).  
This tool makes use of the packages picocli and log4j.

#### Purpose
For filtering of a version 3 gff file. Extracting the wanted data.  
See for the necessary [format](https://www.ensembl.org/info/website/upload/gff.html?redirect=no).

### Getting started
Clone the git repo.

```
git clone https://github.com/Larissavf/gffquerymaster.git
```
You can grab a gff version 3 file from [NCBI](https://www.ncbi.nlm.nih.gov/datasets/)

### How to work with it
Run the following on the commandline when the repo is cloned:

```
java -jar gffquerymaster/build/libs/gffquerymaster-1.0-SNAPSHOT.jar -i <your/path/to/the/file>
```
##### Possible options
Possible options for the tool work as the following:  
You'll need to give the key and the wanted value to what 
the value in your file needs to be the same to.
You can only filter it on 1 column value at the time.
You need to have made your whole output location.

    -i, -input          location of the input file
    -o, --output        location of the output file, 
                        when not specified will use the local directory
    -c, --columnName    -c <columnName>=<filterValue>
                        options columnName: sequenceID, source, 
                        featureType and startAndStop
                        startAndStop=<start>-<stop>
    -a, --atribute      (is comming)


### Project structure

It's a standard gradle structure.

### FAQ
For extra help you can contact us:  

    Cheyenne e.h.b.brouwer@st.hanze.nl
    Larissa l.voshol@st.hanze.nl

