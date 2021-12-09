package com.tregouet.occam;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.tregouet.occam.io.output.IOntologicalCommitment;
import com.tregouet.occam.io.output.impl.OntologicalCommitment;

public class Proto {

	private IOntologicalCommitment representations;
	private static final String NL = System.lineSeparator();
	private final Scanner entry = new Scanner(System.in);
	private String folderPath = null;
	
	public Proto() {
		welcome();
	}
	
	private void welcome() {
		System.out.println("********************");
		System.out.println("********OCCAM********");
		System.out.println("********************");
		System.out.println(NL);
		enterTargetFolder();
	}
	
	private void mainMenu() {
		System.out.println(NL);
		System.out.println("**********MAIN MENU**********");
		System.out.println(NL);
		System.out.println("1 : enter new input" + NL);
		System.out.println("2 : set new target folder" + NL);
		System.out.println("3 : exit" + NL);
		int choice;
		choice = entry.nextInt();
		entry.nextLine();
		switch(choice) {
			case 1 : try {
				enterNewInput();
			} catch (IOException e) {
				System.out.println(e.getMessage() + NL);
				mainMenu();
			} 
				break;
			case 2 : enterTargetFolder();
				break;
			case 3 : System.out.println("Goodbye.");
				System.exit(0);
				break;
			default : 
				System.out.println("Please stay focused." + NL);
				mainMenu();
				break;
		}
	}
	
	private void enterTargetFolder() {
		System.out.println(NL);
		System.out.println("Please enter a path for the target folder : " + NL);
		String pathString = entry.nextLine();
		if (isValidPath(pathString)) {
			folderPath = pathString;
			mainMenu();
		}
		else {
			System.out.println("This path is invalid." + NL);
			enterTargetFolder();
		}
	}
	
	private void enterNewInput() throws IOException {
		System.out.println(NL);
		System.out.println("Please enter a path for the next input : " + NL);
		String inputPathString = entry.nextLine();
		if (isValidPath(inputPathString)) {
			Path inputPath = Paths.get(inputPathString);
			representations = new OntologicalCommitment(folderPath);
			try {
				representations.whatIsThere(inputPath);
				representations.generateHTML();
				outputMenu();
			} catch (IOException e) {
				System.out.println("An error has occurred." + NL);
				e.printStackTrace();
				enterNewInput();
			}
		}
		else {
			System.out.println("This path is invalid." + NL);
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
	
	private void outputMenu() {
		System.out.println(NL);
		if (representations.hasNextCategoricalStructure() 
				&& representations.hasNextTransitionFunctionOverCurrentCategoricalStructure()) {
			System.out.println("1 : generate a new transition function based on the current categorical structure." + NL);
			System.out.println("2 : generate a new transition function based on a new categorical structure." + NL);
			System.out.println("3 : back to main menu." + NL);
			int choice;
			choice = entry.nextInt();
			entry.nextLine();
			switch (choice) {
			case 1 : nextTransitionFunction();
				break;
			case 2 : nextCategoricalStructure();
				break;
			case 3 : mainMenu();
				break;
			default : System.out.println("Please stay focused." + NL);
				outputMenu();
				break;
			}
		}
		else if (representations.hasNextCategoricalStructure()) {
			System.out.println("No additional transition function can be generated from the current "
					+ "categorical structure." + NL);
			System.out.println("1 : generate a new transition function based on a new categorical structure." + NL);
			System.out.println("2 : back to main menu." + NL);
			int choice;
			choice = entry.nextInt();
			entry.nextLine();
			switch (choice) {
			case 1 : nextCategoricalStructure();
				break;
			case 2 : mainMenu();
				break;
			default : System.out.println("Please stay focused." + NL);
				outputMenu();
				break;
			}
		}
		else if (representations.hasNextTransitionFunctionOverCurrentCategoricalStructure()) {
			System.out.println("No additional categorical structure can be generated." + NL);
			System.out.println("1 : generate a new transition function based on the current categorical structure." 
					+ NL);
			System.out.println("2 : back to main menu." + NL);
			int choice;
			choice = entry.nextInt();
			entry.nextLine();
			switch (choice) {
			case 1 : nextTransitionFunction();
				break;
			case 2 : mainMenu();
				break;
			default : System.out.println("Please stay focused." + NL);
				outputMenu();
				break;
			}
		}
		else {
			System.out.println("No additional transition function can be generated." + NL);
			mainMenu();
		}
	}
	
	private void nextCategoricalStructure() {
		System.out.println(NL);
		try {
			representations.nextCategoryTree();
			representations.generateHTML();
		} catch (IOException e) {
			System.out.println("An error has occurred." + NL);
			e.printStackTrace();
			outputMenu();
		}
		System.out.println("A new transition function based on a new categorical structure has been generated." + NL);
		outputMenu();
	}
	
	private void nextTransitionFunction() {
		System.out.println(NL);
		try {
			representations.nextTransitionFunctionOverCurrentCategoricalStructure();
			representations.generateHTML();
		} catch (IOException e) {
			System.out.println("An error has occurred." + NL);
			e.printStackTrace();
			outputMenu();
		}
		System.out.println("A new transition function based on the current categorical structure has been generated."
				+ NL);
		outputMenu();
	}	

}
