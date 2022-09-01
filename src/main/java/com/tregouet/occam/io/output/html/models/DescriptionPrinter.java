package com.tregouet.occam.io.output.html.models;

import java.util.List;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionFormat;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.pages.SorterPagePrinter;

public class DescriptionPrinter {

	public static final DescriptionPrinter INSTANCE = new DescriptionPrinter();
	private static final String nL = System.lineSeparator();
	private static final String[] alinea = SorterPagePrinter.alinea;

	private DescriptionPrinter() {
	}

	public String print(List<IContextObject> context, IRepresentation representation, int a, String iD, boolean optimal, boolean exhaustive) {
		if (representation == null)
			return printNullDescription();
		return printNonNullDescription(context, representation, a, iD, optimal, exhaustive);
	}

	private static String printExhaustiveDescription(IRepresentation representation, int a, String iD) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE
				.getDescriptionViz()
				.setUp(representation.getClassification().mapConceptID2ExtentIDs(), DescriptionFormat.EXHAUSTIVE)
				.apply(representation.getDescription(), iD);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, iD)
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printNonNullDescription(List<IContextObject> context, IRepresentation representation,
			int a, String iD, boolean optimal, boolean exhaustive) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
			.append(alinea[a + 1] + "<header>" + nL)
			.append(alinea[a + 2] + "<h3> DESCRIPTION </h3>" + nL)
			.append(alinea[a + 1] + "</header>" + nL);
		if (optimal)
			sB.append(printOptDescription(representation, a + 1, iD + "_opt") + nL);
		if (exhaustive)
			sB.append(printExhaustiveDescription(representation, a + 1, iD + "_exhaustive") + nL);
		sB.append(alinea[a] + "</section>" + nL);			
		return sB.toString();
	}

	private static String printNullDescription() {
		StringBuilder sB = new StringBuilder();
		String alinea = "   ";
		String alineaa = alinea + alinea;
		String alineaaa = alineaa + alinea;
		String alineaaaa = alineaaa + alinea;
		sB.append(alineaa + "<section>" + nL)
				.append(alineaaa + "<header>" + nL)
					.append(alineaaaa + "<h2> No description has been selected. </h2>" + nL)
				.append(alineaaa + "</header>" + nL)
			.append(alineaa + "</section>" + nL);
		return sB.toString();
	}

	private static String printOptDescription(IRepresentation representation, int a, String iD) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE
				.getDescriptionViz()
				.setUp(representation.getClassification().mapConceptID2ExtentIDs(), DescriptionFormat.OPTIMAL)
				.apply(representation.getDescription(), iD);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, iD)
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

}
