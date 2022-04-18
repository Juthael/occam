package com.tregouet.occam.data.representations.concepts;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject extends Comparable<IContextObject> {

	@Override
	boolean equals(Object o);

	List<IConstruct> getConstructs();

	int getID();

	Iterator<IConstruct> getIteratorOverConstructs();

	String getName();

	@Override
	public int hashCode();

	@Override
	String toString();

	List<ISymbolSeq> toSymbolSeqs();

}