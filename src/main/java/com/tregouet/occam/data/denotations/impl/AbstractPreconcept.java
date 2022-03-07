package com.tregouet.occam.data.denotations.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public abstract class AbstractPreconcept implements IPreconcept {

	protected static int nextID = 100;
	
	protected int type;
	
	public AbstractPreconcept() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractPreconcept))
			return false;
		AbstractPreconcept other = (AbstractPreconcept) obj;
		if (getID() != other.getID())
			return false;
		return true;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getID();
		return result;
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
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int type() {
		return type;
	}

}
