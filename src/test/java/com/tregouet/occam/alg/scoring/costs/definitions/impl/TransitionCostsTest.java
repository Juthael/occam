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

import com.tregouet.occam.alg.generators.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.occam.alg.generators.representations.transitions_gen.impl.ProductionSetBuilder;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.scoring_dep.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.definitions.impl.DefinitionCosterFactory;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.automata.machines.deprec.IGenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class TransitionCostsTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IPreconcepts preconcepts;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IPreconceptTreeSupplier preconceptTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier;
	private Tree<IDenotation, IStronglyContextualized> denotationTree;
	private TreeSet<IAutomaton> automatons;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		preconcepts = new Preconcepts(objects);
		List<IStronglyContextualized> stronglyContextualizeds = new ProductionSetBuilder(preconcepts).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		preconceptTreeSupplier = preconcepts.getConceptTreeSupplier();
		while (preconceptTreeSupplier.hasNext()) {
			Tree<IPreconcept, IIsA> currDenotationSetTree  = preconceptTreeSupplier.next();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currDenotationSetTree, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton_dep(currDenotationSetTree, denotationTree);
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
			Tree<IState, IGenusDifferentiaDefinition_dep> porphyrianTree = tF.getPorphyrianTree();
			for (IGenusDifferentiaDefinition_dep def : porphyrianTree.edgeSet()) {
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
