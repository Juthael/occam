package com.tregouet.occam.io.output.html.representation_page;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.FigurePrinter;
import com.tregouet.occam.io.output.html.general.TablePrinter;
import com.tregouet.occam.io.output.html.problem_space_page.ProblemSpacePagePrinter;

public class RepresentationPrinter {

	public static final RepresentationPrinter INSTANCE = new RepresentationPrinter();
	private static final String nL = System.lineSeparator();
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;
	
	private RepresentationPrinter() {
	}

	private static String printAcceptedFacts(IRepresentation representation, int a) {
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

	private static String printAsymetricalSimilarityMatrix(List<IContextObject> context, IRepresentation representation,
			int a) {
		String[] head = new String[context.size()];
		int idx = 0;
		for (IContextObject obj : context)
			head[idx++] = Integer.toString(obj.iD());
		double[][] matrix = representation.getDescription().getSimilarityMetrics().getAsymmetricalSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> ASYMMETRICAL SIMILARITY MATRIX </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(TablePrinter.INSTANCE.print2DSquareTable(head, matrix, "Asymmetrical similarity matrix",
						a + 1) + nL)
			.append(alinea + "</section>" + nL);
		return sB.toString();
	}

	private static String printAutomatonGraph(IRepresentation representation, int a) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE.getTransitionFunctionViz()
				.apply(representation.getTransitionFunction(), "trans_func");
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> AUTOMATON TRANSITION FUNCTION </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, "Automaton transition function")
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printClassificationTree(IRepresentation representation, int a) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
				.apply(representation.getTreeOfConcepts(), "classification_tree");
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> CLASSIFICATION TREE </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, "Classification tree") + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printDescription(IRepresentation representation, int a) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE.getDescriptionViz()
				.apply(representation.getDescription(), "description");
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> GENUS/DIFFERENTIAE DESCRIPTION </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, "Genus/Differentiae description")
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printGeneralDescription(List<IContextObject> context, IRepresentation representation,
			int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
			.append(alinea[a + 1] + "<header>" + nL)
				.append(alinea[a + 2] + "<h3> GENERAL DESCRIPTION </h3>" + nL)
			.append(alinea[a + 1] + "</header>" + nL)
			.append(printDescription(representation, a + 1) + nL)
			.append(printSimilarityMatrix(context, representation, a + 1) + nL)
			.append(printAsymetricalSimilarityMatrix(context, representation, a + 1) + nL)
			.append(printTypicalityVector(context, representation, a + 1))
		.append(alinea[a] + "</section>");
		return sB.toString();
	}

	private static String printGeneratedFacts(IRepresentation representation, int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> GENERATED FACTS </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printAutomatonGraph(representation, a + 1) + nL)
				.append(printAcceptedFacts(representation, a + 1) + nL)
			.append(alinea[a] + "</section>");
		return sB.toString();
	}

	private static String printSimilarityMatrix(List<IContextObject> context, IRepresentation representation,
			int a) {
		String[] head = new String[context.size()];
		int idx = 0;
		for (IContextObject obj : context)
			head[idx++] = Integer.toString(obj.iD());
		double[][] matrix = representation.getDescription().getSimilarityMetrics().getSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> SIMILARITY MATRIX </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(TablePrinter.INSTANCE.print2DSquareTable(head, matrix, "Similarity matrix", a + 1) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printTypicalityVector(List<IContextObject> context, IRepresentation representation,
			int a) {
		String[] head = new String[context.size()];
		int idx = 0;
		for (IContextObject obj : context)
			head[idx++] = Integer.toString(obj.iD());
		double[] vector = representation.getDescription().getSimilarityMetrics().getTypicalityVector();
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> TYPICALITY VECTOR </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(TablePrinter.INSTANCE.print1DTable(head, vector, "Typicality vector", a + 1) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	public String print(List<IContextObject> context, IRepresentation representation, int a) {
		if (representation == null)
			return printNullRepresentation();
		return printNonNullRepresentation(context, representation, a);
	}
	
	private String printNonNullRepresentation(List<IContextObject> context, IRepresentation representation, int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h2> REPRESENTATION N." + Integer.toString(representation.iD()) + "</h2>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(alinea[a + 1] + "<hr>" + nL)
				.append(printGeneralDescription(context, representation, a + 1) + nL)
				.append(alinea[a + 1] + "<hr>" + nL)
				.append(printGeneratedFacts(representation, a + 1) + nL)
				.append(alinea[a + 1] + "<hr>" + nL)
				.append(printClassificationTree(representation, a + 1) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}	
	
	private String printNullRepresentation() {
		StringBuilder sB = new StringBuilder();
		String alinea = "   ";
		String alineaa = alinea + alinea;
		String alineaaa = alineaa + alinea;
		String alineaaaa = alineaaa + alinea;
		sB.append(alineaa + "<section>" + nL)
				.append(alineaaa + "<header>" + nL)
					.append(alineaaaa + "<h2> No representation has been selected. </h2>" + nL)
				.append(alineaaa + "</header>" + nL)
			.append(alineaa + "</section>" + nL)
			.append(alineaaa + "<hr>" + nL);
		return sB.toString();
	}

}
