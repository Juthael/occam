package com.tregouet.occam.io.input.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Particular;

/**
 * @author Gael Tregouet
 */
public abstract class GenericFileReader {

	public static final String SEPARATOR = "/";
	public static final char NAME_SYMBOL = '@';
	public static final String LABEL_DENOTATION_SYMBOL = "*";
	public static final char IGNORE = '%';

	private GenericFileReader() {
	}

	public static List<IContextObject> getContextObjects(BufferedReader reader) throws IOException {
		List<IContextObject> objects = new ArrayList<>();
		String line;
		List<List<String>> currObjConstructsAsLists = new ArrayList<>();
		String currObjName = null;
		do {
			line = reader.readLine();
			if (line == null || line.equals(SEPARATOR)) {
				if (!currObjConstructsAsLists.isEmpty()) {
					if (currObjName == null)
						objects.add(new Particular(currObjConstructsAsLists));
					else
						objects.add(new Particular(currObjConstructsAsLists, currObjName));
					currObjConstructsAsLists = new ArrayList<>();
					currObjName = null;
				}
			} else if (line.length() > 1 && line.charAt(0) == NAME_SYMBOL) {
				currObjName = line.substring(1);
			} else if (line.length() == 0 || (line.length() > 1 && line.charAt(0) == IGNORE)) {
				//do nothing
			} else {
				currObjConstructsAsLists.add(Arrays.asList(line.split(SEPARATOR)));
			}
		} while (line != null);
		cardinalityTest(objects);
		unicityTest(objects);
		return objects;
	}

	/**
	 * <p>
	 * Generates a list of 'context object' ({@link IContextObject}) out of a
	 * {@link Path} parameter pointing to a text file.
	 * </p>
	 *
	 * <p>
	 * These writing rules must be respected or exceptions will be thrown : <br>
	 * 1-The text contains one or more set of constructs generated using a
	 * context-free grammar. <br>
	 * 2-Every set of constructs begins with a line containing only the character
	 * '/' <br>
	 * 3-After this line, an optional line may provide a name of the next object, in
	 * the form '@NAME' 4-A construct is a list of symbols from the grammar at use,
	 * separated by the character '/'. New construct, new line. No empty line. <br>
	 * </p>
	 *
	 * @param path points to a text file (UTF-8) that must respect the rules
	 *             described above.
	 * @return a 'context object', i.e. a sublist of the language generated by the
	 *         grammar at use.
	 * @throws FileReaderException   if BufferReader instantiation fails
	 * @throws InvalidInputException
	 */
	public static List<IContextObject> getContextObjects(Path path) throws IOException {

		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("GenericFileReader.getContextObjects(Path) : "
					+ "BufferedReader ccannot be instantiated." + System.lineSeparator() + e.getMessage());
		}
		return getContextObjects(reader);
	}

	private static void cardinalityTest(List<IContextObject> objects) throws IOException {
		if (objects.size() < 2)
			throw new IOException("Invalid input : a context should contain at least two sets of " + "constructs.");
	}

	private static void unicityTest(List<IContextObject> objects) throws IOException {
		for (int i = 0; i < objects.size() - 1; i++) {
			Set<IConstruct> iObjConstructs = new HashSet<>(objects.get(i).getConstructs());
			for (int j = i + 1; j < objects.size(); j++) {
				Set<IConstruct> jObjConstructs = new HashSet<>(objects.get(j).getConstructs());
				if (iObjConstructs.equals(jObjConstructs))
					throw new IOException(
							"Invalid input : a context should not contain two identical set of " + "constructs.");
			}
		}
	}

}