package com.tregouet.occam.io.output.html.models;

import java.util.List;

import com.tregouet.occam.data.modules.comparison.IComparator;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.io.output.html.pages.SorterPagePrinter;

public class SimilarityMetricsPrinter {

	public static final SimilarityMetricsPrinter INSTANCE = new SimilarityMetricsPrinter();
	public static final String nL = System.lineSeparator();
	private static final String[] alinea = SorterPagePrinter.alinea;

	private SimilarityMetricsPrinter() {
	}

	public String print(IComparator simAssessor, int a) {
		String[] head = getHead(simAssessor);
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> <u> SIMILARITY METRICS </u> </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printSimilarityMatrix(simAssessor, head, a + 2) + nL)
				.append(printAsymmetricalSimilarityMatrix(simAssessor, head, a + 2) + nL)
				.append(printDifferenceMatrix(simAssessor, head, a + 2) + nL)
				.append(printTypicalityVector(simAssessor, head, a + 2) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private String[] getHead(IComparator simAssessor) {
		List<IContextObject> context = simAssessor.getContext();
		String[] head = new String[context.size()];
		int idx = 0;
		for (IContextObject obj : context)
			head[idx++] = Integer.toString(obj.iD());
		return head;
	}

	private String printAsymmetricalSimilarityMatrix(IComparator simAssessor, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(
				head, simAssessor.getAsymmetricalSimilarityStringMatrix(), "Asymmetrical similarity matrix", a);
	}

	private String printDifferenceMatrix(IComparator simAssessor, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, simAssessor.getDifferenceStringMatrix(), "Difference matrix", a);
	}

	private String printSimilarityMatrix(IComparator simAssessor, String[] head, int a) {
		return TablePrinter.INSTANCE.print2DSquareTable(head, simAssessor.getSimilarityStringMatrix(), "Similarity matrix", a);
	}

	private String printTypicalityVector(IComparator simAssessor, String[] head, int a) {
		return TablePrinter.INSTANCE.print1DTable(head, simAssessor.getTypicalityStringVector(), "Typicality", a);
	}

}
