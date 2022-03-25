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

import com.tregouet.occam.alg.builders.concepts.trees.IConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.productions.from_concepts.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.impl.SimilarityScorerFactory;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConceptLattice;
import com.tregouet.occam.data.concepts.impl.ConceptLattice;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class AbstractSimCalculatorTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConceptLattice conceptLattice;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeBuilder conceptTreeBuilder;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered_reduced_denotations;
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
		conceptLattice = new ConceptLattice(objects);
		List<IStronglyContextualized> stronglyContextualizeds = new IfIsAThenBuildProductions(conceptLattice).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		while (conceptTreeBuilder.hasNext()) {
			Tree<IConcept, IIsA> currTreeOfDenotationSets  = conceptTreeBuilder.next();
			filtered_reduced_denotations = 
					TransitionFunctionSupplier.getDenotationGraphFilteredByTreeOfDenotationSets(
							currTreeOfDenotationSets, denotations);
			denotationTreeSupplier = new RestrictorOpt<>(filtered_reduced_denotations, true);
			while (denotationTreeSupplier.hasNext()) {
				denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = 
						new Automaton_dep(currTreeOfDenotationSets, denotationTree);
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
