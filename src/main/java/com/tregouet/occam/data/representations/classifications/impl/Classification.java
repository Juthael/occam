package com.tregouet.occam.data.representations.classifications.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.utils.ClassificationNormalizer;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class Classification implements IClassification {

	private final InvertedTree<IConcept, IIsA> graph;
	private final Map<Integer, IConcept> iD2Concept = new HashMap<>();
	private final Map<Integer, List<Integer>> conceptID2ExtentIDs;
	private final Map<Integer, Integer> speciesID2GenusID;
	private final Map<Integer, IConcept> particularID2Particular;
	protected NormalizedClassification normalizedClassification;
	private boolean fullyDeveloped;
	private boolean expansionRestricted = false;

	public Classification(InvertedTree<IConcept, IIsA> graph,
			Map<Integer, List<Integer>> conceptID2ExtentIDs, Map<Integer, Integer> speciesID2GenusID,
			Map<Integer, IConcept> particularID2Particular, boolean fullyDeveloped) {
		this.graph = graph;
		for (IConcept concept : graph.vertexSet())
			iD2Concept.put(concept.iD(), concept);
		this.conceptID2ExtentIDs = conceptID2ExtentIDs;
		this.speciesID2GenusID = speciesID2GenusID;
		this.particularID2Particular = particularID2Particular;
		this.normalizedClassification = null;
		this.fullyDeveloped = fullyDeveloped;
	}

	@Override
	public InvertedTree<IConcept, IIsA> asGraph() {
		return graph;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Classification other = (Classification) obj;
		return Objects.equals(graph, other.graph);
	}

	@Override
	public boolean expansionIsRestricted() {
		return expansionRestricted;
	}

	@Override
	public IConcept getConceptWithSpecifiedID(int iD) {
		return iD2Concept.get(iD);
	}

	@Override
	public List<IConcept> getExtent(int conceptID){
		List<IConcept> extent = new ArrayList<>();
		for (Integer particularID : getExtentIDs(conceptID))
			extent.add(particularID2Particular.get(particularID));
		return extent;
	}

	@Override
	public List<Integer> getExtentIDs(int conceptID) {
		return conceptID2ExtentIDs.get(conceptID);
	}

	@Override
	public IConcept getGenus(IConcept concept) {
		return iD2Concept.get(speciesID2GenusID.get(concept.iD()));
	}

	@Override
	public IConcept getGenus(int iD) {
		return iD2Concept.get(speciesID2GenusID.get(iD));
	}

	@Override
	public int getGenusID(int iD) {
		return speciesID2GenusID.get(iD);
	}

	@Override
	public Set<IConcept> getMostSpecificConcepts() {
		return graph.getLeaves();
	}

	@Override
	public Map<Integer, IConcept> getParticularID2Particular() {
		return particularID2Particular;
	}

	@Override
	public Set<Integer> getParticularIDs() {
		return particularID2Particular.keySet();
	}

	@Override
	public int hashCode() {
		return Objects.hash(graph);
	}

	@Override
	public boolean isExpandable() {
		return (!fullyDeveloped && !expansionRestricted);
	}

	@Override
	public boolean isFullyDeveloped() {
		return fullyDeveloped;
	}

	@Override
	public Map<Integer, List<Integer>> mapConceptID2ExtentIDs() {
		return conceptID2ExtentIDs;
	}

	@Override
	public Map<Integer, Integer> mapSpeciesID2GenusID() {
		return speciesID2GenusID;
	}

	@Override
	public NormalizedClassification normalized() {
		if (normalizedClassification == null)
			normalizedClassification = ClassificationNormalizer.normalize(this);
		return normalizedClassification;
	}

	@Override
	public void restrictFurtherExpansion() {
		expansionRestricted = true;
	}

}
