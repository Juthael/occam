package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IClassificationTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IBasicOperator;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.ITransition;
import com.tregouet.occam.data.operators.impl.BlankProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.error.InvalidInputException;

import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

@SuppressWarnings("unused")
public class TransitionFunctionTest {

	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static final SimilarityCalculationStrategy SIM_CALC_STRATEGY = 
			SimilarityCalculationStrategy.RATIO_MODEL;
	private static List<IContextObject> objects;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationTreeSupplier classificationTreeSupplier;
	private Tree<IConcept, IsA> catTree;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Tree<IIntentAttribute, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);		
	}

	@Before
	public void setUp() throws Exception {
		transitionFunctions = new TreeSet<>();
		concepts = new Concepts(objects);
		List<IProduction> productions = new ProductionBuilder(concepts).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		classificationTreeSupplier = concepts.getCatTreeSupplier();
		while (classificationTreeSupplier.hasNext()) {
			catTree = classificationTreeSupplier.nextOntologicalCommitment();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(objects, concepts.getSingletonConcept(), catTree, constrTree, 
								SIM_CALC_STRATEGY);
				transitionFunctions.add(transitionFunction);
			}
		}
	}
	
	@Test
	public void whenCategoryStructureDOTFileRequestedThenReturned() throws IOException {
		boolean dotFileReturnedIsValid = true;
		for (ITransitionFunction tF : transitionFunctions) {
			String stringDOT = tF.getCategoryTreeAsDOTFile();
			if (stringDOT == null || stringDOT.isEmpty())
				dotFileReturnedIsValid = false;
			/*
			 System.out.println(writer.toString());
			 */
			MutableGraph dotGraph = null;
			try {
				dotGraph = new Parser().read(stringDOT);
			}
			catch (Exception e) {
				dotFileReturnedIsValid = false;
			}
		}
		assertTrue(dotFileReturnedIsValid);
	}
	
	@Test
	public void whenTransitionFunctionDOTFileRequestedThenReturned() throws IOException {
		boolean dotFileReturnedIsValid = true;
		for (ITransitionFunction tF : transitionFunctions) {
			String stringDOT = tF.getTransitionFunctionAsDOTFile(TransitionFunctionGraphType.FINITE_AUTOMATON);
			if (stringDOT == null || stringDOT.isEmpty())
				dotFileReturnedIsValid = false;
			/*
			 System.out.println(writer.toString());
			 */
			MutableGraph dotGraph = null;
			try {
				dotGraph = new Parser().read(stringDOT);
			}
			catch (Exception e) {
				dotFileReturnedIsValid = false;
			}
			/*
			//see operator definitions
			for (IOperator operator : transitionFunction.getTransitions()) {
				System.out.println("Operator " + operator.getName() + " : ");
				System.out.println(operator.toString());
			}
			*/
		}
		assertTrue(dotFileReturnedIsValid);
	}
	
	@Test
	public void whenDomainSpecificLanguageRequestedThenReturned() {
		boolean languageReturned = true;
		for (ITransitionFunction tF : transitionFunctions) {
			try {
				IDSLanguageDisplayer languageDisplayer = tF.getDomainSpecificLanguage();
				/*
				System.out.println(languageDisplayer.toString());
				*/
				if (languageDisplayer == null)
					languageReturned = false;
			}
			catch (Exception e) {
				languageReturned = false;
			}
		}
		assertTrue(languageReturned);
	}
	
	@Test
	public void whenSimilarityMatrixRequestedThenReturned() throws IOException {
		int tfIdx = 0;
		int returned = 0;
		/*
		StringBuilder sB = new StringBuilder();
		int objIdx = 0;
		for (IContextObject obj : categories.getContextObjects()) {
			sB.append("Object_" + Integer.toString(objIdx++) + " : " + System.lineSeparator() 
				+ obj.getConstructs().toString());
			sB.append(System.lineSeparator());
		}
		*/
		for (ITransitionFunction tF : transitionFunctions) {
			/*
			Visualizer.visualizeTransitionFunction(tF, "TransFunc" + Integer.toString(tfIdx), 
					TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			double[][] matrix = tF.getSimilarityMatrix();
			if (matrix != null)
				returned++;
			/*
			sB.append("TRANS_FUNC_" + Integer.toString(tfIdx) + " : " + System.lineSeparator());
			for (double[] line : matrix)
				sB.append(Arrays.toString(line) + System.lineSeparator());
			sB.append(System.lineSeparator());
			*/
			tfIdx++;
		}
		/*
		System.out.println(sB.toString());
		*/
		assertTrue(tfIdx == returned);
	}
	
	@Test
	public void whenAsymmetricalSimilarityMatrixRequestedThenReturned() throws IOException {
		int tfIdx = 0;
		int returned = 0;
		/*
		StringBuilder sB = new StringBuilder();
		int objIdx = 0;
		for (IContextObject obj : categories.getContextObjects()) {
			sB.append("Object_" + Integer.toString(objIdx++) + " : " + System.lineSeparator() 
				+ obj.getConstructs().toString());
			sB.append(System.lineSeparator());
		}
		*/
		for (ITransitionFunction tF : transitionFunctions) {
			/*
			Visualizer.visualizeTransitionFunction(tF, "TransFunc" + Integer.toString(tfIdx), 
					TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			double[][] matrix = tF.getAsymmetricalSimilarityMatrix();
			if (matrix != null)
				returned++;
			/*
			sB.append("TRANS_FUNC_" + Integer.toString(tfIdx) + " : " + System.lineSeparator());
			for (double[] line : matrix)
				sB.append(Arrays.toString(line) + System.lineSeparator());
			sB.append(System.lineSeparator());
			*/
			tfIdx++;
		}
		/*
		System.out.println(sB.toString());
		*/
		assertTrue(tfIdx == returned);
	}
	
	@Test
	public void whenCategoricalCoherenceArrayRequestedThenReturned() throws IOException {
		boolean returned = true;
		int nbOfChecks = 0;
		/*
		StringBuilder sB = new StringBuilder();
		int tFIdx = 0;
		*/
		for (ITransitionFunction tF : transitionFunctions) {
			/*
			sB.append("***Transition Function " + Integer.toString(tFIdx) + System.lineSeparator());
			Visualizer.visualizeTransitionFunction(tF, "TFunc_" + Integer.toString(tFIdx++), 
					TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			Map<Integer, Double> coherenceMap = tF.getCategoricalCoherenceMap();
			if (coherenceMap == null || coherenceMap.isEmpty())
				returned = false;
			/*
			List<ICategory> topoOrder = new ArrayList<>();
			new TopologicalOrderIterator<>(tF.getCategoryTree()).forEachRemaining(topoOrder::add);
			for (ICategory cat : topoOrder) {
				sB.append("Category_" + Integer.toString(cat.getID()) + " : " + System.lineSeparator() 
					+ cat.getIntent().toString() + System.lineSeparator());
				sB.append("Coherence : " + coherenceMap.get(cat.getID()).toString());
				sB.append(System.lineSeparator() + System.lineSeparator());
			}
			*/	
			nbOfChecks++;
		}
		/*
		System.out.println(sB.toString());
		*/
		assertTrue(returned && nbOfChecks > 0);
	}
	
	@Test
	public void whenTypicalityArrayRequestedThenReturned() {
		boolean returned = true;
		int nbOfChecks = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			double[] typicalityArray = tF.getTypicalityArray();
			if (typicalityArray == null || typicalityArray.length != objects.size())
				returned = false;
			nbOfChecks++;
		}
		assertTrue(returned && nbOfChecks > 0);
	}
	
	@Test
	public void whenCompilerRequestedThenReturned() {
		fail("NOT IMPLEMENTED YET");
	}

	@Test 
	public void when2ProductionsHaveSameSourceCategoryAndSameTargetAttributeThenHandledBySameOperator() {
		boolean sameOperator = true;
		int checkCount = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			Map<IProduction, IOperator> prodToOpe = new HashMap<>();
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					for (IProduction production : operator.operation()) {
						prodToOpe.put(production, operator);
					}
				}
			}
			List<IProduction> productions = new ArrayList<>(prodToOpe.keySet());
			for (int i = 0 ; i < productions.size() - 1 ; i++) {
				for (int j = i + 1 ; j < productions.size() ; j++) {
					IProduction iProd = productions.get(i);
					IProduction jProd = productions.get(j);
					if (iProd.getSourceCategory().equals(jProd.getSourceCategory())
							&& iProd.getTarget().equals(jProd.getTarget())) {
						checkCount++;
						if (!prodToOpe.get(iProd).equals(prodToOpe.get(jProd)))
							sameOperator = false;						
					}
				}
			}
		}
		assertTrue(checkCount > 0 && sameOperator);
	}
	
	@Test
	public void when2NonBlankProductionsHaveSameSourceAndTargetCategoriesAndSameValueThenHandledBySameOperator() 
			throws InvalidInputException, IOException {
		boolean sameOperator = true;
		int checkCount = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			/*
			System.out.println(tF.getDomainSpecificLanguage().toString());
			Visualizer.visualizeTransitionFunction(tF, "2108251050_tf", TransitionFunctionGraphType.FINITE_AUTOMATON);
			*/
			List<IBasicProduction> basicProds = new ArrayList<>();
			List<IOperator> basicProdsOperators = new ArrayList<>();
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					for (IProduction production : operator.operation()) {
						if (production instanceof BlankProduction) {
						}
						else if (production instanceof IBasicProduction) {
							basicProds.add((IBasicProduction) production);
							basicProdsOperators.add(operator);
						}
						else {
							ICompositeProduction compoProd = (ICompositeProduction) production;
							for (IBasicProduction basicProd : compoProd.getComponents()) {
								basicProds.add(basicProd);
								basicProdsOperators.add(operator);
							}
						}
					}
				}
			}
			for (int i = 0 ; i < basicProds.size() - 1 ; i++) {
				for (int j = i + 1 ; j < basicProds.size() ; j++) {
					IBasicProduction iProd = basicProds.get(i);
					IBasicProduction jProd = basicProds.get(j);
					if (iProd.getSourceCategory().equals(jProd.getSourceCategory())
							&& iProd.getTargetCategory().equals(jProd.getTargetCategory())
							&& iProd.getValue().equals(jProd.getValue())) {
						checkCount++;
						if (!basicProdsOperators.get(basicProds.indexOf(iProd)).equals(
								basicProdsOperators.get(basicProds.indexOf(jProd))))
							sameOperator = false;
					}
				}
			}
		}
		assertTrue(sameOperator == true && checkCount > 0);
	}
	
	@Test
	public void whenProductionsHandledBySameOperatorThenConsistentWithRequirements() {
		boolean consistent = true;
		for (ITransitionFunction tF : transitionFunctions) {
			for (ITransition transition : tF.getTransitions()) {
				if (transition instanceof IOperator) {
					IOperator operator = (IOperator) transition;
					List<IProduction> productions = operator.operation();
					if (!sameSourceAndTargetCategoryForAll(productions))
						consistent = false;
					for (IProduction production : productions) {
						if (!sameTargetAttributeAsOneOtherProduction(production, productions) 
								&& !sameValueAsOneOtherProduction(production, productions))
							consistent = false;
					}
				}
			}
		}
		assertTrue(consistent);
	}
	
	private static boolean sameSourceAndTargetCategoryForAll(List<IProduction> productions) {
		boolean sameSourceAndTargetCategory = true;
		IConcept sourceCategory = null;
		IConcept targetCategory = null;
		for (int i = 0 ; i < productions.size() ; i++) {
			if (i == 0) {
				sourceCategory = productions.get(i).getSourceCategory();
				targetCategory = productions.get(i).getTargetCategory();
			}
			else {
				if (!productions.get(i).getSourceCategory().equals(sourceCategory)
						|| !productions.get(i).getTargetCategory().equals(targetCategory))
					sameSourceAndTargetCategory = false;
			}
		}
		return sameSourceAndTargetCategory;
	}
	
	private static boolean sameTargetAttributeAsOneOtherProduction(IProduction production, List<IProduction> productions) {
		if (productions.size() == 1)
			return true;
		boolean sameTargetAttributeAsOneOther = false;
		List<IProduction> others = new ArrayList<>(productions);
		others.remove(production);
		for (IProduction other : others) {
			if (production.getTarget().equals(other.getTarget()))
				sameTargetAttributeAsOneOther = true;
		}
		return sameTargetAttributeAsOneOther;
	}
	
	private static boolean sameValueAsOneOtherProduction(IProduction production, List<IProduction> productions) {
		if (productions.size() == 1)
			return true;
		boolean sameValue = false;
		List<IProduction> others = new ArrayList<>(productions);
		others.remove(production);
		for (IProduction other : others) {
			List<IConstruct> valuesOfOther = new ArrayList<>(other.getValues());
			if (valuesOfOther.removeAll(production.getValues()))
				sameValue = true;
		}
		return sameValue;
	}
	
	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}	

}
