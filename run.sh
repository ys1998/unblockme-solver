#!/bin/bash

# This is a shell script to execute the UnblockMe Solver Program

# The program is available in two modes - terminal and GUI mode
clear
echo "UNBLOCK-ME-SOLVER"
echo ""
echo "Please select the program mode:"
echo "1. Terminal mode"
echo "2. GUI mode"
echo -n "Your choice : "
read choice
if [[ "$choice" -eq 1 ]]; then
	cd classes
	clear
	java unblockmesolver.Main
elif [[ "$choice" -eq 2 ]]; then
	cd classes
	clear
	java unblockmesolver.GUIMain
else
	echo "Invalid choice!"
	echo "Exiting program."
	sleep 2
	exit
fi