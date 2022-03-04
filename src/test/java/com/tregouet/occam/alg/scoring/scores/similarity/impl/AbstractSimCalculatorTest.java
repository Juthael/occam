package com.tregouet.occam.alg.scoring.scores.similarity.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.Concepts;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class AbstractSimCalculatorTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	private DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> filtered_reduced_denotations;
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
			Tree<IConcept, IIsA> currTreeOfDenotationSets  = denotationSetsTreeSupplier.next();
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
	public void whenAsymmetricalSimilarityMatrixRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			for (IAutomaton tF : automatons) {
				ISimilarityScorer scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
				scorer.input(tF).setScore();
				double[][] matrix = null;
				try {
					matrix = scorer.getAsymmetricalSimilarityMatrix();
				}
				catch (Exception e) {
					returned = false;
				}
				if (matrix == null)
					returned = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}
	
	@Test
	public void whenCoherenceScoreRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			for (IAutomaton tF : automatons) {
				ISimilarityScorer scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
				scorer.input(tF).setScore();;
				Double coherenceScore = null;
				try {
					coherenceScore = scorer.getCoherenceScore();
				}
				catch (Exception e) {
					returned = false;
				}
				if (coherenceScore == null)
					returned = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}
	
	@Test
	public void whenConceptualCoherenceMapRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			for (IAutomaton tF : automatons) {
				ISimilarityScorer scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
				scorer.input(tF).setScore();
				Map<Integer, Double> coherenceMap = null;
				try {
					coherenceMap = scorer.getConceptualCoherenceMap();
				}
				catch (Exception e) {
					returned = false;
				}
				if (coherenceMap == null || coherenceMap.isEmpty())
					returned = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}
	
	@Test
	public void whenSimilarityMatrixRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			for (IAutomaton tF : automatons) {
				ISimilarityScorer scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
				scorer.input(tF).setScore();
				double[][] matrix = null;
				try {
					matrix = scorer.getSimilarityMatrix();
				}
				catch (Exception e) {
					returned = false;
					e.printStackTrace();
				}
				if (matrix == null)
					returned = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}
	
	@Test
	public void whenTypicalityArrayRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			for (IAutomaton tF : automatons) {
				ISimilarityScorer scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
				scorer.input(tF).setScore();
				double[] typicalityArray = null;
				try {
					typicalityArray = scorer.getTypicalityArray();
				}
				catch (Exception e) {
					returned = false;
				}
				if (typicalityArray == null || typicalityArray.length == 0)
					returned = false;
				nbOfTests++;
			}
		}
		if (nbOfTests == 0)
			returned = false;
		assertTrue(returned);
	}

}
