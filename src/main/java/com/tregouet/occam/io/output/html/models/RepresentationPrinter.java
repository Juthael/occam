package com.tregouet.occam.io.output.html.models;

import java.util.List;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.pages.SorterPagePrinter;

public class RepresentationPrinter {

	public static final RepresentationPrinter INSTANCE = new RepresentationPrinter();
	private static final String nL = System.lineSeparator();
	private static final String[] alinea = SorterPagePrinter.alinea;

	private RepresentationPrinter() {
	}

	public String print(List<IContextObject> context, IRepresentation representation, int a) {
		if (representation == null)
			return printNullRepresentation();
		return printNonNullRepresentation(context, representation, a);
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
				.apply(representation.getClassification().normalized().asGraph(), "classification_tree", false);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> CLASSIFICATION TREE </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, "Classification tree") + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printDescription(List<IContextObject> context, IRepresentation representation, int a) {
		return DescriptionPrinter.INSTANCE.print(context, representation, a, "description", true, true);
	}

	private static String printGeneratedFacts(IRepresentation representation, int a) {
		return FactPrinter.INSTANCE.print(representation, a);
	}

	private static String printNonNullRepresentation(List<IContextObject> context, IRepresentation representation,
			int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> <u> REPRESENTATION N." + Integer.toString(representation.iD()) + " </u> </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printDescription(context, representation, a + 2) + nL)
				.append(printAutomatonGraph(representation, a + 2) + nL)
				.append(printGeneratedFacts(representation, a + 2) + nL)
				.append(printClassificationTree(representation, a + 2) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printNullRepresentation() {
		StringBuilder sB = new StringBuilder();
		String alinea = "   ";
		String alineaa = alinea + alinea;
		String alineaaa = alineaa + alinea;
		String alineaaaa = alineaaa + alinea;
		sB.append(alineaa + "<section>" + nL)
				.append(alineaaa + "<header>" + nL)
					.append(alineaaaa + "<h2> No representation has been selected. </h2>" + nL)
				.append(alineaaa + "</header>" + nL)
			.append(alineaa + "</section>" + nL);
		return sB.toString();
	}

}
