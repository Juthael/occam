package com.tregouet.occam.io.output.html.problem_space_page;

import java.util.List;

import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.TablePrinter;

public class SimilarityMetricsPrinter {

	public static final SimilarityMetricsPrinter INSTANCE = new SimilarityMetricsPrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;

	private SimilarityMetricsPrinter() {
	}

	public String print(IProblemSpace problemSpace, int a) {
		String[] head = getHead(problemSpace);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> <u> SIMILARITY METRICS </u> </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printSimilarityMatrix(problemSpace, head, a + 2) + nL)
				.append(printSimilarityMatrixReferences(problemSpace, head, a + 2) + nL)
				.append(printAsymmetricalSimilarityMatrix(problemSpace, head, a + 2) + nL)
				.append(printDifferenceMatrix(problemSpace, head, a + 2) + nL)
				.append(printTypicalityVector(problemSpace, head, a + 2) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private String[] getHead(IProblemSpace problemSpace) {
		List<IContextObject> context = problemSpace.getContext();
		String[] head = new String[context.size()];
		int idx = 0;
		for (IContextObject obj : context)
			head[idx++] = Integer.toString(obj.iD());
		return head;
	}

	private String printAsymmetricalSimilarityMatrix(IProblemSpace problemSpace, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(
				head, problemSpace.getAsymmetricalSimilarityMatrix(), "Asymmetrical similarity matrix", a);
	}

	private String printDifferenceMatrix(IProblemSpace problemSpace, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, problemSpace.getDifferenceMatrix(), "Difference matrix", a);
	}

	private String printSimilarityMatrix(IProblemSpace problemSpace, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, problemSpace.getSimilarityMatrix(), "Similarity matrix", a);
	}

	private String printSimilarityMatrixReferences(IProblemSpace problemSpace, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, problemSpace.getReferenceMatrix(), "Similarity matrix references", a);
	}

	private String printTypicalityVector(IProblemSpace problemSpace, String[] head, int a) {
		return TablePrinter.INSTANCE.print1DTable(head, problemSpace.getTypicalityVector(), "Typicality", a);
	}

}
