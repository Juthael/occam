package com.tregouet.occam.io.output.html.general;

import java.text.DecimalFormat;

public class TablePrinter {
	
	public static final TablePrinter INSTANCE = new TablePrinter();
	public static final String nL = System.lineSeparator();
	private static final DecimalFormat df = new DecimalFormat("#.####");
	
	private TablePrinter() {
	}
	
	public String print(String[] head, double[][] table, String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + nL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + nL);
		sB.append(alineaa + "<thead>" + nL);
		sB.append(alineaaa + "<tr>" + nL);
		for (int i = 0 ; i <= head.length ; i++) {
			sB.append(alineaaaa + "<th> ");
			sB.append(head[i]);
			sB.append(" </th>" + nL);
		}
		sB.append(alineaaa + "</tr>" + nL);
		sB.append(alineaa + "</thead>" + nL);
		sB.append(alineaa + "<tbody>" + nL);
		for (int j = 1 ; j <= head.length ; j++) {
			sB.append(alineaaa + "<tr>" + nL);
			for (int i = 0 ; i <= head.length ; i++) {
				if (i == 0)
					sB.append(alineaaaa + "<th> " + head[j - 1] + " </th>" + nL);
				else sB.append(alineaaaa + "<td>" + round(table[i - 1][j - 1]) + "</td>" + nL);
			}
			sB.append(alineaaa + "</tr>" + nL);
		}
		sB.append(alineaa + "</tbody>" + nL);
		sB.append(alinea + "</table>" + nL);
		return sB.toString();
	}	
	
	public String print(String[] head, double[] table, String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + nL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + nL);
		sB.append(alineaa + "<thead>" + nL);
		sB.append(alineaaa + "<tr>" + nL);
		for (int i = 0 ; i < head.length ; i++) {
			sB.append(alineaaaa + "<th> ");
			sB.append(head[i]);
			sB.append(" </th>" + nL);
		}
		sB.append(alineaaa + "</tr>" + nL);
		sB.append(alineaa + "</thead>" + nL);
		sB.append(alineaa + "<tbody>" + nL);
		sB.append(alineaaa + "<tr>" + nL);
		for (int i = 0 ; i < head.length ; i++)
			sB.append(alineaaaa + "<td>" + round(table[i]) + "</td>" + nL);
		sB.append(alineaaa + "</tr>" + nL);
		sB.append(alineaa + "</tbody>" + nL);
		sB.append(alinea + "</table>" + nL);
		return sB.toString();
	}		
	
	private static String round(double nb) {
		return df.format(nb).toString();
	}
	
	public String print(String[] head, String[] optionalSubHead, String[] body, 
			String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + nL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + nL);
		sB.append(alineaa + "<thead>" + nL);
		sB.append(alineaaa + "<tr>" + nL);
		for (int i = 0 ; i < head.length ; i++) {
			sB.append(alineaaaa + "<th> ");
			sB.append(head[i]);
			sB.append(" </th>" + nL);
		}
		sB.append(alineaaa + "</tr>" + nL);
		sB.append(alineaa + "</thead>" + nL);	
		sB.append(alineaa + "<tbody>" + nL);
		if (optionalSubHead != null) {
			sB.append(alineaaa + "<tr>" + nL);
			for (int i = 0 ; i < head.length ; i++)
				sB.append(alineaaaa + "<td><b>" + optionalSubHead[i] + "</b></td>" + nL);
			sB.append(alineaaa + "</tr>" + nL);
		}
		for (int i = 0 ; i < head.length ; i++)
			sB.append(alineaaaa + "<td>" + nL + body[i] + nL + "</td>" + nL);
		sB.append(alineaaa + "</tr>" + nL);
		sB.append(alineaa + "</tbody>" + nL);
		sB.append(alinea + "</table>" + nL);
		return sB.toString();		
	}

}
