package com.tregouet.occam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.common.base.Splitter;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.impl.ProblemSpace;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.LocalPaths;
import com.tregouet.occam.io.output.html.main_menu.MainMenuPrinter;
import com.tregouet.occam.io.output.html.problem_space_page.ProblemSpacePagePrinter;

public class PrototypeMenu {

	private static final String NL = System.lineSeparator();

	private static final String htmlFileName = "occam.html";

	private final Scanner entry = new Scanner(System.in);

	public PrototypeMenu() {
		welcome();
	}

	private void developRepresentation(IProblemSpace problemSpace) {
		System.out.println(NL);
		System.out.println("Please enter a representation ID : " + NL);
		Integer iD = null;
		try {
			iD = entry.nextInt();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			problemSpaceMenu(problemSpace);
		}
		Boolean result = problemSpace.develop(iD);
		if (result == null) {
			System.out.println("No representation has this ID. " + NL);
			problemSpaceMenu(problemSpace);
		} else {
			if (!result)
				System.out.println("This representation is fully developed already. " + NL);
			problemSpaceMenu(problemSpace);
		}
	}
	
	private void restrictProblemSpace(IProblemSpace problemSpace) {
		System.out.println(NL);
		System.out.println("Please enter IDs of representations to retain, separated by dots.");
		String entryString = null;
		try {
			entryString = entry.next();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			problemSpaceMenu(problemSpace);
		}
		Set<Integer> iDs = new HashSet<>();
		List<String> iDStrings = Splitter.on('.').splitToList(entryString);
		for (String iDString : iDStrings) {
			Integer iD = null;
			try {
				iD = Integer.parseInt(iDString);
			}
			catch (NumberFormatException e) {
				System.out.println("Entry is invalid");
				problemSpaceMenu(problemSpace);
			}
			iDs.add(iD);
		}
		Boolean result = problemSpace.restrictTo(iDs);
		if (result == null) {
			System.out.println("Some ID is missing in the problem space graph. " + NL);
			problemSpaceMenu(problemSpace);
		} else {
			if (!result)
				System.out.println("The specified set is not a restriction." + NL);
			problemSpaceMenu(problemSpace);
		}
	}

	private void displayRepresentation(IProblemSpace problemSpace) {
		System.out.println(NL);
		System.out.println("Please enter a representation ID : " + NL);
		Integer iD = null;
		try {
			iD = entry.nextInt();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			problemSpaceMenu(problemSpace);
		}
		Boolean result = problemSpace.display(iD);
		if (result == null) {
			System.out.println("No representation has this ID. " + NL);
			problemSpaceMenu(problemSpace);
		} else {
			if (!result)
				System.out.println("This representation is already displayed. " + NL);
			problemSpaceMenu(problemSpace);
		}
	}

	private void enterNewInput() throws IOException {
		System.out.println(NL);
		System.out.println("Please enter a path for the next input : " + NL);
		String inputPathString = entry.nextLine();
		if (isValidPath(inputPathString)) {
			Occam.initialize();
			Path inputPath = Paths.get(inputPathString);
			List<IContextObject> objects = GenericFileReader.getContextObjects(inputPath);
			ProblemSpace problemSpace = new ProblemSpace(new HashSet<>(objects));
			problemSpaceMenu(problemSpace);
		} else {
			System.out.println("This path is invalid." + NL);
			enterTargetFolder();
		}
	}

	private void enterTargetFolder() {
		System.out.println(NL);
		System.out.println("Please enter a path for the target folder : " + NL);
		String pathString = entry.nextLine();
		if (isValidPath(pathString)) {
			LocalPaths.INSTANCE.setTargetFolderPath(pathString);
			mainMenu();
		} else {
			System.out.println("This path is invalid." + NL);
			enterTargetFolder();
		}
	}

	private void generate(String htmlPage) throws IOException {
		String sep = File.separator;
		File pageFile = new File(LocalPaths.INSTANCE.getTargetFolderPath() + sep + htmlFileName);
		FileWriter writer = new FileWriter(pageFile);
		writer.write(htmlPage);
		writer.close();
	}

	private void mainMenu() {
		try {
			generate(MainMenuPrinter.INSTANCE.get());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(NL);
		System.out.println("**********MAIN MENU**********");
		System.out.println(NL);
		System.out.println("1 : enter new input" + NL);
		System.out.println("2 : set new target folder" + NL);
		System.out.println("3 : exit" + NL);
		int choice;
		choice = entry.nextInt();
		entry.nextLine();
		switch (choice) {
		case 1:
			try {
				enterNewInput();
			} catch (IOException e) {
				System.out.println(e.getMessage() + NL);
				mainMenu();
			}
			break;
		case 2:
			enterTargetFolder();
			break;
		case 3:
			System.out.println("Goodbye.");
			System.exit(0);
			break;
		default:
			System.out.println("Please stay focused." + NL);
			mainMenu();
			break;
		}
	}

	private void problemSpaceMenu(IProblemSpace problemSpace) {
		try {
			String htmlPage = ProblemSpacePagePrinter.INSTANCE.print(problemSpace);
			generate(htmlPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(NL);
		System.out.println("**********PROBLEM SPACE MENU**********");
		System.out.println(NL);
		System.out.println("1 : enter the ID of a representation to display." + NL);
		System.out.println("2 : enter the ID of a representation to develop." + NL);
		System.out.println("3 : restrict the problem space to some subset of states." + NL);
		System.out.println("4 : back to main menu" + NL);
		int choice = 0;
		try {
			choice = entry.nextInt();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			problemSpaceMenu(problemSpace);
		}
		switch (choice) {
		case 1:
			displayRepresentation(problemSpace);
			break;
		case 2:
			developRepresentation(problemSpace);
			break;
		case 3 : 
			restrictProblemSpace(problemSpace);
		case 4:
			mainMenu();
			break;
		default:
			System.out.println("Please stay focused." + NL);
			mainMenu();
			break;
		}
	}

	private void welcome() {
		System.out.println("********************");
		System.out.println("********OCCAM********");
		System.out.println("********************");
		System.out.println(NL);
		enterTargetFolder();
	}

	private static boolean isValidPath(String path) {
		try {
			Paths.get(path);
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		return true;
	}

}
