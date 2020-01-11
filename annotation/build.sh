#!/bin/bash
mkdir bin
javac -d bin/ src/com/github/rafaritter44/java_pocs/annotation/*.java
cd bin
jar cvfm Annotations.jar ../manifest-addition.txt com/github/rafaritter44/java_pocs/annotation/*.class
rm -r com

