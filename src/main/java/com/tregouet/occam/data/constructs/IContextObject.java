package com.tregouet.occam.data.constructs;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject {
	
	public int hashCode();
	
	boolean equals(Object o);
	
	List<IConstruct> getConstructs();
	
	String getID();
	
	Iterator<IConstruct> getIteratorOverConstructs();
	
	List<ISymbolSeq> toSymbolSeqs();	

}