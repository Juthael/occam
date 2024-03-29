package com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class Denotation extends Construct implements IDenotation {

	private final int conceptID;
	private boolean isRedundant = false;
	private final boolean isArbitraryLabel;

	public Denotation(IConstruct construct, int conceptID) {
		super(construct);
		this.conceptID = conceptID;
		List<ISymbol> list = construct.asList();
		if (list.size() == 1 && list.get(0).toString().contains(GenericFileReader.LABEL_DENOTATION_SYMBOL))
			isArbitraryLabel = true;
		else isArbitraryLabel = false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj) || (getClass() != obj.getClass()))
			return false;
		Denotation other = (Denotation) obj;
		return conceptID == other.conceptID;
	}

	@Override
	public int getConceptID() {
		return conceptID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(conceptID);
		return result;
	}

	@Override
	public boolean isArbitraryLabel() {
		return isArbitraryLabel;
	}

	@Override
	public boolean isRedundant() {
		return isRedundant;
	}

	@Override
	public void markAsRedundant() {
		isRedundant = true;
	}

}
