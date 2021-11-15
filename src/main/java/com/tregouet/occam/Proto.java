package com.tregouet.occam;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.tregouet.occam.io.output.IOntologicalCommitment;
import com.tregouet.occam.io.output.impl.OntologicalCommitment;

public class Proto {

	IOntologicalCommitment representations;
	String nL = System.lineSeparator();
	Scanner entry  = new Scanner(System.in);
	String folderPath = null;
	
	public Proto() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		

	}
	
	private void welcome() {
		System.out.println("********************");
		System.out.println("********OCCAM********");
		System.out.println("********************");
		System.out.println(nL);
		enterTargetFolder();
	}
	
	private void mainMenu() {
		System.out.println("nL");
		System.out.println("**********MAIN MENU**********");
		System.out.println("nL");
		System.out.println("1 : enter new input" + nL);
		System.out.println("2 : set new target folder" + nL);
		System.out.println("3 : exit" + nL);
		int choice = entry.nextInt();
		switch(choice) {
			case 1 : enterNewInput(); 
				break;
			case 2 : enterTargetFolder();
				break;
			case 3 : System.out.println("Goodbye.");
				System.exit(0);
				break;
			default : 
				System.out.println("This is not funny. Please stay focused." + nL);
				mainMenu();
				break;
		}
	}
	
	private void enterTargetFolder() {
		System.out.println("nL");
		System.out.println("Please enter a path for the target folder : " + nL);
		String pathString = entry.next();
		if (isValidPath(pathString)) {
			folderPath = pathString;
			mainMenu();
		}
		else {
			System.out.println("This path is invalid. Please stay focused." + nL);
			enterTargetFolder();
		}
	}
	
	private Path enterNewInput() {
		System.out.println("nL");
		System.out.println("Please enter a path for the next input : " + nL);
		String inputPathString = entry.next();
		if (isValidPath(inputPathString)) {
			Path inputPath = Paths.get(inputPathString);
			representations = new OntologicalCommitment(folderPath);
			representations.whatIsThere(inputPath);
		}
		else {
			System.out.println("This path is invalid." + nL);
			enterTargetFolder();
		}
		
	}
	
	private static boolean isValidPath(String path) {
	    try {
	        Paths.get(path);
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
	    return true;
	}
	
	private void next() {
		
	}

}
