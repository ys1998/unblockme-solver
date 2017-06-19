#!/bin/bash

# Script to compile and arrange the source code for the program

mkdir classes
javac -cp ./classes -d ./classes ./src/*.java 	#compiles all .java files
clear
echo "Source code compiled."
