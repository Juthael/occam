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

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
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
	private static final SimilarityCalculationStrategy SIM_CALCULATION_STRATEGY = 
			SimilarityCalculationStrategy.RATIO_MODEL;
	private static final DecimalFormat df = new DecimalFormat("#.####");
	private final String folderPath;
	private List<IContextObject> context = null;
	private IConcepts concepts = null;
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
	public boolean whatIsThere(Path contextPath) throws IOException, InvalidInputException {
		try {
			context = GenericFileReader.getContextObjects(contextPath);
		} catch (FileReaderException e) {
			return false;
		}
		concepts = new Concepts(context);
		List<IProduction> productions = new ProductionBuilder(concepts).getProductions();
		DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
				new DirectedAcyclicGraph<>(null, null, false);
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		try {
			catStructureAwareTFSupplier = new CatStructureAwareTFSupplier(concepts, constructs, 
					SIM_CALCULATION_STRATEGY);
		} catch (InvalidInputException e) {
			return false;
		}
		representedCatTree = catStructureAwareTFSupplier.next();
		iteOverTF = representedCatTree.getIteratorOverTransitionFunctions();
		currentTransFunc = iteOverTF.next();
		generateCategoryLatticeGraph();
		generateCategoryTreeGraph();
		generateTransitionFunctionGraph();
		return true;
	}

	@Override
	public double getCoherenceScore() {
		return currentTransFunc.getCoherenceScore();
	}

	@Override
	public String generateInputHTMLTranslation(String alinea) {
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		String alineaaaaa = alineaaaa + "   ";
		StringBuilder sB = new StringBuilder();
		sB.append(alinea + "<h3> Input context : </h3> <br>" + NL);
		sB.append(alineaa + "<table>" + NL);
		sB.append(alineaaa + "<thead>" + NL);
		sB.append(alineaaaa + "<tr>" + NL);
		for (IContextObject obj : context) {
			sB.append(alineaaaaa + "<th>" + obj.getName() + "</th>" + NL);
		}
		sB.append(alineaaaa + "</tr>" + NL);
		sB.append(alineaaa + "</thead>" + NL);
		sB.append(alineaaa + "<tbody>" + NL);
		sB.append(alineaaaa + "<tr>" + NL);
		for (IContextObject obj : context) {
			sB.append(alineaaaaa + "<td>" + NL);
			for (IConstruct construct : obj.getConstructs())
				sB.append(construct.toString() + "<br>" + NL);
			sB.append(alineaaaaa + "</td>" + NL);
		}
		sB.append(alineaaaa + "</tr>" + NL);
		sB.append(alineaaa + "</tbody>" + NL);
		sB.append(alineaa + "</table>" + NL);
		return sB.toString();
	}

	@Override
	public void generateCategoryLatticeGraph() throws IOException {
		DirectedAcyclicGraph<IConcept, IsA> lattice = concepts.getCategoryLattice();
		TransitiveReduction.INSTANCE.reduce(lattice); 
		Visualizer.visualizeCategoryGraph(lattice, "category_lattice.png");
	}
	
	@Override
	public void generateCategoryTreeGraph() throws IOException {
		Visualizer.visualizeCategoryGraph(representedCatTree.getCategoryTree(), "category_tree.png");;
	}

	@Override
	public void generateTransitionFunctionGraph() throws IOException {
		Visualizer.visualizeTransitionFunction(currentTransFunc, "transition_function.png", 
				TransitionFunctionGraphType.FINITE_AUTOMATON);
	}

	@Override
	public String generateSimilarityMatrix(String alinea) {
		double[][] similarityMatrix = currentTransFunc.getSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(displayTable(similarityMatrix, "Similarity matrix", alinea));
		sB.append("<br>" + NL);
		return sB.toString();
	}

	@Override
	public String generateAsymmetricalSimilarityMatrix(String alinea) {
		double[][] asymmetricalSimilarityMatrix = currentTransFunc.getAsymmetricalSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(displayTable(asymmetricalSimilarityMatrix, "Asyymetrical similarity matrix", alinea));
		sB.append("<br>" + NL);
		return sB.toString();
	}

	@Override
	public String generateCategoricalCoherenceArray(String alinea) {
		Map<Integer, Double> catIDToCoherence = currentTransFunc.getConceptualCoherenceMap();
		List<IConcept> topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(currentTransFunc.getCategoryTree()).forEachRemaining(topologicalOrder::add);
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + NL);
		sB.append(alinea + "<caption> " + "Category coherence" + "</caption>" + NL);
		sB.append(alineaa + "<thead>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (IConcept cat : topologicalOrder)
			sB.append(alineaaaa + "<th>" + Integer.toString(cat.getID()) + "</th>");
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</thead>" + NL);
		sB.append(alineaa + "<tbody>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (IConcept cat : topologicalOrder)
			sB.append(alineaaaa + "<td>" + round(catIDToCoherence.get(cat.getID())) + "</td>");
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</tbody>" + NL);
		sB.append(alinea + "</table><br>" + NL);
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
		sB.append(alinea + "<h2>Context : </h2>" + NL);
		sB.append(generateInputHTMLTranslation(alineaa));
		sB.append("<hr>");
		sB.append(alinea + "<h2>Representation " + Integer.toString(catTreeIdx) + " : </h2>" + NL);
		sB.append(alineaa + "<p>" + NL);
		sB.append(alineaaa + "<b>Score : " + round(currentTransFunc.getCoherenceScore()) + "</b>" + NL);
		sB.append(alineaa + "</p>" + NL);
		sB.append(alineaa + "<p>" + NL);
		sB.append(alineaaa + "<b>Extent structure : </b>" + representedCatTree.getExtentStructureAsString() + NL);
		sB.append(alineaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Category tree : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(displayFigure("category_tree.png", alineaaaa, "Category tree"));
		sB.append(alineaaa + "</p>" + NL);		
		sB.append(alineaa + "<h3>Transition function : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(displayFigure("transition_function.png", alineaaaa, "Transition function"));
		sB.append(alineaaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Similarity matrices : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(generateSimilarityMatrix(alineaaaa) + NL);
		sB.append(generateAsymmetricalSimilarityMatrix(alineaaaa) + NL);
		sB.append(alineaaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Category coherence scores : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(generateCategoricalCoherenceArray(alineaaaa));
		sB.append(alineaaa + "</p>" + NL);
		sB.append("<hr>");
		sB.append(alinea + "<h2>Category lattice : </h2>" + NL);
		sB.append(alineaa + "<p>" + NL);
		sB.append(displayFigure("category_lattice.png", alineaaa, "Category lattice") + NL);
		sB.append(alineaa + "</p>" + NL);
		sB.append("   " + "</body>" + NL);
		sB.append("</html>");
		htmlPage = sB.toString();
		String sep = File.separator;
		File pageFile = new File(folderPath + sep + "representation.htm");
		FileWriter writer = new FileWriter(pageFile);
		writer.write(htmlPage);
		writer.close();
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
		sB.append(alinea + "<table>" + NL);
		sB.append(alinea + "<caption> " + caption + "</caption>" + NL);
		sB.append(alineaa + "<thead>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (int i = 0 ; i <= context.size() ; i++) {
			sB.append(alineaaaa + "<th>");
			if (i > 0)
				sB.append("obj" + Integer.toString(i - 1));
			sB.append("</th>" + NL);
		}
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</thead>" + NL);
		sB.append(alineaa + "<tbody>" + NL);
		for (int j = 1 ; j <= context.size() ; j++) {
			sB.append(alineaaa + "<tr>" + NL);
			for (int i = 0 ; i <= context.size() ; i++) {
				if (i == 0)
					sB.append(alineaaaa + "<th>obj" + Integer.toString(j - 1) + "</th>" + NL);
				else sB.append(alineaaaa + "<td>" + round(table[i - 1][j - 1]) + "</td>" + NL);
					
			}
			sB.append(alineaaa + "</tr>" + NL);
		}
		sB.append(alineaa + "</tbody>" + NL);
		sB.append(alinea + "</table>" + NL);
		return sB.toString();
	}
	
	private String displayFigure(String fileName, String alinea, String caption) {
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		sB.append(alinea + "<figure>" + NL);
		sB.append(alineaa + "<img src = \"" + "file:///" + folderPath + "\\" + fileName + "\" alt = " + fileName + ">" 
			+ NL);
		sB.append(alineaa + "<figcaption>" + caption + "</figcaption>" + NL);
		sB.append(alinea + "</figure>" + NL);
		return sB.toString();
	}
	
	private String round(double nb) {
		return df.format(nb).toString();
	}

}
