package com.tregouet.occam.io.output.html.general;

import java.text.DecimalFormat;
import java.util.Arrays;

public class TablePrinter {

	public static final TablePrinter INSTANCE = new TablePrinter();
	public static final String NL = System.lineSeparator();
	private static final DecimalFormat DF = new DecimalFormat("#.####");

	private TablePrinter() {
	}

	private static String round(double nb) {
		return DF.format(nb).toString();
	}

	public String print1DTable(String[] head, double[] table, String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + NL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + NL);
		sB.append(alineaa + "<thead>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (String element : head) {
			sB.append(alineaaaa + "<th> ");
			sB.append(element);
			sB.append(" </th>" + NL);
		}
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</thead>" + NL);
		sB.append(alineaa + "<tbody>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		//HERE
		System.out.println(Arrays.toString(head));
		System.out.println(Arrays.toString(table));
		//HERE
		for (int i = 0; i < head.length; i++)
			sB.append(alineaaaa + "<td>" + round(table[i]) + "</td>" + NL);
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</tbody>" + NL);
		sB.append(alinea + "</table>" + NL);
		return sB.toString();
	}

	public String print2DSquareTable(String[] head, double[][] table, String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + NL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + NL);
		sB.append(alineaa + "<thead>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (int i = 0; i <= head.length; i++) {
			if (i == 0) {
				sB.append(alineaaaa + "<th> </th> " + NL);
			}
			else {
				sB.append(alineaaaa + "<th> ");
				sB.append(head[i - 1]);
				sB.append(" </th>" + NL);	
			}
		}
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</thead>" + NL);
		sB.append(alineaa + "<tbody>" + NL);
		for (int j = 1; j <= head.length; j++) {
			sB.append(alineaaa + "<tr>" + NL);
			for (int i = 0; i <= head.length; i++) {
				if (i == 0)
					sB.append(alineaaaa + "<th> " + head[j - 1] + " </th>" + NL);
				else {
					//HERE
					try {
						sB.append(alineaaaa + "<td>" + round(table[i - 1][j - 1]) + "</td>" + NL);
					}
					catch (Exception e) {
						System.out.println(head.toString());
					}
					//HERE
				}
					
			}
			sB.append(alineaaa + "</tr>" + NL);
		}
		sB.append(alineaa + "</tbody>" + NL);
		sB.append(alinea + "</table>" + NL);
		return sB.toString();
	}

	public String printStringTableWithOptionalSubHead(String[] head, String[] optionalSubHead, String[] body,
			String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + NL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + NL);
		sB.append(alineaa + "<thead>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (String element : head) {
			sB.append(alineaaaa + "<th> ");
			sB.append(element);
			sB.append(" </th>" + NL);
		}
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</thead>" + NL);
		sB.append(alineaa + "<tbody>" + NL);
		if (optionalSubHead != null) {
			sB.append(alineaaa + "<tr>" + NL);
			for (int i = 0; i < head.length; i++)
				sB.append(alineaaaa + "<td><b>" + optionalSubHead[i] + "</b></td>" + NL);
			sB.append(alineaaa + "</tr>" + NL);
		}
		for (int i = 0; i < head.length; i++)
			sB.append(alineaaaa + "<td>" + NL + body[i] + NL + "</td>" + NL);
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</tbody>" + NL);
		sB.append(alinea + "</table>" + NL);
		return sB.toString();
	}

}
