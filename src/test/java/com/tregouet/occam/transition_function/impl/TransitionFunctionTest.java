package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
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

	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static final PropertyWeighingStrategy PROP_WHEIGHING_STRATEGY = 
			PropertyWeighingStrategy.INFORMATIVITY_DIAGNOSTIVITY;
	private static final SimilarityCalculationStrategy SIM_CALC_STRATEGY = 
			SimilarityCalculationStrategy.CONTRAST_MODEL;
	private static List<IContextObject> shapes1Obj;
	private ICategories categories;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationTreeSupplier classificationTreeSupplier;
	private Tree<ICategory, IsA> catTree;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Tree<IIntentAttribute, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);		
	}

	@Before
	public void setUp() throws Exception {
		transitionFunctions = new TreeSet<>();
		categories = new Categories(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		classificationTreeSupplier = categories.getCatTreeSupplier();
		while (classificationTreeSupplier.hasNext()) {
			catTree = classificationTreeSupplier.nextOntologicalCommitment();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes1Obj, categories.getObjectCategories(), catTree, constrTree, 
								PROP_WHEIGHING_STRATEGY, SIM_CALC_STRATEGY);
				/*
				visualize("2108140757");
				*/
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
	public void whenCompilerRequestedThenReturned() {
		fail("NOT YET IMPLEMENTED");
	}

	@Test 
	public void when2ProductionsHaveSameSourceCategoryAndSameTargetAttributeThenHandledBySameOperator() {
		boolean sameOperator = true;
		int checkCount = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			Map<IProduction, IOperator> prodToOpe = new HashMap<>();
			for (IOperator operator : tF.getTransitions()) {
				for (IProduction production : operator.operation()) {
					prodToOpe.put(production, operator);
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
			for (IOperator operator : tF.getTransitions()) {
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
			for (IOperator operator : tF.getTransitions()) {
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
		assertTrue(consistent);
	}
	
	private void visualize(String timestamp) throws IOException {
		Categories castcats = (Categories) categories;
		Visualizer.visualizeCategoryGraph(castcats.getTransitiveReduction(), timestamp + "categories");
		Visualizer.visualizeCategoryGraph(catTree, timestamp + "_cat_tree");
		Visualizer.visualizeAttributeGraph(constructs, timestamp + "_constructs");
		Visualizer.visualizeAttributeGraph(filtered_reduced_constructs, timestamp + "_filtered_red_const");
		Visualizer.visualizeAttributeGraph(constrTree, timestamp + "_construct_tree");
	}
	
	private static boolean sameSourceAndTargetCategoryForAll(List<IProduction> productions) {
		boolean sameSourceAndTargetCategory = true;
		ICategory sourceCategory = null;
		ICategory targetCategory = null;
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
