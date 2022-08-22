package com.tregouet.occam.alg.builders.similarity_assessor.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.util.UnorderedPair;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.similarity_assessor.SimAssessorSetter;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.classifications.concepts.impl.Concept;
import com.tregouet.occam.data.representations.classifications.concepts.impl.IsA;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.tree_finder.data.InvertedTree;

public class SystemicPressure extends ASimAssessorSetter implements SimAssessorSetter {

	public SystemicPressure(List<IContextObject> context) {
		super(context);
	}

	@Override
	protected Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDichotomies() {
		Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies = new HashMap<>();
		List<IClassification> dichotomisticClassifications = buildDichotomisticClassifications();
		for (IClassification classification : dichotomisticClassifications) {
			IRepresentation representation = SimAssessorSetter.representationBuilder().apply(classification);
			Set<IConcept> thisOrThat = classification.getMostSpecificConcepts();
			for (IConcept concept : thisOrThat) {
				List<Integer> extentIDs = classification.getExtentIDs(concept.iD());
				if (extentIDs.size() == 2) {
					UnorderedPair<Integer, Integer> comparedPair = UnorderedPair.of(extentIDs.get(0), extentIDs.get(1));
					dichotomies.put(comparedPair, representation);
				}
			}
		}
		return dichotomies;
	}

	@Override
	protected Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDifferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISimilarityMetrics buildSimilarityMetrics() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<IClassification> buildDichotomisticClassifications() {
		List<IClassification> dichotomies = new ArrayList<>();
		IConcept ontologicalCommitment = conceptLattice.getOntologicalCommitment();
		IConcept truism = conceptLattice.getTruism();
		Map<Integer, IConcept> particularID2Particular = conceptLattice.getParticularID2Particular();
		List<Integer> particularIDs = new ArrayList<>(particularID2Particular.keySet());
		List<Set<Integer>> pairs = new ArrayList<>();
		List<Set<Integer>> complements = new ArrayList<>();
		for (int i = 0 ; i < particularIDs.size() - 1 ; i++) {
			for (int j = i + 1 ; j < particularIDs.size() ; j++) {
				Set<Integer> pair = new HashSet<>();
				Set<Integer> complement = new HashSet<>();
				for (int k = 0 ; k < particularIDs.size() ; k++) {
					if (k == i || k == j)
						pair.add(particularIDs.get(k));
					else complement.add(particularIDs.get(k));
				}
				if (particularIDs.size() != 4 || !complements.contains(pair)) {
					pairs.add(pair);
					complements.add(complement);	
				}
			}
		}
		for (int l = 0 ; l < pairs.size() ; l++) {
			IConcept pairGenus = getGenus(pairs.get(l));
			if (pairGenus.equals(truism))
				pairGenus = new Concept(getSetOfConstructs(truism), pairs.get(l));
			IConcept complementGenus = getGenus(complements.get(l));
			if (complementGenus.equals(truism))
				complementGenus = new Concept(getSetOfConstructs(truism), complements.get(l));
			List<IConcept> currentTopoOrder = Arrays.asList(new IConcept[] {pairGenus, complementGenus, truism, ontologicalCommitment});
			DirectedAcyclicGraph<IConcept, IIsA> currentDichotomyGraph = new DirectedAcyclicGraph<>(null, IsA::new, false);
			Graphs.addAllVertices(currentDichotomyGraph, currentTopoOrder);
			currentDichotomyGraph.addEdge(truism, ontologicalCommitment);
			currentDichotomyGraph.addEdge(pairGenus, truism);
			currentDichotomyGraph.addEdge(complementGenus, truism);
			Set<IConcept> alternative = new HashSet<>(Arrays.asList(new IConcept[] {pairGenus, complementGenus}));
			InvertedTree<IConcept, IIsA> dichotomisticTree = 
					new InvertedTree<IConcept, IIsA>(currentDichotomyGraph, ontologicalCommitment, alternative, currentTopoOrder);
			IClassification dichotomy = SimAssessorSetter.classificationBuilder().apply(dichotomisticTree, particularID2Particular);
			dichotomies.add(dichotomy);
		}
		return dichotomies;
	}
	
	private static Set<IConstruct> getSetOfConstructs(IConcept concept){
		Set<IConstruct> constructs = new HashSet<>();
		for (IDenotation denotation : concept.getDenotations()) {
			constructs.add(denotation.copy());
		}
		return constructs;
	}
	
	private IConcept getGenus(Set<Integer> conceptIDs) {
		Set<IConcept> concepts = new HashSet<>();
		for (Integer conceptID : conceptIDs) {
			concepts.add(conceptLattice.getConceptWithSpecifiedID(conceptID));
		}
		return conceptLattice.getLeastCommonSuperordinate(concepts);
	}
	
	private static DirectedAcyclicGraph<IConcept, IIsA> stem(IConceptLattice conceptLattice) {
		DirectedAcyclicGraph<IConcept, IIsA> initialGraph = new DirectedAcyclicGraph<>(null, IsA::new, false);
		IConcept ontologicalCommitment = conceptLattice.getOntologicalCommitment();
		IConcept truism = conceptLattice.getTruism();
		IIsA initialEdge = conceptLattice.getOntologicalUpperSemilattice().getEdge(truism, ontologicalCommitment);
		initialGraph.addVertex(truism);
		initialGraph.addVertex(ontologicalCommitment);
		initialGraph.addEdge(truism, ontologicalCommitment, initialEdge);
		return initialGraph;
	}
	
	private static DirectedAcyclicGraph<IConcept, IIsA> clone(DirectedAcyclicGraph<IConcept, IIsA> graph) {
		DirectedAcyclicGraph<IConcept, IIsA> clone = new DirectedAcyclicGraph<>(null, IsA::new, false);
		for (IConcept concept : graph.vertexSet())
			clone.addVertex(concept);
		for (IIsA edge : graph.edgeSet())
			clone.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
		return clone;
	}

}
