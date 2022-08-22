package com.tregouet.occam.data.representations.classifications.concepts;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.representations.classifications.concepts.impl.Particular;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject extends Comparable<IContextObject> {

	public static final int OBJ_FIRST_ID = 1;

	@Override
	public int hashCode();

	@Override
	boolean equals(Object o);

	List<IConstruct> getConstructs();

	Iterator<IConstruct> getIteratorOverConstructs();

	String getName();

	int iD();

	@Override
	String toString();

	List<ISymbolSeq> toSymbolSeqs();

	public static void initializeIDGenerator() {
		Particular.initializeIDGenerator();
	}

}