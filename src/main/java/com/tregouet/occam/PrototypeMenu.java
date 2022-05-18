package com.tregouet.occam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NavigableSet;
import java.util.Scanner;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationSortedSetBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.ICompleteRepresentations;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.LocalPaths;
import com.tregouet.occam.io.output.html.main_menu.MainMenuPrinter;
import com.tregouet.occam.io.output.html.problem_space_page.ProblemSpacePagePrinter;
import com.tregouet.occam.io.output.html.representation_page.RepresentationPagePrinter;

public class PrototypeMenu {

	private static final String NL = System.lineSeparator();

	private static final String htmlFileName = "occam.html";

	private final Scanner entry = new Scanner(System.in);

	public PrototypeMenu() {
		welcome();
	}

	private static boolean isValidPath(String path) {
		try {
			Paths.get(path);
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		return true;
	}

	private void enterNewInput() throws IOException {
		System.out.println(NL);
		System.out.println("Please enter a path for the next input : " + NL);
		String inputPathString = entry.nextLine();
		if (isValidPath(inputPathString)) {
			Path inputPath = Paths.get(inputPathString);
			List<IContextObject> objects = GenericFileReader.getContextObjects(inputPath);
			RepresentationSortedSetBuilder completeRepBldr = GeneratorsAbstractFactory.INSTANCE
					.getRepresentationSortedSetBuilder();
			ICompleteRepresentations completeRepresentations = completeRepBldr.apply(objects);
			ProblemSpaceBuilder pbSpaceBldr = GeneratorsAbstractFactory.INSTANCE.getProblemSpaceBuilder();
			IProblemSpace pbSpace = pbSpaceBldr.apply(completeRepresentations);
			problemSpaceMenu(objects, pbSpace);
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

	private void problemSpaceMenu(List<IContextObject> objects, IProblemSpace problemSpace) {
		try {
			String htmlPage = ProblemSpacePagePrinter.INSTANCE.print(objects, problemSpace);
			generate(htmlPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(NL);
		System.out.println("**********PROBLEM SPACE MENU**********");
		System.out.println(NL);
		System.out.println("1 : select a representation" + NL);
		System.out.println("2 : add a new representation (Not implemented yet)" + NL);
		System.out.println("3 : back to main menu" + NL);
		int choice = 0;
		try {
			choice = entry.nextInt();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			problemSpaceMenu(objects, problemSpace);
		}
		switch (choice) {
		case 1:
			IRepresentation representation = selectARepresentation(objects, problemSpace);
			representationMenu(objects, problemSpace, representation);
			break;
		case 2:
			System.out.print("This functionality is not available yet.");
			problemSpaceMenu(objects, problemSpace);
			break;
		case 3:
			mainMenu();
			break;
		default:
			System.out.println("Please stay focused." + NL);
			mainMenu();
			break;
		}
	}

	private void representationMenu(List<IContextObject> objects, IProblemSpace problemSpace,
			IRepresentation representation) {
		try {
			String htmlPage = RepresentationPagePrinter.INSTANCE.print(objects, representation);
			generate(htmlPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(NL);
		System.out.println("**********REPRESENTATION MENU**********");
		System.out.println(NL);
		System.out.println("1 : display next representation (score descending order)" + NL);
		System.out.println("2 : display previous representation (score ascending order)" + NL);
		System.out.println("3 : back to main menu" + NL);
		int choice = 0;
		try {
			choice = entry.nextInt();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			problemSpaceMenu(objects, problemSpace);
		}
		switch (choice) {
		case 1:
			NavigableSet<IRepresentation> lowerRep = problemSpace.getSortedSetOfStates().tailSet(representation, false);
			if (lowerRep.isEmpty()) {
				System.out.println("There is no next representation.");
				representationMenu(objects, problemSpace, representation);
			} else
				representationMenu(objects, problemSpace, lowerRep.first());
			break;
		case 2:
			NavigableSet<IRepresentation> higherRep = problemSpace.getSortedSetOfStates().headSet(representation,
					false);
			if (higherRep.isEmpty()) {
				System.out.println("There is no previous representation.");
				representationMenu(objects, problemSpace, representation);
			} else
				representationMenu(objects, problemSpace, higherRep.last());
			break;
		case 3:
			problemSpaceMenu(objects, problemSpace);
			break;
		default:
			System.out.println("Please stay focused." + NL);
			representationMenu(objects, problemSpace, representation);
			break;
		}
	}

	private IRepresentation selectARepresentation(List<IContextObject> objects, IProblemSpace problemSpace) {
		System.out.println(NL);
		System.out.println("Please enter a representation ID : " + NL);
		Integer iD = null;
		try {
			iD = entry.nextInt();
			entry.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			return selectARepresentation(objects, problemSpace);
		}
		IRepresentation representation = (IRepresentation) problemSpace.getStateWithID(iD);
		if (representation == null) {
			System.out.println("No representation has this ID. " + NL);
			return selectARepresentation(objects, problemSpace);
		}
		return representation;
	}

	private void welcome() {
		System.out.println("********************");
		System.out.println("********OCCAM********");
		System.out.println("********************");
		System.out.println(NL);
		enterTargetFolder();
	}

}
