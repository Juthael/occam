package com.tregouet.occam.io.output.html.models;

import java.io.IOException;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.modules.sorting.ISorter;
import com.tregouet.occam.io.output.html.pages.CategorizerPagePrinter;

public class ProblemSpacePrinter {

	public static final ProblemSpacePrinter INSTANCE = new ProblemSpacePrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = CategorizerPagePrinter.alinea;

	private ProblemSpacePrinter() {
	}

	public String print(ISorter sorter, String fileName, int a) throws IOException {
		String filePath = VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(sorter.getProblemSpaceGraph(),
				fileName);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<p>" + nL)
					.append(FigurePrinter.INSTANCE.displayFigure(filePath, a + 2, "Problem space graph") + nL)
				.append(alinea[a + 1] + "</p>" + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

}
