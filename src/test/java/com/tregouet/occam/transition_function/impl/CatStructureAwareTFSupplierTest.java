package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.utils.Visualizer;

public class CatStructureAwareTFSupplierTest {
	
	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private static ICategories categories;
	private static final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		categories = new Categories(shapes2Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenRequestedThenReturnsRepresentedCatStructuresInIncreasingCostOrder() throws IOException {
		boolean increasingOrder = true;
		int checkCount = 1;
		ICatStructureAwareTFSupplier transFuncSupplier = new CatStructureAwareTFSupplier(categories, constructs);
		/*
		System.out.println(transFuncSupplier.getDefinitionOfObjects() + System.lineSeparator());
		*/
		IRepresentedCatTree representedCatTree = transFuncSupplier.next();
		double prevCost = representedCatTree.getCost();
		/*
		int idx = 0;
		visualize(representedCatTree, idx);
		*/
		while (transFuncSupplier.hasNext()) {
			representedCatTree = transFuncSupplier.next();			
			double nextCost = representedCatTree.getCost();
			/*
			idx++;
			visualize(representedCatTree, idx);
			*/
			if (nextCost < prevCost)
				increasingOrder = false;
			prevCost = nextCost;
			checkCount ++;
		}
		assertTrue(increasingOrder && checkCount > 0);
	}
	
	@SuppressWarnings("unused")
	private void visualize(IRepresentedCatTree representedCatTree, int index) throws IOException {
		System.out.println("********************************");
		System.out.println(representedCatTree.getExtentStructureAsString() 
				+ " : " 
				+ Double.toString(representedCatTree.getCost()));
		ITransitionFunction tF = representedCatTree.getTransitionFunction();
		System.out.println(tF.getDomainSpecificLanguage().toString());
		Visualizer.visualizeTransitionFunction(tF, "TF_" + Integer.toString(index));
	}

}
