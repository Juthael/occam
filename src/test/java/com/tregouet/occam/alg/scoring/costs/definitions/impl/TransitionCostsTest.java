package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.scoring.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.Concepts;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class TransitionCostsTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IDenotation, IBasicProductionAsEdge> denotationTreeSupplier;
	private Tree<IDenotation, IBasicProductionAsEdge> denotationTree;
	private TreeSet<IAutomaton> automatons;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		concepts = new Concepts(objects);
		List<IBasicProductionAsEdge> basicProductionAsEdges = new ProductionBuilder(concepts).getProductions();
		basicProductionAsEdges.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		denotationSetsTreeSupplier = concepts.getDenotationSetsTreeSupplier();
		while (denotationSetsTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> currDenotationSetTree  = denotationSetsTreeSupplier.next();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currDenotationSetTree, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton(currDenotationSetTree, denotationTree);
				automatons.add(automaton);
			}
		}
	}

	@Test
	public void whenDefinitionCostRequestedThenReturned() {
		boolean costReturned = true;
		int nbOfTests = 0;
		IDefinitionCoster coster = 
				DefinitionCosterFactory.INSTANCE.apply(DefinitionCostingStrategy.TRANSITION_COSTS);
		for (IAutomaton tF : automatons) {
			Tree<IState, IGenusDifferentiaDefinition> porphyrianTree = tF.getPorphyrianTree();
			for (IGenusDifferentiaDefinition def : porphyrianTree.edgeSet()) {
				Double defCost = null;
				try {
					coster.input(def).setCost();
					defCost = def.getCost();
				}
				catch (Exception e) {
					costReturned = false;
				}
				if (defCost == null)
					costReturned = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			costReturned = false;
		assertTrue(costReturned);
	}

}
