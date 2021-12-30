package com.tregouet.occam.data.languages.generic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class ContextObject implements IContextObject {

	private static int nextID = 0;
	private final List<IConstruct> constructs = new ArrayList<IConstruct>();
	private final int iD;
	private final String name;
	
	public ContextObject(List<List<String>> constructsAsLists) {
		iD = nextID++;
		for (List<String> constructAsList : constructsAsLists)
			constructs.add(new Construct(constructAsList.toArray(new String[constructAsList.size()])));
		name = null;
	}
	
	public ContextObject(List<List<String>> constructsAsLists, String name) {
		iD = nextID++;
		for (List<String> constructAsList : constructsAsLists)
			constructs.add(new Construct(constructAsList.toArray(new String[constructAsList.size()])));
		this.name = name;
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContextObject other = (ContextObject) obj;
		if (iD != other.iD)
			return false;
		return true;
	}
	
	@Override
	public List<IConstruct> getConstructs() {
		return constructs;
	}
	
	@Override
	public int getID() {
		return iD;
	}

	@Override
	public Iterator<IConstruct> getIteratorOverConstructs() {
		return constructs.iterator();
	}
	
	@Override
	public String getName() {
		if (name == null)
			return Integer.toString(iD);
		else return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + iD;
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (IConstruct construct : constructs)
			sB.append(construct.toString() + System.lineSeparator());
		return sB.toString();
	}

	@Override
	public List<ISymbolSeq> toSymbolSeqs(){
		List<ISymbolSeq> symbolSeqs = new ArrayList<ISymbolSeq>();
		for (IConstruct construct : constructs)
			symbolSeqs.add(new SymbolSeq(construct.toListOfStringsWithPlaceholders()));
		return symbolSeqs;
	}

	@Override
	public int compareTo(IContextObject o) {
		return this.iD - o.getID();
	}

}
