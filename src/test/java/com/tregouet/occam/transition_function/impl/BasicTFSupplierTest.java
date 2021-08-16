package com.tregouet.occam.transition_function.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.IBasicTFSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;

public class BasicTFSupplierTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private static ICategories categories;
	private static ICatTreeSupplier catTreeSupplier;
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
		TransitiveReduction.INSTANCE.reduce(constructs);		
	}

	@Before
	public void setUp() throws Exception {
		catTreeSupplier = categories.getCatTreeSupplier();
	}

	@Test
	public void whenRequestedThenReturnsTransitionFuncInIncreasingCostOrder() {
		boolean increasingOrder = true;
		int checkCount = 1;
		IBasicTFSupplier transFuncSupplier = new BasicTFSupplier(categories, constructs);
		double prevCost = transFuncSupplier.next().getCost();
		/*
		System.out.println("0 : " + Double.toString(prevCost));
		*/
		while (transFuncSupplier.hasNext()) {
			double nextCost = transFuncSupplier.next().getCost();
			if (nextCost < prevCost)
				increasingOrder = false;
			prevCost = nextCost;
			checkCount++;
			/*
			System.out.println(checkCount + " : " + Double.toString(nextCost));
			*/
		}
		assertTrue(increasingOrder && checkCount > 0);
	}

}
