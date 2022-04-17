package com.tregouet.occam.io.output.html.problem_space_page;

import java.util.List;

import com.tregouet.occam.alg.displayers.graph_visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.FigurePrinter;
import com.tregouet.occam.io.output.html.general.HeaderPrinter;

public class ProblemSpacePagePrinter {
	
	public static final ProblemSpacePagePrinter INSTANCE = new ProblemSpacePagePrinter();
	public static final String NL = System.lineSeparator();
	
	private ProblemSpacePagePrinter() {
	}
	
	public String print(List<IContextObject> objects, IProblemSpace problemSpace, String fileName) {
		String alinea = "   ";
		String alineaa = alinea + alinea;
		String alineaaa = alineaa + alinea;
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + NL)
			.append(alinea + "<hr>" + NL)
			.append(alinea + "<section>" + NL)
			.append(alineaa + "<header>" + NL)
			.append(alineaaa + "<h2> CONTEXT </h2>" + NL)
			.append(alineaa + "</header>" + NL)
			.append(alineaa + "<p>" + NL)
			.append(ContextPrinter.INSTANCE.print(objects, alineaaa) + NL)
			.append(alineaa + "</p>" + NL)
			.append(alinea + "</section>")
			.append(alinea + "<hr>" + NL)
			.append(alinea + "<section>" + NL)
			.append(alineaa + "<header>" + NL)
			.append(alineaaa + "<h2> PROBLEM SPACE </h2>" + NL)
			.append(alineaa + "</header>" + NL)
			.append(alineaa + "<p>" + NL)
			.append(ProblemSpacePrinter.INSTANCE.print(problemSpace, "problem_space", alineaaa))
			.append(alineaa + "</p>" + NL)
			.append(alinea + "</section>" + NL)
			//représentations par ordre croissant
		
		return sB.toString();
	}
	

}
