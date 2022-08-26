package com.tregouet.occam.alg.displayers.visualizers.descriptions.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionViz;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;

public abstract class ADescriptionViz implements DescriptionViz {
	
	protected Map<Integer, List<Integer>> conceptID2ExtentIDs = null;

	@Override
	abstract public String apply(IDescription description, String fileName);

	@Override
	abstract public DescriptionViz setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);
	
	protected String conceptIdToString(Integer conceptID) {
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

}
