package com.tregouet.occam.data.structures.representations.classifications.concepts.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class Particular implements IContextObject {

	private static int nextID = IContextObject.OBJ_FIRST_ID;
	private final List<IConstruct> constructs = new ArrayList<>();
	private final int iD;
	private final String name;

	public Particular(List<List<String>> constructsAsLists) {
		iD = nextID++;
		for (List<String> constructAsList : constructsAsLists)
			constructs.add(new Construct(constructAsList.toArray(new String[constructAsList.size()])));
		name = null;
	}

	public Particular(List<List<String>> constructsAsLists, String name) {
		iD = nextID++;
		for (List<String> constructAsList : constructsAsLists)
			constructs.add(new Construct(constructAsList.toArray(new String[constructAsList.size()])));
		this.name = name;
	}

	@Override
	public int compareTo(IContextObject o) {
		return this.iD - o.iD();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Particular other = (Particular) obj;
		if (iD != other.iD)
			return false;
		return true;
	}

	@Override
	public List<IConstruct> getConstructs() {
		return constructs;
	}

	@Override
	public Iterator<IConstruct> getIteratorOverConstructs() {
		return constructs.iterator();
	}

	@Override
	public String getName() {
		if (name == null)
			return Integer.toString(iD);
		else
			return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + iD;
		return result;
	}

	@Override
	public int iD() {
		return iD;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (IConstruct construct : constructs)
			sB.append(construct.toString() + System.lineSeparator());
		return sB.toString();
	}

	@Override
	public List<ISymbolSeq> toSymbolSeqs() {
		List<ISymbolSeq> symbolSeqs = new ArrayList<>();
		for (IConstruct construct : constructs)
			symbolSeqs.add(new SymbolSeq(construct.toListOfStringsWithPlaceholders()));
		return symbolSeqs;
	}

	public static void initializeIDGenerator() {
		nextID = IContextObject.OBJ_FIRST_ID;
	}

}
