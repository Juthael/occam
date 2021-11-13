package com.tregouet.occam.io.output.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.input.util.exceptions.FileReaderException;
import com.tregouet.occam.io.output.IOntologicalCommitment;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;
import com.tregouet.occam.transition_function.impl.CatStructureAwareTFSupplier;
import com.tregouet.tree_finder.error.InvalidInputException;

public class OntologicalCommitment implements IOntologicalCommitment {

	private static final String NL = System.lineSeparator();
	private static final Path headPath = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io", 
			"output", "html", "head.txt");
	private static final PropertyWeighingStrategy PROP_WHEIGHING_STRATEGY = 
			PropertyWeighingStrategy.INFORMATIVITY_DIAGNOSTIVITY;
	private static final SimilarityCalculationStrategy SIM_CALCULATION_STRATEGY = 
			SimilarityCalculationStrategy.RATIO_MODEL;
	private static final Escaper escaper = HtmlEscapers.htmlEscaper();
	private static final DecimalFormat df = new DecimalFormat("#.####");
	private List<IContextObject> context = null;
	private ICategories categories = null;
	private ICatStructureAwareTFSupplier catStructureAwareTFSupplier = null;
	private IRepresentedCatTree representedCatTree = null;
	private int catTreeIdx = 0;
	private Iterator<ITransitionFunction> iteOverTF = null;
	private ITransitionFunction currentTransFunc = null;
	private int transFuncIdx = 0;
	
	public OntologicalCommitment(String folderPath) {
		Visualizer.setLocation(folderPath);
	}

	@Override
	public boolean whatIsThere(Path contextPath) {
		try {
			context = GenericFileReader.getContextObjects(contextPath);
		} catch (FileReaderException e) {
			return false;
		}
		categories = new Categories(context);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
				new DirectedAcyclicGraph<>(null, null, false);
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		try {
			catStructureAwareTFSupplier = new CatStructureAwareTFSupplier(categories, constructs, 
					PROP_WHEIGHING_STRATEGY, SIM_CALCULATION_STRATEGY);
		} catch (InvalidInputException e) {
			return false;
		}
		representedCatTree = catStructureAwareTFSupplier.next();
		return true;
	}

	@Override
	public double getCoherenceScore() {
		return currentTransFunc.getCoherenceScore();
	}

	@Override
	public String generateInputHTMLTranslation(String alinea) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea + escaper.escape("<hr>") + NL);
		sB.append(alinea + escaper.escape("<h2> Input context : </h2> <br>") + NL);
		int objIdx = 1;
		String largerAlinea = alinea + "   ";
		for (IContextObject obj : context) {
			sB.append(largerAlinea + escaper.escape("<h3>Object " + Integer.toString(objIdx++) + " : </h3>") + NL);
			String stillLargerAlinea = largerAlinea + "   ";
			sB.append(stillLargerAlinea + escaper.escape("<p>") + NL);
			for (IConstruct att : obj.getConstructs()) {
				sB.append(stillLargerAlinea + escaper.escape(att.toString() + "<br>") + NL);
			}
			sB.append(largerAlinea + escaper.escape("</p> <br>") + NL);
		}
		return sB.toString();
	}

	@Override
	public void generateCategoryLatticeGraph() throws IOException {
		Visualizer.visualizeCategoryGraph(categories.getCategoryLattice(), "category_lattice");
	}
	
	@Override
	public void generateCategoryStructureGraph() throws IOException {
		Visualizer.visualizeCategoryGraph(representedCatTree.getCategoryTree(), "category_tree");;
	}

	@Override
	public void generateTransitionFunctionGraph() throws IOException {
		Visualizer.visualizeTransitionFunction(currentTransFunc, "transition_function", 
				TransitionFunctionGraphType.FINITE_AUTOMATON);
	}

	@Override
	public String generateSimilarityMatrix(String alinea) {
		double[][] similarityMatrix = currentTransFunc.getSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(displayTable(similarityMatrix, "Similarity matrix", alinea));
		sB.append(escaper.escape("<br>") + NL);
		return sB.toString();
	}

	@Override
	public String generateAsymmetricalSimilarityMatrix(String alinea) {
		double[][] asymmetricalSimilarityMatrix = currentTransFunc.getAsymmetricalSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(displayTable(asymmetricalSimilarityMatrix, "Similarity matrix", alinea));
		sB.append(escaper.escape("<br>") + NL);
		return sB.toString();
	}

	@Override
	public String generateCategoricalCoherenceArray(String pathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNextCategoricalStructure() {
		return (catStructureAwareTFSupplier != null && catStructureAwareTFSupplier.hasNext());
	}

	@Override
	public boolean hasNextTransitionFunctionOverCurrentCategoricalStructure() {
		return (iteOverTF != null && iteOverTF.hasNext());
	}

	@Override
	public void nextCategoricalStructure() {
		representedCatTree = catStructureAwareTFSupplier.next();
		iteOverTF = representedCatTree.getIteratorOverTransitionFunctions();
		catTreeIdx++;
	}

	@Override
	public void nextTransitionFunctionOverCurrentCategoricalStructure() {
		currentTransFunc = iteOverTF.next();
	}

	@Override
	public void generateHTML() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCategoryTreeIndex() {
		return catTreeIdx;
	}

	@Override
	public int getTransitionFunctionIndex() {
		return transFuncIdx;
	}
	
	private String displayTable(double[][] table, String caption, String alinea) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + escaper.escape("<table>") + NL);
		sB.append(alinea + escaper.escape("<caption> " + caption + "</caption>") + NL);
		sB.append(alineaa + escaper.escape("<thead>") + NL);
		sB.append(alineaaa + escaper.escape("<tr>") + NL);
		for (int i = 0 ; i <= context.size() ; i++) {
			sB.append(alineaaaa + escaper.escape("<th>"));
			if (i > 0)
				sB.append("obj" + Integer.toString(i));
			sB.append(escaper.escape("</th>") + NL);
		}
		sB.append(alineaaa + escaper.escape("</tr>") + NL);
		sB.append(alineaa + escaper.escape("</thead>") + NL);
		sB.append(alineaa + escaper.escape("<tbody>") + NL);
		for (int j = 1 ; j <= context.size() ; j++) {
			sB.append(alineaaa + escaper.escape("<tr>") + NL);
			for (int i = 0 ; i <= context.size() ; i++) {
				if (i == 0)
					sB.append(alineaaaa + escaper.escape("<th>obj" + Integer.toString(j) + "</th>") + NL);
				else sB.append(alineaaaa + escaper.escape("<td>" + round(table[i - 1][j - 1]) + "</td>") + NL);
					
			}
			sB.append(alineaaa + escaper.escape("</tr>") + NL);
		}
		sB.append(alineaa + escaper.escape("</tbody>") + NL);
		sB.append(alinea + escaper.escape("</table>") + NL);
		return sB.toString();
	}
	
	private String round(double nb) {
		return df.format(nb).toString();
	}

}
