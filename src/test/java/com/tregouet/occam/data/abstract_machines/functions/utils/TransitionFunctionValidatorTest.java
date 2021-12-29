package com.tregouet.occam.data.abstract_machines.functions.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.conceptual_structure_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.TransitionFunctionGraphType;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TransitionFunctionValidatorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes1Obj;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeSupplier conceptTreeSupplier;
	private Tree<IConcept, IIsA> conceptTree;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentConstruct, IProduction> constrTreeSupplier;
	private Tree<IIntentConstruct, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES2);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		transitionFunctions = new TreeSet<>();
		concepts = new Concepts(shapes1Obj);
		List<IProduction> productions = new ProductionBuilder(concepts).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeSupplier = concepts.getClassificationSupplier();
		while (conceptTreeSupplier.hasNext()) {
			conceptTree = conceptTreeSupplier.next();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByConceptTree(conceptTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(conceptTree, constrTree);
				transitionFunctions.add(transitionFunction);
			}
		}
	}
	
	private boolean containsSomeUnreacheableState(ITransitionFunction tF) {
		return !new ConnectivityInspector<>(tF.getFiniteAutomatonGraph()).isConnected();
	}

}
