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
import java.util.TreeSet;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.representation_gen.transitions_gen.impl.ProductionSetBuilder;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.transition_function_gen.IStructureBasedTFSupplier;
import com.tregouet.occam.alg.transition_function_gen.impl.StructureBasedTFSupplier;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.IIsomorphicAutomatons;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.IRepresentationDisplayer;
import com.tregouet.occam.io.output.utils.Visualizer;

public class RepresentationDisplayer implements IRepresentationDisplayer {

	private static final String NL = System.lineSeparator();
	private static final Path headPath = Paths.get(".", "src", "main", "java", "com", "tregouet", "occam", "io", 
			"output", "html", "head.txt");
	private static final DecimalFormat df = new DecimalFormat("#.####");
	private final String folderPath;
	private TreeSet<IContextObject> context = null;
	private IPreconcepts preconcepts = null;
	private IStructureBasedTFSupplier structureBasedTFSupplier = null;
	private IIsomorphicAutomatons isomorphicAutomatons = null;
	private int denotSetTreeIdx = 0;
	private Iterator<IAutomaton> iteOverTF = null;
	private IAutomaton currentTransFunc = null;
	private int transFuncIdx = 0;
	
	public RepresentationDisplayer(String folderPath) {
		this.folderPath = folderPath;
		Visualizer.setLocation(folderPath);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_6);
	}

	private static String round(double nb) {
		return df.format(nb).toString();
	}

	@Override
	public String generateAsymmetricalSimilarityMatrix(String alinea) {
		double[][] asymmetricalSimilarityMatrix = 
				currentTransFunc.getSimilarityCalculator().getAsymmetricalSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(displayTable(asymmetricalSimilarityMatrix, "Asymmetrical similarity matrix", alinea));
		sB.append("<br>" + NL);
		return sB.toString();
	}

	@Override
	public void generateDenotationSetLatticeGraph() throws IOException {
		DirectedAcyclicGraph<IPreconcept, IIsA> lattice = preconcepts.getLatticeOfConcepts();
		TransitiveReduction.INSTANCE.reduce(lattice); 
		Visualizer.visualizeDenotationSetGraph(lattice, "denotation_lattice.png");
	}

	@Override
	public String generateConceptualCoherenceArray(String alinea) {
		Map<Integer, Double> catIDToCoherence = 
				currentTransFunc.getSimilarityCalculator().getConceptualCoherenceMap();
		List<IPreconcept> topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(currentTransFunc.getTreeOfDenotationSets()).forEachRemaining(topologicalOrder::add);
		StringBuilder sB = new StringBuilder();
		String alineaa = alinea + "   ";
		String alineaaa = alineaa + "   ";
		String alineaaaa = alineaaa + "   ";
		sB.append(alinea + "<table>" + NL);
		sB.append(alinea + "<caption> " + "Conceptual coherence" + "</caption>" + NL);
		sB.append(alineaa + "<thead>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (IPreconcept cat : topologicalOrder)
			sB.append(alineaaaa + "<th>" + Integer.toString(cat.getID()) + "</th>");
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</thead>" + NL);
		sB.append(alineaa + "<tbody>" + NL);
		sB.append(alineaaa + "<tr>" + NL);
		for (IPreconcept cat : topologicalOrder)
			sB.append(alineaaaa + "<td>" + round(catIDToCoherence.get(cat.getID())) + "</td>");
		sB.append(alineaaa + "</tr>" + NL);
		sB.append(alineaa + "</tbody>" + NL);
		sB.append(alinea + "</table><br>" + NL);
		return sB.toString();
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
		sB.append(alinea + "<h2>Representation " + Integer.toString(denotSetTreeIdx) + " : </h2>" + NL);
		sB.append(alineaa + "<p>" + NL);
		sB.append(alineaaa + "<b>Coherence score : " 
				+ round(currentTransFunc.getSimilarityCalculator().getCoherenceScore()) + "</b>" + NL);
		sB.append(alineaaa + "Transition function cost : " + round(currentTransFunc.getCost()) + NL);
		sB.append(alineaa + "</p>" + NL);
		sB.append(alineaa + "<p>" + NL);
		sB.append(alineaaa + "<b>Extent structure : </b>" + isomorphicAutomatons.getExtentStructureAsString() + NL);
		sB.append(alineaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Prophyrian tree : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(displayFigure("porphyrian_tree.png", alineaaaa, "Porphyrian tree"));
		sB.append(alineaaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Tree of denotation sets : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(displayFigure("denot_set_tree.png", alineaaaa, "Tree of denotation sets"));
		sB.append(alineaaa + "</p>" + NL);		
		sB.append(alineaa + "<h3>Transition function : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(displayFigure("transition_function.png", alineaaaa, "Transition function"));
		sB.append(alineaaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Tree of denotations : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(displayFigure("denot_tree.png", alineaaaa, "Tree of denotations"));
		sB.append(alineaaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Similarity matrices : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(generateSimilarityMatrix(alineaaaa) + NL);
		sB.append(generateAsymmetricalSimilarityMatrix(alineaaaa) + NL);
		sB.append(alineaaa + "</p>" + NL);
		sB.append(alineaa + "<h3>Concept coherence scores : </h3>" + NL);
		sB.append(alineaaa + "<p>" + NL);
		sB.append(generateConceptualCoherenceArray(alineaaaa));
		sB.append(alineaaa + "</p>" + NL);
		sB.append("<hr>");
		sB.append(alinea + "<h2>Lattice of denotation sets: </h2>" + NL);
		sB.append(alineaa + "<p>" + NL);
		sB.append(displayFigure("concept_lattice.png", alineaaa, "Lattice of denotation sets") + NL);
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
	public void generatePorphyrianTree() throws IOException {
		Visualizer.visualizePorphyrianTree(currentTransFunc, "porphyrian_tree.png");
	}

	@Override
	public String generateSimilarityMatrix(String alinea) {
		double[][] similarityMatrix = currentTransFunc.getSimilarityCalculator().getSimilarityMatrix();
		StringBuilder sB = new StringBuilder();
		sB.append(displayTable(similarityMatrix, "Similarity matrix", alinea));
		sB.append("<br>" + NL);
		return sB.toString();
	}

	@Override
	public void generateTransitionFunctionGraph() throws IOException {
		Visualizer.visualizeTransitionFunction(currentTransFunc, "transition_function.png");
	}

	@Override
	public void generateTreeOfDenotationSets() throws IOException {
		Visualizer.visualizeDenotationSetGraph(isomorphicAutomatons.getTreeOfDenotationSets(), "denot_set_tree.png");;
	}
	
	@Override
	public void generateTreeOfDenotations() throws IOException {
		Visualizer.visualizeDenotationGraph(currentTransFunc.getTreeOfDenotationsWithNoBlankProduction(), "denot_tree.png");
	}

	@Override
	public double getCoherenceScore() {
		return currentTransFunc.getSimilarityCalculator().getCoherenceScore();
	}

	@Override
	public int getDenotationSetTreeIndex() {
		return denotSetTreeIdx;
	}

	@Override
	public int getTransitionFunctionIndex() {
		return transFuncIdx;
	}

	@Override
	public boolean hasNextConceptualStructure() {
		return (structureBasedTFSupplier != null && structureBasedTFSupplier.hasNext());
	}

	@Override
	public boolean hasNextTransitionFunctionOverCurrentConceptualStructure() {
		return (iteOverTF != null && iteOverTF.hasNext());
	}

	@Override
	public void nextTreeOfDenotationSets() throws IOException {
		isomorphicAutomatons = structureBasedTFSupplier.next();
		denotSetTreeIdx++;
		generateTreeOfDenotationSets();
		iteOverTF = isomorphicAutomatons.getIteratorOverAutomatons();
		currentTransFunc = iteOverTF.next();
		generateTreeOfDenotations();
		generateTransitionFunctionGraph();
		generatePorphyrianTree();
	}
	
	@Override
	public void nextTransitionFunctionOverCurrentConceptualStructure() throws IOException {
		currentTransFunc = iteOverTF.next();
		generateTreeOfDenotations();
		generateTransitionFunctionGraph();
		generatePorphyrianTree();
	}
	
	@Override
	public boolean represent(Path contextPath) throws IOException {
		try {
			context = new TreeSet<>(GenericFileReader.getContextObjects(contextPath));
		} catch (IOException e) {
			return false;
		}
		preconcepts = new Preconcepts(context);
		List<IStronglyContextualized> stronglyContextualizeds = new ProductionSetBuilder(preconcepts).getProductions();
		DirectedAcyclicGraph<IDenotation, IStronglyContextualized> constructs = 
				new DirectedAcyclicGraph<>(null, null, false);
		stronglyContextualizeds.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		try {
			structureBasedTFSupplier = new StructureBasedTFSupplier(preconcepts, constructs);
		} catch (IOException e) {
			return false;
		}
		isomorphicAutomatons = structureBasedTFSupplier.next();
		iteOverTF = isomorphicAutomatons.getIteratorOverAutomatons();
		currentTransFunc = iteOverTF.next();
		generateDenotationSetLatticeGraph();
		generateTreeOfDenotationSets();
		generateTreeOfDenotations();
		generateTransitionFunctionGraph();
		generatePorphyrianTree();
		return true;
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

}
