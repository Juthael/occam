package com.tregouet.occam.data.operators.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.exceptions.PropertyTargetingException;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.IInfoMeter;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.impl.IntentAttTreeSupplier;
import com.tregouet.occam.transition_function.impl.TransitionFunction;
import com.tregouet.occam.transition_function.impl.TransitionFunctionSupplier;
import com.tregouet.tree_finder.data.InTree;

public class InfoMeterTest {

	private static Path shapes1 = Paths.get(".", "src", "test", "java", "files", "shapes1.txt");
	private static List<IContextObject> shapes1Obj;
	private static ICategories categories;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private static ICatTreeSupplier catTreeSupplier;
	private static InTree<ICategory, DefaultEdge> catTree;
	private static DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private static IIntentAttTreeSupplier constrTreeSupplier;
	private static Map<ITransitionFunction, IInfoMeter> transFuncToInfometer = new HashMap<>();
	
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
		catTreeSupplier = categories.getCatTreeSupplier();
		while (catTreeSupplier.hasNext()) {
			catTree = catTreeSupplier.nextWithTunnelCategoriesRemoved();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByCategoryTree(catTree, constructs);
			constrTreeSupplier = new IntentAttTreeSupplier(filtered_reduced_constructs);
			while (constrTreeSupplier.hasNext()) {
				InTree<IIntentAttribute, IProduction> constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes1Obj, categories.getObjectCategories(), catTree, constrTree);
				/*
				visualize("2108140757");
				*/
				transFuncToInfometer.put(transitionFunction, transitionFunction.getInfometer());
			}
		}	
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenInformativityOfTheSpecifiedPropertyIsRequestedThenReturned() {
		/*
		System.out.println("OBJECTS : " + System.lineSeparator());
		for (IContextObject obj : shapes1Obj) {
			System.out.println(obj.toString());
		}
		System.out.println(System.lineSeparator());
		*/
		boolean returnedIndeed = true;
		for (ITransitionFunction tF : transFuncToInfometer.keySet()){
			/*
			System.out.println("NEW TRANSITION FUNCTION : " + System.lineSeparator());
			*/
			List<IOperator> properties = tF.getTransitions();
			IInfoMeter infometer = tF.getInfometer();
			for (IOperator prop : properties) {
				try {
					double informativity = infometer.getInformativity(prop);
					if (Double.isNaN(informativity))
						returnedIndeed = false;
					/*
					System.out.println(prop.toString() + Double.toString(informativity));
					System.out.println(System.lineSeparator());
					*/
				}
				catch (Exception e) {
					returnedIndeed = false;
				}
			}
		}
		assertTrue(returnedIndeed);
	}
	
	@Test
	public void whenObjectIndexIsInvalidThenExceptionThrown() {
		boolean exceptionAlwaysThrownWithInvalidIndex = true;
		List<String> propertySpecification = new ArrayList<>(Arrays.asList(new String[] {"figure", "forme"}));
		boolean propSpecIsValid = true;
		int validObjIndex = 0;
		int invalidObjIndex = shapes1Obj.size();
		for (ITransitionFunction tF : transFuncToInfometer.keySet()) {
			IInfoMeter infometer = transFuncToInfometer.get(tF);
			try {
				double informativity = infometer.getInformativity(validObjIndex, propertySpecification);
				if (Double.isNaN(informativity))
					propSpecIsValid = false;
			}
			catch (Exception e) {
				propSpecIsValid = false;
			}
			if (propSpecIsValid) {
				boolean exceptionThrown = false;
				try {
					infometer.getInformativity(invalidObjIndex, propertySpecification);
				}
				catch (PropertyTargetingException e) {
					exceptionThrown = true;
				}
				if (!exceptionThrown)
					exceptionAlwaysThrownWithInvalidIndex = false;
			}
		}
		assertTrue(propSpecIsValid && exceptionAlwaysThrownWithInvalidIndex);
	}
	
	@Test
	public void whenPropertySpecificationIsInvalidThenExceptionThrown() {
		boolean exceptionAlwaysThrownWhenInvalidSpecification = true;
		boolean whenValidThenOk = true;
		List<String> validSpecification = new ArrayList<>(Arrays.asList(new String[] {"figure", "forme"}));
		List<String> invalidSpecification = new ArrayList<>(Arrays.asList(new String[] {"figure", "foo"}));
		int validObjIndex = 0;;
		for (ITransitionFunction tF : transFuncToInfometer.keySet()) {
			IInfoMeter infometer = transFuncToInfometer.get(tF);
			try {
				double informativity = infometer.getInformativity(validObjIndex, validSpecification);
				if (Double.isNaN(informativity))
					whenValidThenOk = false;
			}
			catch (Exception e) {
				whenValidThenOk = false;
			}
			boolean exceptionThrownWithInvalidSpec = false;
			try {
				infometer.getInformativity(validObjIndex, invalidSpecification);
			}
			catch (PropertyTargetingException e) {
				exceptionThrownWithInvalidSpec = true;
			}
			if (!exceptionThrownWithInvalidSpec)
				exceptionAlwaysThrownWhenInvalidSpecification = false;
		}
		assertTrue(exceptionAlwaysThrownWhenInvalidSpecification && whenValidThenOk);
	}

}
