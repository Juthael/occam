package com.tregouet.occam.io.output.html.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuPrinter {

	public static final MenuPrinter INSTANCE = new MenuPrinter();

	private static final Path mainMenu = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io",
			"output", "html", "files", "mainMenu.txt");
	private static final Path problemSpaceMenu = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io",
			"output", "html", "files", "problemSpaceMenu.txt");
	private static final Path representationMenu = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam",
			"io", "output", "html", "files", "representationMenu.txt");
	private static final Path startPageMenu = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io",
			"output", "html", "files", "startPageMenu.txt");
	private static final String nL = System.lineSeparator();

	private MenuPrinter() {
	}

	public String print(MenuType menuType, String alinea) {
		Path path = null;
		switch (menuType) {
		case START_MENU:
			path = startPageMenu;
			break;
		case MAIN_MENU:
			path = mainMenu;
			break;
		case PROBLEM_SPACE_MENU:
			path = problemSpaceMenu;
			break;
		case REPRESENTATION_MENU:
			path = representationMenu;
			break;
		}
		StringBuilder sB = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path);
			String line = alinea + reader.readLine();
			while (line != null) {
				sB.append(alinea + line + nL);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sB.toString();
	}

}
