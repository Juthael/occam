package com.tregouet.occam.data.abstract_machines.automatons.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;

import com.tregouet.occam.alg.builders.representations.concept_trees.IConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.productions.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.transition_function_gen_dep.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.logical_structures.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TransitionFunctionValidatorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes1Obj;
	private IConceptLattice conceptLattice;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeBuilder conceptTreeBuilder;
	private Tree<IConcept, IIsA> treeOfDenotationSets;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> constrTreeSupplier;
	private Tree<IDenotation, IStronglyContextualized> constrTree;
	private TreeSet<IAutomaton> automatons;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes1Obj = GenericFileReader.getContextObjects(SHAPES2);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		automatons = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		conceptLattice = new ConceptLattice(shapes1Obj);
		List<IStronglyContextualized> stronglyContextualizeds = new IfIsAThenBuildProductions(conceptLattice).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		while (conceptTreeBuilder.hasNext()) {
			treeOfDenotationSets = conceptTreeBuilder.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(treeOfDenotationSets, denotations);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton_dep(treeOfDenotationSets, constrTree);
				automatons.add(automaton);
			}
		}
	}
	
	private boolean containsSomeUnreacheableState(IAutomaton tF) {
		return !new ConnectivityInspector<>(tF.getFiniteAutomatonGraph()).isConnected();
	}

}
