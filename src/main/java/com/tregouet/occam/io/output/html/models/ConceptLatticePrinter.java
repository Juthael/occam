package com.tregouet.occam.io.output.html.models;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.modules.categorization.ICategorizer;
import com.tregouet.occam.io.output.html.pages.CategorizerPagePrinter;

public class ConceptLatticePrinter {

	public static final ConceptLatticePrinter INSTANCE = new ConceptLatticePrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = CategorizerPagePrinter.alinea;

	private ConceptLatticePrinter() {
	}

	public String print(ICategorizer categorizer, String fileName, int a) {
		String filePath = VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(categorizer.getLatticeOfConcepts(),
				fileName, false);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<p>" + nL)
					.append(FigurePrinter.INSTANCE.displayFigure(filePath, a + 2, "Concept lattice") + nL)
				.append(alinea[a + 1] + "</p>" + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

}
