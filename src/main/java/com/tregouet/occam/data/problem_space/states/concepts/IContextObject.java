package com.tregouet.occam.data.problem_space.states.concepts;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.concepts.impl.Particular;
import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject extends Comparable<IContextObject> {
	
	public static final int OBJ_FIRST_ID = 1;

	@Override
	public int hashCode();

	@Override
	boolean equals(Object o);

	List<IConstruct> getConstructs();

	int iD();

	Iterator<IConstruct> getIteratorOverConstructs();

	String getName();

	@Override
	String toString();

	List<ISymbolSeq> toSymbolSeqs();
	
	public static void initializeIDGenerator() {
		Particular.initializeIDGenerator();
	}

}