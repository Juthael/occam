package com.tregouet.occam.io.output.html.general;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.io.output.html.problem_space_page.ProblemSpacePagePrinter;

public class FactPrinter {
	
	public static final FactPrinter INSTANCE = new FactPrinter();
	private static final String nL = System.lineSeparator();
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;
	//HERE
	private static int count = 0;
	//HERE
	
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
				.append(TablePrinter.INSTANCE.printStringTableWithOptionalSubHead(head, null, facts, "Accepted facts",
							a + 1) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}
	
	private static String setHeadOfAcceptedFactsArray(IRepresentation representation, Integer conceptID) {
		if (representation.isFullyDeveloped())
			return conceptID.toString();
		else {
			//HERE
			try {
				TreeSet<Integer> test = new TreeSet<>(representation.getExtentIDs(conceptID));
			}
			catch (Exception e) {
				System.out.println("concept not found (" + count + ") : " + conceptID.toString());
				System.out.println("Extent map : ");
				Map<Integer, List<Integer>> extentMap = representation.getClassification().mapConceptID2ExtentIDs();
				for (Integer iD : extentMap.keySet()) 
					System.out.println(iD.toString() + " : " + extentMap.get(iD));
				VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(representation.getClassification().asGraph(), "FactPrinter_error" + count++);
				System.out.println(System.lineSeparator());
				System.exit(0);
			}
			//HERE
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
