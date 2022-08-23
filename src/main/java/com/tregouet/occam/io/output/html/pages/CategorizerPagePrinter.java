package com.tregouet.occam.io.output.html.pages;

import java.io.IOException;
import java.util.List;

import com.tregouet.occam.data.modules.categorization.ICategorizer;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.models.ConceptLatticePrinter;
import com.tregouet.occam.io.output.html.models.ContextPrinter;
import com.tregouet.occam.io.output.html.models.FootPrinter;
import com.tregouet.occam.io.output.html.models.HeaderPrinter;
import com.tregouet.occam.io.output.html.models.ProblemSpacePrinter;
import com.tregouet.occam.io.output.html.models.RepresentationPrinter;

public class CategorizerPagePrinter {

	public static final CategorizerPagePrinter INSTANCE = new CategorizerPagePrinter();
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

	private CategorizerPagePrinter() {
	}

	public String print(ICategorizer categorizer) throws IOException {
		List<IContextObject> objects = categorizer.getContext();
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
						.append(ProblemSpacePrinter.INSTANCE.print(categorizer, "problem_space", 3) + NL)
					.append(alinea[2] + "</section>")
					.append(alinea[2] + "<section>")
						.append(alinea[3] + "<header>" + NL)
							.append(alinea[4] + "<h3> Concept lattice </h3>")
						.append(alinea[3] + "</header>" + NL)
						.append(ConceptLatticePrinter.INSTANCE.print(categorizer, "concept_lattice", 3) + NL)
					.append(alinea[2] + "</section>")
				.append(alinea[1] + "</section>" + NL)
				.append(alinea[1] + "<hr>" + NL)
				.append(alinea[1] + "<section>" + NL)
					.append(alinea[2] + "<header>" + NL)
						.append(alinea[3] + "<h2> REPRESENTATIONS </h2>" + NL)
					.append(alinea[2] + "</header>" + NL)
				.append(alinea[1] + "<hr>" + NL)
					.append(RepresentationPrinter.INSTANCE.print(objects, categorizer.getActiveRepresentation(), 2))
				.append(alinea[1] + "</section>" + NL)
			.append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}

}
