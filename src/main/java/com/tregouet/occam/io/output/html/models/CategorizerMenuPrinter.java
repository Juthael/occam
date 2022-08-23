package com.tregouet.occam.io.output.html.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Supplier;

public class CategorizerMenuPrinter implements Supplier<String> {

	public static final CategorizerMenuPrinter INSTANCE = new CategorizerMenuPrinter();
	private static final Path mainMenuPath = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io",
			"output", "html", "files", "mainMenu.txt");
	private static final String nL = System.lineSeparator();

	private CategorizerMenuPrinter() {
	}

	@Override
	public String get() {
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get());
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(mainMenuPath);
			String line = reader.readLine();
			while (line != null) {
				sB.append(line + nL);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sB.append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}

}
