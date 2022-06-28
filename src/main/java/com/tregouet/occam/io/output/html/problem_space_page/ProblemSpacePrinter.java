package com.tregouet.occam.io.output.html.problem_space_page;

import java.io.IOException;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.io.output.html.general.FigurePrinter;

public class ProblemSpacePrinter {

	public static final ProblemSpacePrinter INSTANCE = new ProblemSpacePrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;

	private ProblemSpacePrinter() {
	}

	public String print(IProblemSpace problemSpace, String fileName, int a) throws IOException {
		String filePath = VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(problemSpace.getProblemSpaceGraph(),
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
