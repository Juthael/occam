package com.tregouet.occam.alg.displayers.formatters.descriptions.genus.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.displayers.formatters.descriptions.genus.GenusFormatter;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;

public class IdThenExtent implements GenusFormatter {

	private Map<Integer, List<Integer>> conceptID2ExtentIDs = null;

	public IdThenExtent() {
	}

	@Override
	public String apply(Integer conceptID) {
		if (conceptID2ExtentIDs == null)
			return conceptID.toString();
		if (conceptID == IConcept.ONTOLOGICAL_COMMITMENT_ID)
			return conceptID.toString() + " = Ω";
		List<Integer> conceptExtent = conceptID2ExtentIDs.get(conceptID);
		if (conceptExtent.size() == 1)
			return conceptID.toString();
		StringBuilder sB = new StringBuilder();
		sB.append(conceptID.toString() + " = {");
		Iterator<Integer> extIte = conceptExtent.iterator();
		while (extIte.hasNext()) {
			sB.append(extIte.next().toString());
			if (extIte.hasNext()) {
				sB.append(", ");
			}
		}
		sB.append("}");
		return sB.toString();
	}

	@Override
	public GenusFormatter setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs) {
		this.conceptID2ExtentIDs = conceptID2ExtentIDs;
		return this;
	}

}
