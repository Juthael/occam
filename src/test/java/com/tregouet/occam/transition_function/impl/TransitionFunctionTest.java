package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.source.tree.AssertTree;
import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.CatTreeSupplier;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.BasicProduction;
import com.tregouet.occam.data.operators.impl.BlankProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.utils.Visualizer;
import com.tregouet.tree_finder.data.InTree;
import com.tregouet.tree_finder.error.InvalidSemiLatticeExeption;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

@SuppressWarnings("unused")
public class TransitionFunctionTest {

	private static Path shapes1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private static ICategories categories;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> reduced_constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private static ICatTreeSupplier catTreeSupplier;
	private static InTree<ICategory, DefaultEdge> catTree;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_constructs;
	private static IIntentAttTreeSupplier constrTreeSupplier;
	private static InTree<IIntentAttribute, IProduction> constrTree;
	private static ITransitionFunction transitionFunction;
	private static TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(shapes1);
		categories = new Categories(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		reduced_constructs = (DirectedAcyclicGraph<IIntentAttribute, IProduction>) constructs.clone();
		TransitiveReduction.INSTANCE.reduce(reduced_constructs);
		catTreeSupplier = categories.getCatTreeSupplier();
		while (catTreeSupplier.hasNext()) {
			catTree = catTreeSupplier.next();
			filtered_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, reduced_constructs);
			constrTreeSupplier = new IntentAttTreeSupplier(filtered_constructs);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.next();
				transitionFunction = 
						new TransitionFunction(shapes1Obj, categories.getObjectCategories(), catTree, constrTree);
				/*
				visualize("2108140757");
				*/
				transitionFunctions.add(transitionFunction);
			}
		}		
	}

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void whenCategoryStructureDOTFileRequestedThenReturned() throws IOException {
		boolean dotFileReturnedIsValid = true;
		for (ITransitionFunction tF : transitionFunctions) {
			String stringDOT = tF.getCategoryStructureAsDOTFile();
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
			//display graph
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File("D:\\ProjetDocs\\essais_viz\\" + "cat_dot_test"));
			*/
		}
		assertTrue(dotFileReturnedIsValid);
	}
	
	@Test
	public void whenTransitionFunctionDOTFileRequestedThenReturned() throws IOException {
		boolean dotFileReturnedIsValid = true;
		for (ITransitionFunction tF : transitionFunctions) {
			String stringDOT = tF.getTransitionFunctionAsDOTFile();
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
			/*
			//display graph
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File("D:\\ProjetDocs\\essais_viz\\" + "tf_dot_test"));
			*/	
		}
		assertTrue(dotFileReturnedIsValid);
	}
	
	@Test
	public void whenDomainSpecificLanguageRequestedThenReturned() {
		boolean languageReturned = true;
		for (ITransitionFunction tF : transitionFunctions) {
			try {
				IDSLanguageDisplayer languageDisplayer = transitionFunction.getDomainSpecificLanguage();
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
	
	
	//PROBLEM HERE
	@Test
	public void when2NonBlankProductionsHaveSameSourceAndTargetCategoriesAndSameValueThenHandledBySameOperator() 
			throws InvalidSemiLatticeExeption, IOException {
		boolean sameOperator = true;
		int checkCount = 0;
		for (ITransitionFunction tF : transitionFunctions) {
			/*
			visualize("2108140818");
			System.out.println(tF.getDomainSpecificLanguage().toString());
			Visualizer.visualizeTransitionFunction(tF, "2108140828_tf");
			*/
			Map<IBasicProduction, IOperator> prodToOpe = new HashMap<>();
			for (IOperator operator : tF.getTransitions()) {
				for (IProduction production : operator.operation()) {
					if (production instanceof BlankProduction) {
					}
					else if (production instanceof IBasicProduction) {
						prodToOpe.put((IBasicProduction) production, operator);
					}
					else {
						ICompositeProduction compoProd = (ICompositeProduction) production;
						for (IBasicProduction basicProd : compoProd.getComponents()) {
							prodToOpe.put(basicProd, operator);
						}
					}
				}
			}
			List<IBasicProduction> basicProds = new ArrayList<>(prodToOpe.keySet());
			for (int i = 0 ; i < basicProds.size() - 1 ; i++) {
				for (int j = i + 1 ; j < basicProds.size() ; j++) {
					IBasicProduction iProd = basicProds.get(i);
					IBasicProduction jProd = basicProds.get(j);
					if (iProd.getSourceCategory().equals(jProd.getSourceCategory())
							&& iProd.getTargetCategory().equals(jProd.getTargetCategory())
							&& iProd.getValue().equals(jProd.getValue())) {
						checkCount++;
						if (!prodToOpe.get(jProd).equals(prodToOpe.get(jProd)))
							sameOperator = false;
					}
				}
			}
		}
		assertTrue(sameOperator == true && checkCount > 0);
	}
	
	@Test
	public void whenProductionsHandledBySameOperatorThenConsistentWithRequirements() {
		fail("Not yet implemented");
	}
	
	@Test
	public void whenTransitionFunctionInstantiatedThenGraphIsATree() {
		fail("Not yet implemented");
	}
	
	@Test
	public void whenOperatorsBuiltThenHaveExpectedCost() {
		fail("Not yet implemented");
	}
	
	private static void visualize(String timestamp) throws IOException {
		Categories castcats = (Categories) categories;
		Visualizer.visualizeCategoryGraph(castcats.getGraph(), timestamp + "categories");
		Visualizer.visualizeCategoryGraph(catTree, timestamp + "_cat_tree");
		Visualizer.visualizeAttributeGraph(constructs, timestamp + "_constructs");
		Visualizer.visualizeAttributeGraph(reduced_constructs, timestamp + "_constructs_reduced");
		Visualizer.visualizeAttributeGraph(filtered_constructs, timestamp + "_filtered_const");
		Visualizer.visualizeAttributeGraph(constrTree, timestamp + "_construct_tree");
	}

}
