package com.tregouet.occam.io.output.html.problem_space_page;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.ICategorizer;
import com.tregouet.occam.io.output.html.general.FigurePrinter;

public class ConceptLatticePrinter {

	public static final ConceptLatticePrinter INSTANCE = new ConceptLatticePrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;

	private ConceptLatticePrinter() {
	}

	public String print(ICategorizer categorizer, String fileName, int a) {
		String filePath = VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(categorizer.getLatticeOfConcepts(),
				fileName);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<p>" + nL)
					.append(FigurePrinter.INSTANCE.displayFigure(filePath, a + 2, "Concept lattice") + nL)
				.append(alinea[a + 1] + "</p>" + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

}
