package com.tregouet.occam.data.constructs;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	List<IConstruct> getConstructs();
	
	String getID();
	
	Iterator<IConstruct> getIteratorOverConstructs();
	
	List<ISymbolSeq> toSymbolSeqs();	

}