package com.tregouet.occam.alg.builders.pb_space.concept_lattices.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.utils.MarkRedundantDenotations;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Concept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.ConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.IsA;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public class GaloisConnection implements ConceptLatticeBuilder {

	private List<IContextObject> objects = null;
	private DirectedAcyclicGraph<IConcept, IIsA> lattice = null;
	private InvertedUpperSemilattice<IConcept, IIsA> invertedUpperSemilattice = null;
	private IConcept ontologicalCommitment = null;
	private List<IConcept> topologicalOrder = null;
	private IConcept truism = null;
	private List<IConcept> particulars = null;
	private List<Integer> particularIDs = null;
	private IConcept absurdity = null;

	public GaloisConnection() {
	}

	@Override
	public IConceptLattice apply(Collection<IContextObject> objects) {
		AVariable.resetVarNaming();
		this.objects = new ArrayList<>(objects);
		particulars = new ArrayList<>(Arrays.asList(new IConcept[objects.size()]));
		particularIDs = new ArrayList<>(Arrays.asList(new Integer[objects.size()]));
		lattice = new DirectedAcyclicGraph<>(null, IsA::new, false);
		buildLattice();
		IConcept truism = null;
		IConcept absurdity = null;
		for (IConcept concept : lattice.vertexSet()) {
			switch (concept.type()) {
			case TRUISM:
				truism = concept;
				break;
			case ABSURDITY:
				absurdity = concept;
				break;
			case PARTICULAR:
				int idx = getParticularIdx(concept);
				particulars.set(idx, concept);
				particularIDs.set(idx, concept.iD());
				break;
			default:
				break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = new Everything(new HashSet<>(particularIDs));
		@SuppressWarnings("unchecked")
		DirectedAcyclicGraph<IConcept, IIsA> upperSemilattice = (DirectedAcyclicGraph<IConcept, IIsA>) lattice.clone();
		upperSemilattice.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(upperSemilattice);
		topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(upperSemilattice).forEachRemaining(topologicalOrder::add);
		this.invertedUpperSemilattice = new InvertedUpperSemilattice<>(upperSemilattice, truism,
				new HashSet<>(particulars), topologicalOrder);
		this.invertedUpperSemilattice.addAsNewRoot(ontologicalCommitment, true);
		markRedundantDenotationsOfUSLConcepts();
		return output();
	}

	private Map<Set<IConstruct>, Set<Integer>> buildIntentToExtentRel() {
		Map<Set<IConstruct>, Set<Integer>> denotatingConstructsToExtents = new HashMap<>();
		Set<Set<IContextObject>> objectsPowerSet = buildObjectsPowerSet();
		for (Set<IContextObject> subset : objectsPowerSet) {
			Set<IConstruct> denotatingConstructs;
			if (subset.size() > 1)
				denotatingConstructs = ConceptLatticeBuilder.getDenotationBuilder().apply(subset);
			else if (subset.size() == 1)
				denotatingConstructs = new HashSet<>(new ArrayList<>(subset).get(0).getConstructs());
			else {
				denotatingConstructs = new HashSet<>();
				for (IContextObject obj : objects)
					denotatingConstructs.addAll(obj.getConstructs());
			}
			if (denotatingConstructsToExtents.containsKey(denotatingConstructs))
				denotatingConstructsToExtents.get(denotatingConstructs).addAll(getIDsOf(subset));
			else
				denotatingConstructsToExtents.put(denotatingConstructs, getIDsOf(subset));
		}
		denotatingConstructsToExtents = replaceEqualConstructsByUniqueInstanceAndNameVariables(
				denotatingConstructsToExtents);
		return denotatingConstructsToExtents;
	}

	private void buildLattice() {
		Map<Set<IConstruct>, Set<Integer>> denotatingConstructsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<Integer>> entry : denotatingConstructsToExtents.entrySet()) {
			IConcept concept = new Concept(entry.getKey(), entry.getValue());
			if (!concept.getMaxExtentIDs().isEmpty()) {
				if (concept.getMaxExtentIDs().size() == 1)
					concept.setType(ConceptType.PARTICULAR);
				else if (concept.getMaxExtentIDs().size() == objects.size()) {
					concept.setType(ConceptType.TRUISM);
				} else {
					concept.setType(ConceptType.UNIVERSAL);
				}
			} else {
				concept.setType(ConceptType.ABSURDITY);
			}
			lattice.addVertex(concept);
		}
		List<IConcept> denotSetList = new ArrayList<>(lattice.vertexSet());
		for (int i = 0; i < denotSetList.size() - 1; i++) {
			IConcept iDenotSet = denotSetList.get(i);
			for (int j = i + 1; j < denotSetList.size(); j++) {
				IConcept jDenotSet = denotSetList.get(j);
				if (iDenotSet.getMaxExtentIDs().containsAll(jDenotSet.getMaxExtentIDs()))
					lattice.addEdge(jDenotSet, iDenotSet);
				else if (jDenotSet.getMaxExtentIDs().containsAll(iDenotSet.getMaxExtentIDs()))
					lattice.addEdge(iDenotSet, jDenotSet);
			}
		}
		TransitiveReduction.INSTANCE.reduce(lattice);
	}

	private Set<Set<IContextObject>> buildObjectsPowerSet() {
		Set<Set<IContextObject>> powerSet = new HashSet<>();
		for (int i = 0; i < (1 << objects.size()); i++) {
			Set<IContextObject> subset = new HashSet<>();
			for (int j = 0; j < objects.size(); j++) {
				if (((1 << j) & i) > 0)
					subset.add(objects.get(j));
			}
			powerSet.add(subset);
		}
		return powerSet;
	}

	private void markRedundantDenotationsOfUSLConcepts() {
		for (IConcept concept : invertedUpperSemilattice) {
			MarkRedundantDenotations.of(concept);
		}
	}

	private IConceptLattice output() {
		return new ConceptLattice(objects, lattice, invertedUpperSemilattice, topologicalOrder, ontologicalCommitment,
				truism, particulars, absurdity);
	}

	private Map<Set<IConstruct>, Set<Integer>> replaceEqualConstructsByUniqueInstanceAndNameVariables(
			Map<Set<IConstruct>, Set<Integer>> denotatingConstructsToExtents) {
		// reset variable name provider
		AVariable.resetVarNaming();
		/*
		 * Build a map such that all equal constructs will return the same construct
		 * instance (that will therefore be the reference instance).
		 */
		int nbOfMappings = denotatingConstructsToExtents.size();
		Map<Set<IConstruct>, Set<Integer>> paramCopyWithRefInstancesAndNamedVars = new HashMap<>(nbOfMappings);
		Map<IConstruct, IConstruct> constructToRefInstance = new HashMap<>();
		for (Set<IConstruct> constructSet : denotatingConstructsToExtents.keySet()) {
			for (IConstruct construct : constructSet) {
				if (!constructToRefInstance.containsKey(construct)) {
					constructToRefInstance.put(construct, construct);
				}
			}
		}
		/*
		 * Build a copy of the parameter map with reference construct instances only in
		 * values, and real variable names in constructs instead of placeholders. Must
		 * use transitory collections not based on hash tables, since variable naming
		 * will modify hashcodes.
		 */
		List<List<IConstruct>> setsOfReferenceConstrInstances = new ArrayList<>();
		List<Set<Integer>> listOfExtents = new ArrayList<>();
		for (Entry<Set<IConstruct>, Set<Integer>> entry : denotatingConstructsToExtents.entrySet()) {
			List<IConstruct> withReferenceInstances = new ArrayList<>();
			for (IConstruct construct : entry.getKey()) {
				withReferenceInstances.add(constructToRefInstance.get(construct));
			}
			setsOfReferenceConstrInstances.add(withReferenceInstances);
			listOfExtents.add(entry.getValue());
		}
		// name variable, using side effects
		for (IConstruct refInstance : constructToRefInstance.values())
			refInstance.nameVariables();
		/*
		 * Populate the returned map with reference construct instances, whose variables
		 * now have a name.
		 */
		for (int i = 0; i < setsOfReferenceConstrInstances.size(); i++) {
			paramCopyWithRefInstancesAndNamedVars.put(new HashSet<>(setsOfReferenceConstrInstances.get(i)),
					listOfExtents.get(i));
		}
		return paramCopyWithRefInstancesAndNamedVars;
	}
	
	private int getParticularIdx(IConcept particular) {
		Set<IConstruct> particularConstructs = new HashSet<>();
		for (IDenotation denotation : particular.getDenotations())
			particularConstructs.add(new Construct(denotation.asList()));
		for (int i = 0 ; i < objects.size() ; i++) {
			IContextObject iObject = objects.get(i);
			Set<IConstruct> objectConstructs = new HashSet<>(iObject.getConstructs());
			if (particularConstructs.equals(objectConstructs))
				return i;
		}
		return -1; //never happens
	}
	
	private Set<Integer> getIDsOf(Set<IContextObject> objectSet) {
		Set<Integer> iDs = new HashSet<>();
		for (IContextObject object : objectSet) {
			iDs.add(object.iD());
		}
		return iDs;
	}

}
