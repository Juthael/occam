package com.tregouet.occam.io.output.html.problem_space_page;

import java.util.List;

import com.tregouet.occam.data.problem_space.ICategorizer;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.general.TablePrinter;

public class SimilarityMetricsPrinter {

	public static final SimilarityMetricsPrinter INSTANCE = new SimilarityMetricsPrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;

	private SimilarityMetricsPrinter() {
	}

	public String print(ICategorizer categorizer, int a) {
		String[] head = getHead(categorizer);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> <u> SIMILARITY METRICS </u> </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printSimilarityMatrix(categorizer, head, a + 2) + nL)
				.append(printSimilarityMatrixReferences(categorizer, head, a + 2) + nL)
				.append(printAsymmetricalSimilarityMatrix(categorizer, head, a + 2) + nL)
				.append(printDifferenceMatrix(categorizer, head, a + 2) + nL)
				.append(printTypicalityVector(categorizer, head, a + 2) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private String[] getHead(ICategorizer categorizer) {
		List<IContextObject> context = categorizer.getContext();
		String[] head = new String[context.size()];
		int idx = 0;
		for (IContextObject obj : context)
			head[idx++] = Integer.toString(obj.iD());
		return head;
	}

	private String printAsymmetricalSimilarityMatrix(ICategorizer categorizer, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(
				head, categorizer.getAsymmetricalSimilarityMatrix(), "Asymmetrical similarity matrix", a);
	}

	private String printDifferenceMatrix(ICategorizer categorizer, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, categorizer.getDifferenceMatrix(), "Difference matrix", a);
	}

	private String printSimilarityMatrix(ICategorizer categorizer, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, categorizer.getSimilarityMatrix(), "Similarity matrix", a);
	}

	private String printSimilarityMatrixReferences(ICategorizer categorizer, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, categorizer.getReferenceMatrix(), "Similarity matrix references", a);
	}

	private String printTypicalityVector(ICategorizer categorizer, String[] head, int a) {
		return TablePrinter.INSTANCE.print1DTable(head, categorizer.getTypicalityVector(), "Typicality", a);
	}

}
