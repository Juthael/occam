package com.tregouet.occam.io.output.html.pages;

import java.io.IOException;
import java.util.List;

import com.tregouet.occam.data.modules.sorting.ISorter;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.models.ConceptLatticePrinter;
import com.tregouet.occam.io.output.html.models.ContextPrinter;
import com.tregouet.occam.io.output.html.models.FootPrinter;
import com.tregouet.occam.io.output.html.models.HeaderPrinter;
import com.tregouet.occam.io.output.html.models.ProblemSpacePrinter;
import com.tregouet.occam.io.output.html.models.RepresentationPrinter;

public class SorterPagePrinter {

	public static final SorterPagePrinter INSTANCE = new SorterPagePrinter();
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

	private SorterPagePrinter() {
	}

	public String print(ISorter sorter) throws IOException {
		List<IContextObject> objects = sorter.getContext();
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + NL)
			.append(alinea[0] + "<hr>" + NL)
				.append(alinea[1] + "<section>" + NL)
					.append(alinea[2] + "<header>" + NL)
						.append(alinea[3] + "<h2> CONTEXT </h2>" + NL)
					.append(alinea[2] + "</header>" + NL)
					.append(ContextPrinter.INSTANCE.print(objects, 2) + NL)
				.append(alinea[1] + "</section>")
				.append(alinea[1] + "<hr>" + NL)
				.append(alinea[1] + "<section>" + NL)
					.append(alinea[2] + "<header>" + NL)
						.append(alinea[3] + "<h2> PROBLEM SPACE </h2>" + NL)
					.append(alinea[2] + "</header>" + NL)
					.append(alinea[2] + "<section>")
						.append(alinea[3] + "<header>" + NL)
							.append(alinea[4] + "<h3> Problem space graph </h3>")
						.append(alinea[3] + "</header>" + NL)
						.append(ProblemSpacePrinter.INSTANCE.print(sorter, "problem_space", 3) + NL)
					.append(alinea[2] + "</section>")
				.append(alinea[1] + "</section>" + NL)
				.append(alinea[1] + "<hr>" + NL)
				.append(alinea[1] + "<section>" + NL)
					.append(alinea[2] + "<header>" + NL)
						.append(alinea[3] + "<h2> REPRESENTATIONS </h2>" + NL)
					.append(alinea[2] + "</header>" + NL)
				.append(alinea[1] + "<hr>" + NL)
					.append(RepresentationPrinter.INSTANCE.print(objects, sorter.getActiveRepresentation(), 2))
				.append(alinea[1] + "</section>" + NL)
				.append(alinea[1] + "<section>" + NL)
					.append(alinea[2] + "<header>" + NL)
						.append(alinea[3] + "<h2> CONCEPT LATTICE </h2>" + NL)
					.append(alinea[2] + "</header>" + NL)
				.append(alinea[1] + "<hr>" + NL)
				.append(ConceptLatticePrinter.INSTANCE.print(sorter, "concept_lattice", 2) + NL)
			.append(alinea[1] + "</section>" + NL)
			.append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}

}
