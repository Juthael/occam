package com.tregouet.occam.io.output.html.general;

import java.text.DecimalFormat;

import com.tregouet.occam.io.output.html.problem_space_page.ProblemSpacePagePrinter;

public class TablePrinter {

	public static final TablePrinter INSTANCE = new TablePrinter();
	private static final String nL = System.lineSeparator();
	private static final DecimalFormat DF = new DecimalFormat("#.####");
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;

	private TablePrinter() {
	}

	public String print1DTable(String[] head, double[] table, String caption, int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<table>" + nL)
				.append(alinea[a + 1] + "<caption> " + caption + "</caption>" + nL)
				.append(alinea[a + 1] + "<thead>" + nL)
					.append(alinea[a + 2] + "<tr>" + nL);
						for (String element : head)
							sB.append(alinea[a + 3] + "<th> "+ element + " </th>" + nL);
					sB.append(alinea[a + 2] + "</tr>" + nL)
				.append(alinea[a + 1] + "</thead>" + nL)
				.append(alinea[a + 1] + "<tbody>" + nL)
					.append(alinea[a + 2] + "<tr>" + nL);
						for (int i = 0; i < head.length; i++)
							sB.append(alinea[a + 3] + "<td>" + round(table[i]) + "</td>" + nL);
					sB.append(alinea[a + 2] + "</tr>" + nL)
				.append(alinea[a + 1] + "</tbody>" + nL);
		sB.append(alinea[a] + "</table>" + nL);
		return sB.toString();
	}

	public String print2DSquareTable(String[] head, double[][] table, String caption, int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<table>" + nL)
			.append(alinea[a + 1] + "<caption> " + caption + "</caption>" + nL)
			.append(alinea[a + 1] + "<thead>" + nL)
				.append(alinea[a + 2] + "<tr>" + nL);
					for (int i = 0; i <= head.length; i++) {
						if (i == 0)
							sB.append(alinea[a + 3] + "<th> </th> " + nL);
						else sB.append(alinea[a + 3] + "<th> " + head[i - 1] + " </th>" + nL);
					}
				sB.append(alinea[a + 2] + "</tr>" + nL)
			.append(alinea[a + 1] + "</thead>" + nL)
			.append(alinea[a + 1] + "<tbody>" + nL);
				for (int j = 1; j <= head.length; j++) {
					sB.append(alinea[a + 2] + "<tr>" + nL);
						for (int i = 0; i <= head.length; i++) {
							if (i == 0)
								sB.append(alinea[a + 3] + "<th> " + head[j - 1] + " </th>" + nL);
							else sB.append(alinea[a + 3] + "<td>" + round(table[i - 1][j - 1]) + "</td>" + nL);
						}
					sB.append(alinea[a + 2] + "</tr>" + nL);
				}
			sB.append(alinea[a + 1] + "</tbody>" + nL)
		.append(alinea[a] + "</table>" + nL);
		return sB.toString();
	}

	public String printStringTableWithOptionalSubHead(String[] head, String[] optionalSubHead, String[] body,
			String caption, int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<table>" + nL)
				.append(alinea[a + 1] + "<caption> " + caption + "</caption>" + nL)
				.append(alinea[a + 1] + "<thead>" + nL)
					.append(alinea[a + 2] + "<tr>" + nL);
					for (String element : head) {
						sB.append(alinea[a + 3] + "<th> " + element + " </th>" + nL);
					}
					sB.append(alinea[a + 2] + "</tr>" + nL)
				.append(alinea[a + 1] + "</thead>" + nL)
				.append(alinea[a + 1] + "<tbody>" + nL);
					if (optionalSubHead != null) {
						sB.append(alinea[a + 2] + "<tr>" + nL);
						for (int i = 0; i < head.length; i++)
							sB.append(alinea[a + 3] + "<td><b>" + optionalSubHead[i] + "</b></td>" + nL);
						sB.append(alinea[a + 2] + "</tr>" + nL);
					}
					sB.append(alinea[a + 2] + "<tr>" + nL);
						for (int i = 0; i < head.length; i++)
							sB.append(alinea[a + 3] + "<td>" + nL + body[i] + nL + "</td>" + nL);
					sB.append(alinea[a + 2] + "</tr>" + nL)
				.append(alinea[a + 1] + "</tbody>" + nL)
		.append(alinea[a] + "</table>" + nL);
		return sB.toString();
	}

	private static String round(double nb) {
		return DF.format(nb).toString();
	}

}
