package com.tregouet.occam.alg.scoring.scores.similarity.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.concepts_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class DynamicFramingTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeSupplier conceptTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_reduced_denotations;
	private IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier;
	private Tree<IDenotation, IStronglyContextualized> denotationTree;
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
		List<IStronglyContextualized> stronglyContextualizeds = new ProductionBuilder(concepts).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeSupplier = concepts.getConceptTreeSupplier();
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> currTreeOfDenotationSets  = conceptTreeSupplier.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currTreeOfDenotationSets, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton(currTreeOfDenotationSets, denotationTree);
				automatons.add(automaton);
			}
		}
	}

	@Test
	public void whenAsymmetricalSimilarityRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		ISimilarityScorer scorer = 
				SimilarityScorerFactory.INSTANCE.apply(SimilarityScoringStrategy.DYNAMIC_FRAMING);
		for (IAutomaton tF : automatons) {
			scorer.input(tF);
			List<IConcept> objects = new ArrayList<>(tF.getTreeOfDenotationSets().getLeaves());
			int[] objectIDs = new int[objects.size()];
			for (int i = 0 ; i < objectIDs.length ; i++) {
				objectIDs[i] = objects.get(i).getID();
			}
			for (int j = 0 ; j < objectIDs.length ; j++) {
				for (int k = 0 ; k < objectIDs.length ; k++) {
					double similarity = scorer.howSimilarTo(objectIDs[j], objectIDs[k]);
					if (Double.isNaN(similarity))
						returned = false;
					nbOfTests++;
				}
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}
	
	@Test
	public void whenSymmetricalSimilarityRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		ISimilarityScorer scorer = 
				SimilarityScorerFactory.INSTANCE.apply(SimilarityScoringStrategy.DYNAMIC_FRAMING);
		for (IAutomaton tF : automatons) {
			scorer.input(tF);
			List<IConcept> objects = new ArrayList<>(tF.getTreeOfDenotationSets().getLeaves());
			int[] objectIDs = new int[objects.size()];
			for (int i = 0 ; i < objectIDs.length ; i++) {
				objectIDs[i] = objects.get(i).getID();
			}
			for (int j = 0 ; j < objectIDs.length - 1 ; j++) {
				for (int k = j+1 ; k < objectIDs.length ; k++) {
					double similarity = scorer.howSimilar(objectIDs[j], objectIDs[k]);
					if (Double.isNaN(similarity))
						returned = false;
					nbOfTests++;
				}
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}

}
