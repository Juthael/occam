package com.tregouet.occam.data.languages.generic;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject extends Comparable<IContextObject> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	List<IConstruct> getConstructs();
	
	int getID();
	
	Iterator<IConstruct> getIteratorOverConstructs();
	
	String getName();
	
	List<ISymbolSeq> toSymbolSeqs();	

}