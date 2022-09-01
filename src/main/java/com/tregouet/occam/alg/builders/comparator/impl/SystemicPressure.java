package com.tregouet.occam.alg.builders.comparator.impl;

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

import com.tregouet.occam.alg.builders.comparator.ComparatorSetter;
import com.tregouet.occam.data.modules.comparison.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.impl.Concept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.structures.representations.classifications.concepts.impl.IsA;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.Functions;

public class SystemicPressure extends AComparatorSetter implements ComparatorSetter {

	public SystemicPressure() {
		super();
	}

	@Override
	protected Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDichotomies() {
		Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies = new HashMap<>();
		List<IClassification> dichotomisticClassifications = buildDichotomisticClassifications(conceptLattice);
		for (IClassification classification : dichotomisticClassifications) {
			IRepresentation representation = ComparatorSetter.representationBuilder().apply(classification);
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
		Map<UnorderedPair<Integer, Integer>, IRepresentation> diffRepresentations = new HashMap<>();
		Map<Integer, IConcept> particularID2Particular = conceptLattice.getParticularID2Particular();
		List<Integer> particularIDs = new ArrayList<>(particularID2Particular.keySet());
		for (Integer i = 0 ; i < particularIDs.size() - 1 ; i++) {
			for (Integer j = i + 1 ; j < particularIDs.size() ; j++) {
				InvertedTree<IConcept, IIsA> diffTree =
						setUpClassificationTree(particularIDs.get(i), particularIDs.get(j), conceptLattice);
				IClassification diffClassification = ComparatorSetter.classificationBuilder().apply(diffTree, particularID2Particular);
				IRepresentation diffRepresentation = ComparatorSetter.representationBuilder().apply(diffClassification);
				UnorderedPair<Integer, Integer> pairIDs = UnorderedPair.of(particularIDs.get(i), particularIDs.get(j));
				diffRepresentations.put(pairIDs, diffRepresentation);
			}
		}
		return diffRepresentations;
	}

	@Override
	protected ISimilarityMetrics buildSimilarityMetrics() {
		return ComparatorSetter.similarityMetricsBuilder().apply(conceptLattice, dichotomies, differences);
	}

	private static List<IClassification> buildDichotomisticClassifications(IConceptLattice conceptLattice) {
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
			Set<Integer> pairIDs = pairs.get(l);
			IConcept pairSupremum = getSupremum(pairIDs, conceptLattice);
			IConcept pairGenus = buildRestrictedConcept(pairSupremum, pairIDs, pairSupremum.type() == ConceptType.TRUISM);
			Set<Integer> complementIDs = complements.get(l);
			IConcept complementSupremum = getSupremum(complementIDs, conceptLattice);
			IConcept complementGenus =
					buildRestrictedConcept(complementSupremum, complementIDs, complementSupremum.type() == ConceptType.TRUISM);
			List<IConcept> currentTopoOrder = Arrays.asList(new IConcept[] {pairGenus, complementGenus, truism, ontologicalCommitment});
			DirectedAcyclicGraph<IConcept, IIsA> currentDichotomyGraph = new DirectedAcyclicGraph<>(null, IsA::new, false);
			Graphs.addAllVertices(currentDichotomyGraph, currentTopoOrder);
			currentDichotomyGraph.addEdge(truism, ontologicalCommitment);
			currentDichotomyGraph.addEdge(pairGenus, truism);
			currentDichotomyGraph.addEdge(complementGenus, truism);
			Set<IConcept> alternative = new HashSet<>(Arrays.asList(new IConcept[] {pairGenus, complementGenus}));
			InvertedTree<IConcept, IIsA> dichotomisticTree =
					new InvertedTree<>(currentDichotomyGraph, ontologicalCommitment, alternative, currentTopoOrder);
			IClassification dichotomy = ComparatorSetter.classificationBuilder().apply(dichotomisticTree, particularID2Particular);
			dichotomies.add(dichotomy);
		}
		return dichotomies;
	}

	private static IConcept buildRestrictedConcept(IConcept concept, Set<Integer> restrictedExtentIDs, boolean newID) {
		IConcept restrictedCopy;
		Integer restrConceptID = (newID ? null : concept.iD());
		int nbOfDenotations = concept.getDenotations().size();
		IConstruct[] constructs = new IConstruct[nbOfDenotations];
		boolean[] redundant = new boolean[nbOfDenotations];
		int idx = 0;
		for (IDenotation denotation : concept.getDenotations()) {
			constructs[idx] = denotation.copy();
			redundant[idx] = denotation.isRedundant();
			idx++;
		}
		restrictedCopy = new Concept(constructs, redundant, restrictedExtentIDs, restrConceptID);
		restrictedCopy.setType(concept.type());
		return restrictedCopy;
	}

	private static IConcept getSupremum(Set<Integer> conceptIDs, IConceptLattice conceptLattice) {
		Set<IConcept> concepts = new HashSet<>();
		for (Integer conceptID : conceptIDs) {
			concepts.add(conceptLattice.getConceptWithSpecifiedID(conceptID));
		}
		return conceptLattice.getLeastCommonSuperordinate(concepts);
	}

	private static InvertedTree<IConcept, IIsA> setUpClassificationTree(
			int particularID1, int particularID2, IConceptLattice lattice) {
		Set<Integer> extentIDs = new HashSet<>();
		extentIDs.add(particularID1);
		extentIDs.add(particularID2);
		IConcept particular1 = null;
		IConcept particular2 = null;
		//retrieve vertices
		for (IConcept particular : lattice.getParticulars()) {
			if (particular.iD() == particularID1)
				particular1 = particular;
			else if (particular.iD() == particularID2)
				particular2 = particular;
		}
		IConcept ontologicalCommitment = new Everything(extentIDs);
		IConcept genus = buildRestrictedConcept(
				Functions.supremum(lattice.getOntologicalUpperSemilattice(), particular1, particular2),
				extentIDs, false) ;
		//build dag
		DirectedAcyclicGraph<IConcept, IIsA> dag = new DirectedAcyclicGraph<>(null, IsA::new, false);
		dag.addVertex(particular1);
		dag.addVertex(particular2);
		dag.addVertex(genus);
		dag.addVertex(ontologicalCommitment);
		dag.addEdge(particular1, genus);
		dag.addEdge(particular2, genus);
		dag.addEdge(genus, ontologicalCommitment);
		//build topo order
		List<IConcept> topoOrder = new ArrayList<>(5);
		topoOrder.add(particular1);
		topoOrder.add(particular2);
		topoOrder.add(genus);
		topoOrder.add(ontologicalCommitment);
		return new InvertedTree<>(
				dag, ontologicalCommitment,
				new HashSet<>(Arrays.asList(new IConcept[] {particular1, particular2})),
				topoOrder);
	}

}
