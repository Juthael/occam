package com.tregouet.occam.io.output.html.general;

public class FigurePrinter {
	
	public static final FigurePrinter INSTANCE = new FigurePrinter();
	public static final String NL = System.lineSeparator();
	
	private FigurePrinter() {
	}
	
	public String displayFigure(String fileName, String folderPath, String alinea, String caption) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		sB.append(alinea + "<figure>" + NL);
		sB.append(alineaa + "<img src = \"" + "file:///" + folderPath + "\\" + fileName + "\" alt = " + fileName + ">" 
			+ NL);
		sB.append(alineaa + "<figcaption>" + caption + "</figcaption>" + NL);
		sB.append(alinea + "</figure>" + NL);
		return sB.toString();
	}
	
	public String displayFigure(String fullPath, String alinea, String caption) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		sB.append(alinea + "<figure>" + NL);
		sB.append(alineaa + "<img src = \"" + "file:///" + fullPath + "\" alt = " 
				+ fullPath.substring(fullPath.lastIndexOf("\\") + 1) + ">" + NL);
		sB.append(alineaa + "<figcaption>" + caption + "</figcaption>" + NL);
		sB.append(alinea + "</figure>" + NL);
		return sB.toString();
	}	

}
