package com.tregouet.occam.io.output.html.problem_space_page;

import java.io.IOException;
import java.util.List;

import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.ContextPrinter;
import com.tregouet.occam.io.output.html.general.FootPrinter;
import com.tregouet.occam.io.output.html.general.HeaderPrinter;

public class ProblemSpacePagePrinter {

	public static final ProblemSpacePagePrinter INSTANCE = new ProblemSpacePagePrinter();
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

	private ProblemSpacePagePrinter() {
	}

	public String print(IProblemSpace problemSpace) throws IOException {
		List<IContextObject> objects = problemSpace.getContext();
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
						.append(ProblemSpacePrinter.INSTANCE.print(problemSpace, "problem_space", 3) + NL)
					.append(alinea[2] + "</section>")
					.append(alinea[2] + "<section>")
						.append(alinea[3] + "<header>" + NL)
							.append(alinea[4] + "<h3> Concept lattice </h3>")
						.append(alinea[3] + "</header>" + NL)
						.append(ConceptLatticePrinter.INSTANCE.print(problemSpace, "concept_lattice", 3) + NL)
					.append(alinea[2] + "</section>")
				.append(alinea[1] + "</section>" + NL)
				.append(alinea[1] + "<hr>" + NL)
				.append(alinea[1] + "<section>" + NL)
					.append(alinea[2] + "<header>" + NL)
						.append(alinea[3] + "<h2> REPRESENTATIONS </h2>" + NL)
					.append(alinea[2] + "</header>" + NL)
				.append(alinea[1] + "<hr>" + NL)
					.append(RepresentationPrinter.INSTANCE.print(objects, problemSpace.getActiveRepresentation(), 2))
				.append(alinea[1] + "</section>" + NL)
			.append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}	

}
