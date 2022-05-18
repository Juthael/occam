package com.tregouet.occam.io.output.html.problem_space_page;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.ContextPrinter;
import com.tregouet.occam.io.output.html.general.FootPrinter;
import com.tregouet.occam.io.output.html.general.HeaderPrinter;
import com.tregouet.occam.io.output.html.general.TablePrinter;

public class ProblemSpacePagePrinter {

	public static final ProblemSpacePagePrinter INSTANCE = new ProblemSpacePagePrinter();
	public static final String NL = System.lineSeparator();

	private ProblemSpacePagePrinter() {
	}

	public String print(List<IContextObject> objects, IProblemSpace problemSpace) throws IOException {
		String alinea = "   ";
		String alineaa = alinea + alinea;
		String alineaaa = alineaa + alinea;
		String alineaaaa = alineaaa + alinea;
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + NL).append(alineaa + "<hr>" + NL)
			.append(alineaa + "<section>" + NL)
				.append(alineaaa + "<header>" + NL)
					.append(alineaaaa + "<h2> CONTEXT </h2>" + NL)
				.append(alineaaa + "</header>" + NL).append(ContextPrinter.INSTANCE.print(objects, alineaaa) + NL)
			.append(alineaa + "</section>").append(alineaa + "<hr>" + NL)
			.append(alineaa + "<section>" + NL)
				.append(alineaaa + "<header>" + NL)
					.append(alineaaaa + "<h2> PROBLEM SPACE </h2>" + NL)
				.append(alineaaa + "</header>" + NL)
				.append(ProblemSpacePrinter.INSTANCE.print(problemSpace, "problem_space", alineaaa) + NL)
			.append(alineaa + "</section>" + NL).append(alineaa + "<hr>" + NL)
			.append(alineaa + "<section>" + NL)
			//HERE
			.append(alineaa + "</section>" + NL)
			.append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}

}
