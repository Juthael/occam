package com.tregouet.occam.io.output.html.models;

import com.tregouet.occam.io.output.html.pages.SorterPagePrinter;

public class FigurePrinter {

	public static final FigurePrinter INSTANCE = new FigurePrinter();
	public static final String NL = System.lineSeparator();
	private static final String[] alinea = SorterPagePrinter.alinea;

	private FigurePrinter() {
	}

	public String displayFigure(String fullPath, int a, String caption) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<figure>" + NL)
				.append(alinea[a + 1] + "<img src = \"" + "file:///" + fullPath + "\" alt = "
					+ fullPath.substring(fullPath.lastIndexOf("\\") + 1) + ">" + NL)
				.append(alinea[a + 1] + "<figcaption>" + caption + "</figcaption>" + NL)
			.append(alinea[a] + "</figure>" + NL);
		return sB.toString();
	}

	public String displayFigure(String fileName, String folderPath, int a, String caption) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<figure>" + NL)
				.append(alinea[a + 1] + "<img src = \"" + "file:///" + folderPath + "\\" + fileName + "\" alt = " + fileName + ">"
				+ NL)
				.append(alinea[a + 1] + "<figcaption>" + caption + "</figcaption>" + NL)
			.append(alinea[a] + "</figure>" + NL);
		return sB.toString();
	}

}
