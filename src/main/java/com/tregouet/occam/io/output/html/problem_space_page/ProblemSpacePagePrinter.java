package com.tregouet.occam.io.output.html.problem_space_page;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;
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
		sB.append(HeaderPrinter.INSTANCE.get() + NL).append(alineaa + "<hr>" + NL).append(alineaa + "<section>" + NL)
				.append(alineaaa + "<header>" + NL).append(alineaaaa + "<h2> CONTEXT </h2>" + NL)
				.append(alineaaa + "</header>" + NL).append(ContextPrinter.INSTANCE.print(objects, alineaaa) + NL)
				.append(alineaa + "</section>").append(alineaa + "<hr>" + NL).append(alineaa + "<section>" + NL)
				.append(alineaaa + "<header>" + NL).append(alineaaaa + "<h2> PROBLEM SPACE </h2>" + NL)
				.append(alineaaa + "</header>" + NL)
				.append(ProblemSpacePrinter.INSTANCE.print(problemSpace, "problem_space", alineaaa) + NL)
				.append(alineaa + "</section>" + NL).append(alineaa + "<hr>" + NL).append(alineaa + "<section>" + NL)
				.append(alineaaa + "<header>" + NL).append(alineaaaa + "<h2> SCORES </h2>" + NL)
				.append(alineaaa + "</header>" + NL).append(printRepresentationList(problemSpace, alineaaa) + NL)
				.append(alineaa + "</section>" + NL).append(FootPrinter.INSTANCE.get());
		return sB.toString();
	}

	private String printRepresentationList(IProblemSpace problemSpace, String alinea) {
		NavigableSet<IRepresentation> sortedRep = problemSpace.getSortedSetOfStates();
		String[] head = new String[sortedRep.size()];
		String[] table = new String[sortedRep.size()];
		int idx = 0;
		Iterator<IRepresentation> descendingIte = sortedRep.descendingIterator();
		while (descendingIte.hasNext()) {
			IRepresentation next = descendingIte.next();
			head[idx] = Integer.toString(next.iD());
			table[idx] = next.score().toString();
			idx++;
		}
		return TablePrinter.INSTANCE.printStringTableWithOptionalSubHead(head, null, table, "Representation scores",
				alinea);
	}

}
