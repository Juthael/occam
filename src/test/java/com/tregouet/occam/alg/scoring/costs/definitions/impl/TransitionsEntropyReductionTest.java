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
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class TransitionsEntropyReductionTest {
	
	private static final Path SHAPES1 = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> shapes1Obj;
	private IDenotationSets denotationSets;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private Tree<IDenotationSet, IIsA> treeOfDenotationSets;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> filtered_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IBasicProductionAsEdge> denotationTreeSupplier;
	private Tree<IDenotation, IBasicProductionAsEdge> denotationTree;
	private TreeSet<IAutomaton> automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES1);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		denotationSets = new DenotationSets(shapes1Obj);
		List<IBasicProductionAsEdge> basicProductionAsEdges = new ProductionBuilder(denotationSets).getProductions();
		basicProductionAsEdges.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		while (denotationSetsTreeSupplier.hasNext()) {
			treeOfDenotationSets = denotationSetsTreeSupplier.next();
			filtered_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.next();
				IAutomaton automaton = 
						new Automaton(treeOfDenotationSets, denotationTree);
				automatons.add(automaton);
			}
		}
	}

	@Test
	public void whenDefinitionCostsRequestedThenReturned() {
		boolean asExpected = true;
		int nbOfTests = 0;
		IDefinitionCoster coster = 
				DefinitionCosterFactory.INSTANCE.apply(DefinitionCostingStrategy.TRANSITIONS_ENTROPY_REDUCTION);
		for (IAutomaton automaton : automatons) {
			coster.setCosterParameters(automaton);
			for (IGenusDifferentiaDefinition definition : automaton.getPorphyrianTree().edgeSet()) {
				Double definitionCost = null;
				try {
					coster.input(definition).setCost();
					definitionCost = definition.getCost();
				}
				catch (Exception e) {
					asExpected = false;
				}
				if (definitionCost == null)
					asExpected = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			asExpected = false;
		assertTrue(asExpected);
	}

}
