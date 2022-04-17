package com.tregouet.occam.io.output.html.problem_space_page;

import java.io.IOException;

import com.tregouet.occam.alg.displayers.graph_visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.io.output.html.general.FigurePrinter;

public class ProblemSpacePrinter {
	
	public static final ProblemSpacePrinter INSTANCE = new ProblemSpacePrinter();
	public static final String nL = System.lineSeparator();
	
	private ProblemSpacePrinter() {
	}
	
	public String print(IProblemSpace problemSpace, String fileName, String alinea) throws IOException {
		String filePath = VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(problemSpace.asGraph(), alinea);
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		StringBuilder sB = new StringBuilder();
		sB.append(alinea + "<section>" + nL);
		sB.append(alineaa + "<header>" + nL);
		sB.append(alineaaa + "<h2> Problem Space Graph </h2>" + nL);
		sB.append(alineaa + "</header>" + nL);
		sB.append(alineaa + "<p>" + nL);
		sB.append(FigurePrinter.INSTANCE.displayFigure(filePath, alineaaa, "Problem space graph") + nL);
		sB.append(alineaa + "</p>" + nL);
		sB.append(alinea + "</section>" + nL);
		return sB.toString();
	}

}
