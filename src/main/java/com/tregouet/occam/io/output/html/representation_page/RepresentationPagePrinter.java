package com.tregouet.occam.io.output.html.representation_page;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.ContextPrinter;
import com.tregouet.occam.io.output.html.general.FigurePrinter;
import com.tregouet.occam.io.output.html.general.FootPrinter;
import com.tregouet.occam.io.output.html.general.HeaderPrinter;
import com.tregouet.occam.io.output.html.general.TablePrinter;

public class RepresentationPagePrinter {

	public static final RepresentationPagePrinter INSTANCE = new RepresentationPagePrinter();
	private static final String nL = System.lineSeparator();

	private RepresentationPagePrinter() {
	}

	private static String printAcceptedFacts(IRepresentation representation, String alinea) {
		Map<Integer, List<String>> objID2acceptedFacts = representation.mapParticularIDsToFactualDescription(FormattersAbstractFactory.INSTANCE.getFactDisplayer());
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
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> ACCEPTED FACTS </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(TablePrinter.INSTANCE.printStringTableWithOptionalSubHead(head, null, facts, "Accepted facts",
						alineaaa) + nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}
	
	private static String setHeadOfAcceptedFactsArray(IRepresentation representation, Integer conceptID) {
		if (representation instanceof ICompleteRepresentation)
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

	private static String printAsymetricalSimilarityMatrix(List<IContextObject> objects, IRepresentation representation,
			String alinea) {
		String[] head = new String[objects.size()];
		int idx = 0;
		for (IContextObject obj : objects)
			head[idx++] = Integer.toString(obj.iD());
		double[][] matrix = representation.getDescription().getSimilarityMetrics().getAsymmetricalSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> ASYMMETRICAL SIMILARITY MATRIX </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(TablePrinter.INSTANCE.print2DSquareTable(head, matrix, "Asymmetrical similarity matrix",
						alineaaa) + nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printAutomatonGraph(IRepresentation representation, String alinea) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE.getTransitionFunctionViz()
				.apply(representation.getTransitionFunction(), "trans_func");
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> AUTOMATON TRANSITION FUNCTION </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, alineaaa, "Automaton transition function")
						+ nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printClassificationTree(IRepresentation representation, String alinea) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
				.apply(representation.getTreeOfConcepts(), "classification_tree");
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> CLASSIFICATION TREE </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, alineaaa, "Classification tree") + nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printContext(List<IContextObject> objects, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> CONTEXT </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(ContextPrinter.INSTANCE.print(objects, alineaaa) + nL).append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printDescription(IRepresentation representation, String alinea) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE.getDescriptionViz()
				.apply(representation.getDescription(), "description");
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> GENUS/DIFFERENTIAE DESCRIPTION </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, alineaaa, "Genus/Differentiae description")
						+ nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printGeneralDescription(List<IContextObject> objects, IRepresentation representation,
			String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alineaa + "<section>" + nL).append(alineaaa + "<header>" + nL)
				.append(alineaaaa + "<h3> GENERAL DESCRIPTION </h3>" + nL).append(alineaaa + "</header>" + nL)
				.append(printContext(objects, alineaaa) + nL).append(printDescription(representation, alineaaa) + nL)
				.append(printSimilarityMatrix(objects, representation, alineaaa) + nL)
				.append(printAsymetricalSimilarityMatrix(objects, representation, alineaaa) + nL)
				.append(printTypicalityVector(objects, representation, alineaaa)).append(alineaa + "</section>");
		return sB.toString();
	}

	private static String printGeneratedFacts(IRepresentation representation, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alineaa + "<section>" + nL).append(alineaaa + "<header>" + nL)
				.append(alineaaaa + "<h3> GENERATED FACTS </h3>" + nL).append(alineaaa + "</header>" + nL)
				.append(printAutomatonGraph(representation, alineaaaa) + nL)
				.append(printAcceptedFacts(representation, alineaaaa) + nL).append(alineaa + "</section>");
		return sB.toString();
	}

	private static String printSimilarityMatrix(List<IContextObject> objects, IRepresentation representation,
			String alinea) {
		String[] head = new String[objects.size()];
		int idx = 0;
		for (IContextObject obj : objects)
			head[idx++] = Integer.toString(obj.iD());
		double[][] matrix = representation.getDescription().getSimilarityMetrics().getSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> SIMILARITY MATRIX </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(TablePrinter.INSTANCE.print2DSquareTable(head, matrix, "Similarity matrix", alineaaa) + nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printTypicalityVector(List<IContextObject> objects, IRepresentation representation,
			String alinea) {
		String[] head = new String[objects.size()];
		int idx = 0;
		for (IContextObject obj : objects)
			head[idx++] = Integer.toString(obj.iD());
		double[] vector = representation.getDescription().getSimilarityMetrics().getTypicalityVector();
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		sB.append(alinea + "<section>" + nL).append(alineaa + "<header>" + nL)
				.append(alineaaa + "<h3> TYPICALITY VECTOR </h3>" + nL).append(alineaa + "</header>" + nL)
				.append(TablePrinter.INSTANCE.print1DTable(head, vector, "Typicality vector", alineaaa) + nL)
				.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	public String print(List<IContextObject> objects, IRepresentation representation) {
		StringBuilder sB = new StringBuilder();
		String alinea = "   ";
		String alineaa = alinea + alinea;
		String alineaaa = alineaa + alinea;
		String alineaaaa = alineaaa + alinea;
		sB.append(HeaderPrinter.INSTANCE.get() + nL).append(alineaa + "<section>" + nL)
				.append(alineaaa + "<header>" + nL)
				.append(alineaaaa + "<h2> REPRESENTATION N." + Integer.toString(representation.iD()) + "</h2>" + nL)
				.append(alineaaa + "</header>" + nL).append(alineaaa + "<hr>" + nL)
				.append(printGeneralDescription(objects, representation, alineaa) + nL).append(alineaaa + "<hr>" + nL)
				.append(printGeneratedFacts(representation, alineaa) + nL).append(alineaaa + "<hr>" + nL)
				.append(printClassificationTree(representation, alineaaa) + nL).append(alineaa + "</section>" + nL)
				.append(FootPrinter.INSTANCE.get() + nL);
		return sB.toString();
	}

}
