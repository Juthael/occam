package com.tregouet.occam.io.output.html.models;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.io.output.html.pages.CategorizerPagePrinter;

public class FactPrinter {

	public static final FactPrinter INSTANCE = new FactPrinter();
	private static final String nL = System.lineSeparator();
	private static final String[] alinea = CategorizerPagePrinter.alinea;

	private FactPrinter() {
	}

	public String print(IRepresentation representation, int a) {
		Map<Integer, List<String>> objID2acceptedFacts =
				representation.mapParticularIDsToFactualDescription(FormattersAbstractFactory.INSTANCE.getFactDisplayer());
		NavigableSet<Integer> objIDs = new TreeSet<>(objID2acceptedFacts.keySet());
		String[] head = new String[objIDs.size()];
		String[] facts = new String[objIDs.size()];
		int idx = 0;
		for (Integer iD : objIDs) {
			head[idx] = setHeadOfAcceptedFactsArray(representation, iD);
			StringBuilder sB = new StringBuilder();
			for (String factString : objID2acceptedFacts.get(iD))
				sB.append(factString + " <br> " + nL);
			facts[idx] = sB.toString();
			idx++;
		}
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> ACCEPTED FACTS </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(TablePrinter.INSTANCE.printStringVerticalTable(head, facts, "Accepted facts",
							a + 1) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String setHeadOfAcceptedFactsArray(IRepresentation representation, Integer conceptID) {
		if (representation.isFullyDeveloped())
			return conceptID.toString();
		else {
			TreeSet<Integer> extent = new TreeSet<>(representation.getExtentIDs(conceptID));
			if (extent.size() == 1)
				return conceptID.toString();
			else {
				StringBuilder sB = new StringBuilder();
				sB.append(conceptID.toString() + " = { ");
				Iterator<Integer> extentIte = extent.iterator();
				while (extentIte.hasNext()) {
					sB.append(extentIte.next().toString());
					if (extentIte.hasNext())
						sB.append(", ");
				}
				sB.append(" }");
				return sB.toString();
			}
		}
	}

}
