package com.tregouet.occam.io.output.html.pages;

import java.io.IOException;
import java.util.List;

import com.tregouet.occam.data.modules.comparison.IComparator;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.models.ContextPrinter;
import com.tregouet.occam.io.output.html.models.FootPrinter;
import com.tregouet.occam.io.output.html.models.HeaderPrinter;
import com.tregouet.occam.io.output.html.models.DescriptionPrinter;
import com.tregouet.occam.io.output.html.models.TablePrinter;

public class ComparatorPagePrinter {

	public static final ComparatorPagePrinter INSTANCE = new ComparatorPagePrinter();
	public static final String NL = System.lineSeparator();
	public static final String[] alinea = new String[] {
			"   ",
			"      ",
			"         ",
			"            ",
			"               ",
			"                  ",
			"                     ",
			"                        ",
			"                           ",
			"                              ",
			"                                 "};

	private ComparatorPagePrinter() {
	}

	public String print(IComparator comparator) throws IOException {
		List<IContextObject> objects = comparator.getContext();
		String[] tableHead = new String[objects.size()];
		for (int i = 0 ; i < tableHead.length ; i++)
			tableHead[i] = Integer.toString(objects.get(i).iD());
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + NL)
			.append(alinea[0] + "<hr>" + NL)
			.append(alinea[1] + "<section>" + NL)
				.append(alinea[2] + "<header>" + NL)
					.append(alinea[3] + "<h2> CONTEXT </h2>" + NL)
				.append(alinea[2] + "</header>" + NL)
				.append(ContextPrinter.INSTANCE.print(objects, 2) + NL)
			.append(alinea[1] + "</section>" +NL)
			.append(alinea[1] + "<hr>" + NL)
			.append(alinea[1] + "<section>" + NL)
				.append(alinea[2] + "<header>" + NL)
					.append(alinea[3] + "<h2> SIMILARITY METRICS </h2>" + NL)
				.append(alinea[2] + "</header>" + NL)
				.append(alinea[2] + "<section>" + NL)
					.append(alinea[3] + "<header>" + NL)
						.append(alinea[4] + "<h3> Similarity matrix </h3>" + NL)
					.append(alinea[3] + "</header>" + NL)
					.append(TablePrinter.INSTANCE.print2DSquareTable(
							tableHead, comparator.getSimilarityStringMatrix(), "Similarity matrix", 3))
				.append(alinea[2] + "</section>")
				.append(alinea[2] + "<section>" + NL)
					.append(alinea[3] + "<header>" + NL)
						.append(alinea[4] + "<h3> Asymmetrical similarity matrix </h3>" + NL)
					.append(alinea[3] + "</header>" + NL)
					.append(TablePrinter.INSTANCE.print2DSquareTable(
							tableHead, comparator.getAsymmetricalSimilarityStringMatrix(), "Asymmetrical similarity matrix", 3))
				.append(alinea[2] + "</section>" + NL)
				.append(alinea[2] + "<section>" + NL)
					.append(alinea[3] + "<header>" + NL)
					.append(alinea[4] + "<h3> Typicality </h3>" + NL)
					.append(alinea[3] + "</header>" + NL)
					.append(TablePrinter.INSTANCE.print1DTable(
							tableHead, comparator.getTypicalityStringVector(), "Typicality", 3))
				.append(alinea[2] + "</section>" + NL)
				.append(alinea[2] + "<section>" + NL)
					.append(alinea[3] + "<header>" + NL)
						.append(alinea[4] + "<h3> Difference </h3>" + NL)
					.append(alinea[3] + "</header>" + NL)
					.append(TablePrinter.INSTANCE.print2DSquareTable(
							tableHead, comparator.getDifferenceStringMatrix(), "Difference matrix", 3))
				.append(alinea[2] + "</section>" + NL)
			.append(alinea[1] + "</section>" + NL)
			.append(alinea[1] + "<hr>" + NL)
			.append(alinea[1] + "<section>" + NL)
				.append(alinea[2] + "<header>" + NL)
					.append(alinea[3] + "<h2> REPRESENTATIONS </h2>" + NL)
				.append(alinea[2] + "</header>" + NL)
			.append(alinea[1] + "<hr>" + NL)
				.append(alinea[2] + "<section>" + NL)
					.append(alinea[3] + "<header>" + NL)
						.append(alinea[4] + "<h3> Representation of similarity </h3>" + NL)
					.append(alinea[3] + "</header>" + NL)
					.append(DescriptionPrinter.INSTANCE
							.print(objects, comparator.getActiveRepresentationOfSimilarity(), 3, "similarity", true, true))
				.append(alinea[2] + "</section>" + NL)
				.append(alinea[2] + "<section>" + NL)
					.append(alinea[3] + "<header>" + NL)
						.append(alinea[4] + "<h3> Representation of differences </h3>" + NL)
					.append(alinea[3] + "</header>" + NL)
				.append(DescriptionPrinter.INSTANCE
						.print(objects, comparator.getActiveRepresentationOfDifferences(), 3, "differences", true, true))
				.append(alinea[2] + "</section>" + NL)
			.append(alinea[1] + "</section>" + NL)
		.append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}

}
