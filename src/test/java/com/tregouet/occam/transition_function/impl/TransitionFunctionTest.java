package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.CatTreeSupplier;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.utils.Visualizer;
import com.tregouet.tree_finder.data.InTree;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

@SuppressWarnings("unused")
public class TransitionFunctionTest {

	private static Path shapes1 = Paths.get(".", "src", "test", "java", "files", "shapes1.txt");
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
		catTree = catTreeSupplier.next();
		filtered_constructs = 
				TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, reduced_constructs);
		constrTreeSupplier = new IntentAttTreeSupplier(filtered_constructs, true);		
		constrTree = constrTreeSupplier.next();
		/*
		visualize("2108081330");
		*/
		transitionFunction = 
				new TransitionFunction(shapes1Obj, categories.getObjectCategories(), catTree, constrTree);
	}

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void whenCategoryStructureDOTFileRequestedThenReturned() throws IOException {
		boolean dotFileReturnedIsValid = true;
		String stringDOT = transitionFunction.getCategoryStructureAsDOTFile();
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
		assertTrue(stringDOT != null && !stringDOT.isEmpty() && dotFileReturnedIsValid);
	}
	
	@Test
	public void whenTransitionFunctionDOTFileRequestedThenReturned() throws IOException {
		boolean dotFileReturnedIsValid = true;
		String stringDOT = transitionFunction.getTransitionFunctionAsDOTFile();
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
		assertTrue(stringDOT != null && !stringDOT.isEmpty() && dotFileReturnedIsValid);
	}
	
	@Test
	public void whenDomainSpecificLanguageRequestedThenReturned() {
		fail("NOT YET IMPLEMENTED");
	}
	
	@Test
	public void whenCompilerRequestedThenReturned() {
		fail("NOT YET IMPLEMENTED");
	}

	@Test 
	public void when2ProductionsHaveSameSourceCategoryAndSameTargetAttributeThenHandledBySameOperator() {
		fail("Not yet implemented");
	}
	
	@Test
	public void when2ProductionsHaveSameSourceAndTargetCategoriesAndSameValueThenHandledBySameOperator() {
		fail("Not yet implemented");
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
