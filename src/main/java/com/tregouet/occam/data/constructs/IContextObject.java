package com.tregouet.occam.data.constructs;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public interface IContextObject {
	
	List<IConstruct> getConstructs();
	
	String getID();
	
	Iterator<IConstruct> getIteratorOnConstructs();
	
	List<ISymbolSeq> toSymbolSeqs();

}