package com.tregouet.occam.data.abstract_machines.automatons.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TransitionFunctionValidatorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes1Obj;
	private IDenotationSets denotationSets;
	private DirectedAcyclicGraph<IDenotation, IEdgeProduction> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private Tree<IDenotationSet, IIsA> treeOfDenotationSets;
	private DirectedAcyclicGraph<IDenotation, IEdgeProduction> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IEdgeProduction> constrTreeSupplier;
	private Tree<IDenotation, IEdgeProduction> constrTree;
	private TreeSet<IAutomaton> automatons;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES2);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		denotationSets = new DenotationSets(shapes1Obj);
		List<IEdgeProduction> edgeProductions = new ProductionBuilder(denotationSets).getProductions();
		edgeProductions.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		while (denotationSetsTreeSupplier.hasNext()) {
			treeOfDenotationSets = denotationSetsTreeSupplier.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton(treeOfDenotationSets, constrTree);
				automatons.add(automaton);
			}
		}
	}
	
	private boolean containsSomeUnreacheableState(IAutomaton tF) {
		return !new ConnectivityInspector<>(tF.getFiniteAutomatonGraph()).isConnected();
	}

}
