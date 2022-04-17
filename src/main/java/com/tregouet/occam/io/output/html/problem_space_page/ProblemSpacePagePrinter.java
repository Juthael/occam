package com.tregouet.occam.io.output.html.problem_space_page;

import java.util.List;

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
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + NL);
		sB.append("<hr>" + NL);
		sB.append(ContextPrinter.INSTANCE.print(objects, new String()) + NL);
		sB.append("<hr>" + NL);
		sB.append(FigurePrinter.INSTANCE.displayFigure(Visualizer., fileName, fileName))
	}
	

}
