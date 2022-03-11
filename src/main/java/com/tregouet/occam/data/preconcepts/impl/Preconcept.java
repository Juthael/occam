package com.tregouet.occam.data.preconcepts.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.preconcepts_gen.impl.RedundantDenotMarker;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class Preconcept implements IPreconcept {

	protected static int nextID = 100;
	
	private final Set<IDenotation> denotations = new HashSet<>();
	private final Set<IContextObject> extent;
	private final int iD;
	private int type;
	
	public Preconcept(Set<IConstruct> denotatingConstructs, Set<IContextObject> extent) {
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		if (extent.size() == 1)
			iD = extent.iterator().next().getID();
		else iD = nextID++;
	}
	
	public Preconcept(Set<IConstruct> denotatingConstructs, Set<IContextObject> extent, int iD) {
		for (IConstruct construct : denotatingConstructs)
			this.denotations.add(new Denotation(construct, this));
		this.extent = Collections.unmodifiableSet(extent);
		this.iD = iD;
	}

	@Override
	public IPreconcept complementThisWith(IPreconcept complementing) {
		return new ComplementaryPreconcept(this, complementing);
	}
	
	@Override
	public IPreconcept getComplemented() {
		return null;
	}	

	@Override
	public Set<IContextObject> getExtent() {
		return extent;
	}

	@Override
	public int getID() {
		return iD;
	}

	@Override
	public Set<IDenotation> getDenotations() {
		return denotations;
	}

	@Override
	public boolean isComplementary() {
		return false;
	}

	@Override
	public String toString() {
		if (type == IPreconcept.ABSURDITY)
			return "ABSURDITY";
		StringBuilder sB = new StringBuilder();
		sB.append(Integer.toString(iD));
		String newLine = System.lineSeparator();
		sB.append(newLine);
		Iterator<IDenotation> iterator = denotations.iterator();
		while (iterator.hasNext()) {
			sB.append(iterator.next().toString());
			if (iterator.hasNext())
				sB.append(newLine);
		}
		
		return sB.toString();
	}	
	
	/**
	 * If many attributes meet the constraint, returns the first found. 
	 * @throws PropertyTargetingException 
	 */
	@Override
	public IDenotation getMatchingDenotation(List<String> constraintAsStrings) throws IOException {
		IDenotation matchingDenotation = null;
		IConstruct constraintAsConstruct = 
				new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		Iterator<IDenotation> denotationIte = getDenotations().iterator();
		while (denotationIte.hasNext()) {
			IDenotation currDenotation = denotationIte.next();
			if (currDenotation.meets(constraintAsConstruct)) {
				if (matchingDenotation == null)
					matchingDenotation = currDenotation;
				else throw new IOException("AbstractDenotationSet.getMatchingDenotation(List<String>) : "
						+ "the constraint is not specific enough to target a single attribute.");
			}
		}
		return matchingDenotation;
	}	
	
	@Override
	public boolean meets(IConstruct constraint) {
		return getDenotations().stream().anyMatch(a -> a.meets(constraint));
	}

	@Override
	public boolean meets(List<String> constraintAsStrings) {
		IConstruct constraint = new Construct(constraintAsStrings.toArray(new String[constraintAsStrings.size()]));
		return meets(constraint);
	}	
	
	@Override
	public int type() {
		return type;
	}	
	
	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public IPreconcept buildComplementOfThis(Set<IPreconcept> complementMinimalLowerBounds, IPreconcept supremum) {
		Set<IContextObject> complementExtent = new HashSet<>();
		for (IPreconcept rebutterMinLowerBound : complementMinimalLowerBounds)
			complementExtent.addAll(rebutterMinLowerBound.getExtent());
		return new ComplementaryPreconcept(this, supremum, complementExtent);
	}

	@Override
	public void markRedundantDenotations() {
		RedundantDenotMarker.of(this);
	}

	@Override
	public Set<IDenotation> getRedundantDenotations() {
		Set<IDenotation> redundantDenotations = new HashSet<>();
		for (IDenotation denotation : denotations) {
			if (denotation.isRedundant())
				redundantDenotations.add(denotation);
		}
		return redundantDenotations;
	}

}
