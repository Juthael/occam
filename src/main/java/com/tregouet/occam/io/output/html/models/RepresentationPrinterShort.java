package com.tregouet.occam.io.output.html.models;

import java.util.List;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.pages.CategorizerPagePrinter;

public class RepresentationPrinterShort {

	public static final RepresentationPrinterShort INSTANCE = new RepresentationPrinterShort();
	private static final String nL = System.lineSeparator();
	private static final String[] alinea = CategorizerPagePrinter.alinea;

	private RepresentationPrinterShort() {
	}

	public String print(List<IContextObject> context, IRepresentation representation, int a, String iD) {
		if (representation == null)
			return printNullRepresentation();
		return printNonNullRepresentation(context, representation, a, iD);
	}
	
	private static String printSummarizedWeightOptDescription(IRepresentation representation, int a, String iD) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE
				.getSummarizedWeightOptDescriptionViz()
				.setUp(representation.getClassification().mapConceptID2ExtentIDs())
				.apply(representation.getDescription(), iD);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> " + iD + " </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, iD)
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}
	
	private static String printSummarizedPropOptDescription(IRepresentation representation, int a, String iD) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE
				.getSummarizedPropOptDescriptionViz()
				.setUp(representation.getClassification().mapConceptID2ExtentIDs())
				.apply(representation.getDescription(), iD);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> " + iD + " </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, iD)
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}	

	private static String printDescription(IRepresentation representation, int a, String iD) {
		String figureFullPath = VisualizersAbstractFactory.INSTANCE
				.getDescriptionViz()
				.setUp(representation.getClassification().mapConceptID2ExtentIDs())
				.apply(representation.getDescription(), iD);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> " + iD + " </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(FigurePrinter.INSTANCE.displayFigure(figureFullPath, a + 1, iD)
						+ nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printNonNullRepresentation(List<IContextObject> context, IRepresentation representation,
			int a, String iD) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> <u> REPRESENTATION N." + Integer.toString(representation.iD()) + " </u> </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printSummarizedWeightOptDescription(representation, a + 2, iD + "_summarized_weight_opt") + nL)
				.append(printSummarizedPropOptDescription(representation, a + 2, iD + "_summarized_prop_opt") + nL)
				.append(printDescription(representation, a + 2, iD + "_exhaustive") + nL)
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
