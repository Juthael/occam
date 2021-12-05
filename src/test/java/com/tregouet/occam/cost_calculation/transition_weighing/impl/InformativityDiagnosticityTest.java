package com.tregouet.occam.cost_calculation.transition_weighing.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.cost_calculation.property_weighing.IPropertyWeigher;
import com.tregouet.occam.data.concepts.IClassificationTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.exceptions.PropertyTargetingException;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.impl.TransitionFunction;
import com.tregouet.occam.transition_function.impl.TransitionFunctionSupplier;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class InformativityDiagnosticityTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static final PropertyWeighingStrategy PROP_WHEIGHING_STRATEGY = 
			PropertyWeighingStrategy.INFORMATIVITY_DIAGNOSTIVITY;
	private static final SimilarityCalculationStrategy SIM_CALCULATION_STRATEGY = 
			SimilarityCalculationStrategy.CONTRAST_MODEL;
	private List<IContextObject> shapes2Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IClassificationTreeSupplier classificationTreeSupplier;
	private Tree<IConcept, IsA> catTree;
	private DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> constrTreeSupplier;
	private Map<ITransitionFunction, IPropertyWeigher> transFuncToInfometer = new HashMap<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		concepts = new Concepts(shapes2Obj);
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
				Tree<IIntentAttribute, IProduction> constrTree = constrTreeSupplier.next();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(shapes2Obj, concepts.getSingletonConcept(), catTree, constrTree, 
								PROP_WHEIGHING_STRATEGY, SIM_CALCULATION_STRATEGY);
				/*
				visualize("2108140757");
				*/
				transFuncToInfometer.put(transitionFunction, transitionFunction.getInfometer());
			}
		}	
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
			IPropertyWeigher infometer = tF.getInfometer();
			for (IOperator prop : properties) {
				try {
					double informativity = infometer.getPropertyWeight(prop);
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
		int invalidObjIndex = shapes2Obj.size();
		for (ITransitionFunction tF : transFuncToInfometer.keySet()) {
			IPropertyWeigher infometer = transFuncToInfometer.get(tF);
			try {
				double informativity = infometer.getPropertyWeight(validObjIndex, propertySpecification);
				if (Double.isNaN(informativity))
					propSpecIsValid = false;
			}
			catch (Exception e) {
				propSpecIsValid = false;
			}
			if (propSpecIsValid) {
				boolean exceptionThrown = false;
				try {
					infometer.getPropertyWeight(invalidObjIndex, propertySpecification);
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
			IPropertyWeigher infometer = transFuncToInfometer.get(tF);
			try {
				double informativity = infometer.getPropertyWeight(validObjIndex, validSpecification);
				if (Double.isNaN(informativity))
					whenValidThenOk = false;
			}
			catch (Exception e) {
				whenValidThenOk = false;
			}
			boolean exceptionThrownWithInvalidSpec = false;
			try {
				infometer.getPropertyWeight(validObjIndex, invalidSpecification);
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
