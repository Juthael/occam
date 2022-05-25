package com.tregouet.occam.io.output.html.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Supplier;

public class FootPrinter implements Supplier<String> {

	public static final FootPrinter INSTANCE = new FootPrinter();
	private static final Path footPath = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io",
			"output", "html", "files", "foot.txt");
	private static final String nL = System.lineSeparator();

	private FootPrinter() {
	}

	@Override
	public String get() {
		StringBuilder sB = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(footPath);
			String line = reader.readLine();
			while (line != null) {
				sB.append(line + nL);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sB.toString();
	}

}
