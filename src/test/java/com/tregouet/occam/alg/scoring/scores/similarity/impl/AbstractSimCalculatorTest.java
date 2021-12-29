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

import com.tregouet.occam.alg.conceptual_structure_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring.scores.similarity.SimilarityScoringStrategy;
import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.impl.TransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class AbstractSimCalculatorTest {
	
	private static final Path SHAPES = Paths.get(".", "src", "test", "java", "files", "shapes1bis.txt");
	private static List<IContextObject> objects;
	private IConcepts concepts;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	private IConceptTreeSupplier conceptTreeSupplier;
	private DirectedAcyclicGraph<IIntentConstruct, IProduction> filtered_reduced_constructs;
	private IHierarchicalRestrictionFinder<IIntentConstruct, IProduction> constrTreeSupplier;
	private Tree<IIntentConstruct, IProduction> constrTree;
	private TreeSet<ITransitionFunction> transitionFunctions;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objects = GenericFileReader.getContextObjects(SHAPES);	
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		transitionFunctions = new TreeSet<>(ScoreThenCostTFComparator.INSTANCE);
		concepts = new Concepts(objects);
		List<IProduction> productions = new ProductionBuilder(concepts).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
		conceptTreeSupplier = concepts.getClassificationSupplier();
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> currConceptTree  = conceptTreeSupplier.next();
			filtered_reduced_constructs = 
					TransitionFunctionSupplier.getConstructGraphFilteredByConceptTree(
							currConceptTree, constructs);
			constrTreeSupplier = new RestrictorOpt<>(filtered_reduced_constructs, true);
			while (constrTreeSupplier.hasNext()) {
				constrTree = constrTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = 
						new TransitionFunction(currConceptTree, constrTree);
				transitionFunctions.add(transitionFunction);
			}
		}
	}

	@Test
	public void whenAsymmetricalSimilarityMatrixRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		ISimilarityScorer scorer;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
			for (ITransitionFunction tF : transitionFunctions) {
				scorer.input(tF);
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
		ISimilarityScorer scorer;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
			for (ITransitionFunction tF : transitionFunctions) {
				scorer.input(tF);
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
		ISimilarityScorer scorer;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
			for (ITransitionFunction tF : transitionFunctions) {
				scorer.input(tF);
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
		ISimilarityScorer scorer;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
			for (ITransitionFunction tF : transitionFunctions) {
				scorer.input(tF);
				double[][] matrix = null;
				try {
					matrix = scorer.getSimilarityMatrix();
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
	public void whenTypicalityArrayRequestedThenReturned() {
		boolean returned = true;
		int nbOfTests = 0;
		ISimilarityScorer scorer;
		for (SimilarityScoringStrategy strategy : SimilarityScoringStrategy.values()) {
			scorer = SimilarityScorerFactory.INSTANCE.apply(strategy);
			for (ITransitionFunction tF : transitionFunctions) {
				scorer.input(tF);
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
