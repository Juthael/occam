package com.tregouet.occam.io.output.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
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
	private final String folderPath;
	private List<IContextObject> context = null;
	private ICategories categories = null;
	private ICatStructureAwareTFSupplier catStructureAwareTFSupplier = null;
	private IRepresentedCatTree representedCatTree = null;
	private int catTreeIdx = 0;
	private Iterator<ITransitionFunction> iteOverTF = null;
	private ITransitionFunction currentTransFunc = null;
	private int transFuncIdx = 0;
	
	public OntologicalCommitment(String folderPath) {
		this.folderPath = folderPath;
		Visualizer.setLocation(folderPath);
	}

	@Override
	public boolean whatIsThere(Path contextPath) throws IOException {
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
		generateCategoryLatticeGraph();
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
		sB.append(alinea + escaper.escape("<h3> Input context : </h3> <br>") + NL);
		int objIdx = 1;
		String largerAlinea = alinea + "   ";
		for (IContextObject obj : context) {
			sB.append(largerAlinea + escaper.escape("<h4>Object " + Integer.toString(objIdx++) + " : </h4>") + NL);
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
	public void generateCategoryTreeGraph() throws IOException {
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
	public String generateCategoricalCoherenceArray(String alinea) {
		Map<Integer, Double> catIDToCoherence = currentTransFunc.getCategoricalCoherenceMap();
		List<ICategory> topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(currentTransFunc.getCategoryTree()).forEachRemaining(topologicalOrder::add);
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + escaper.escape("<table>") + NL);
		sB.append(alinea + escaper.escape("<caption> " + "Category coherence" + "</caption>") + NL);
		sB.append(alineaa + escaper.escape("<thead>") + NL);
		sB.append(alineaaa + escaper.escape("<tr>") + NL);
		for (ICategory cat : topologicalOrder)
			sB.append(alineaaaa + escaper.escape("<th>" + Integer.toString(cat.getID()) + "</th>"));
		sB.append(alineaaa + escaper.escape("</tr>") + NL);
		sB.append(alineaa + escaper.escape("</thead>") + NL);
		sB.append(alineaa + escaper.escape("<tbody>") + NL);
		sB.append(alineaaa + escaper.escape("<tr>") + NL);
		for (ICategory cat : topologicalOrder)
			sB.append(alineaaaa + escaper.escape("<td>" + round(catIDToCoherence.get(cat.getID())) + "</td>"));
		sB.append(alineaaa + escaper.escape("</tr>") + NL);
		sB.append(alineaa + escaper.escape("</tbody>") + NL);
		sB.append(alinea + escaper.escape("</table><br>") + NL);
		return sB.toString();
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
	public void nextCategoryTree() throws IOException {
		representedCatTree = catStructureAwareTFSupplier.next();
		catTreeIdx++;
		generateCategoryTreeGraph();
		iteOverTF = representedCatTree.getIteratorOverTransitionFunctions();
		currentTransFunc = iteOverTF.next();
		generateTransitionFunctionGraph();
	}

	@Override
	public void nextTransitionFunctionOverCurrentCategoricalStructure() throws IOException {
		currentTransFunc = iteOverTF.next();
		generateTransitionFunctionGraph();
	}

	@Override
	public void generateHTML() throws IOException {
		String htmlPage;
		String alinea = "      ";
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		StringBuilder sB = new StringBuilder();
		BufferedReader reader;	
		reader = Files.newBufferedReader(headPath);
		String line = reader.readLine();
		while (line != null) {
			sB.append(line + NL);
			line = reader.readLine();
		}
		sB.append(escaper.escape("<hr>"));
		sB.append(alinea + escaper.escape("<h2>Representation : </h2>") + NL);
		sB.append(alineaa + escaper.escape("<p>") + NL);
		sB.append(alineaaa + escaper.escape("<b>Score : " + round(currentTransFunc.getCoherenceScore()) + "</b>") + NL);
		sB.append(alineaa + escaper.escape("</p>") + NL);
		sB.append(alineaa + escaper.escape("<h3>Category tree : </h3>") + NL);
		sB.append(alineaaa + escaper.escape("<p>") + NL);
		sB.append(displayFigure("category_tree", alineaaaa, "Category tree"));
		sB.append(alineaaa + escaper.escape("</p>") + NL);
		sB.append(alineaa + escaper.escape("<h3>Transition function : </h3>") + NL);
		sB.append(alineaaa + escaper.escape("<p>") + NL);
		sB.append(displayFigure("transition_function", alineaaaa, "Transition function"));
		sB.append(alineaaa + escaper.escape("</p>") + NL);
		sB.append(alineaa + escaper.escape("<h3>Similarity matrices : </h3>") + NL);
		sB.append(alineaaa + escaper.escape("<p>") + NL);
		sB.append(generateSimilarityMatrix(alineaaaa) + NL);
		sB.append(generateAsymmetricalSimilarityMatrix(alineaaaa) + NL);
		sB.append(alineaaa + escaper.escape("</p>") + NL);
		sB.append(alineaa + escaper.escape("<h3>Category coherence scores : </h3>") + NL);
		sB.append(alineaaa + escaper.escape("<p>") + NL);
		sB.append(generateCategoricalCoherenceArray(alineaaaa));
		sB.append(alineaaa + escaper.escape("</p>") + NL);
		sB.append(escaper.escape("<hr>"));
		sB.append(alinea + escaper.escape("<h2>Context : </h2>") + NL);
		sB.append(generateInputHTMLTranslation(alineaa));
		sB.append(alineaa + escaper.escape("<h3>Category lattice : </h3>") + NL);
		sB.append(alineaaa + escaper.escape("<p>") + NL);
		sB.append(displayFigure("category_lattice", alineaaaa, "Category lattice") + NL);
		sB.append(alineaaa + escaper.escape("</p>") + NL);
		sB.append("   " + escaper.escape("</body>" + NL));
		sB.append(escaper.escape("</html>"));
		htmlPage = sB.toString();
		String sep = File.separator;
		File pageFile = new File(folderPath + sep + "representation.htm");
		FileWriter writer = new FileWriter(pageFile);
		writer.write(htmlPage);
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
	
	private String displayFigure(String fileName, String alinea, String caption) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		sB.append(alinea + escaper.escape("<figure>") + NL);
		sB.append(alineaa + escaper.escape("<img src = \"" + folderPath + fileName + "\" alt = " + fileName + ">") 
			+ NL);
		sB.append(alineaa + escaper.escape("<figcaption>" + caption + "</figcaption>") + NL);
		sB.append(alinea + escaper.escape("</figure>") + NL);
		return sB.toString();
	}
	
	private String round(double nb) {
		return df.format(nb).toString();
	}

}
