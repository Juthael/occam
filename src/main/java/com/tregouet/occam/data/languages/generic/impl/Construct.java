package com.tregouet.occam.data.languages.generic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.tregouet.occam.data.languages.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.ITerminal;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Construct implements IConstruct {

	protected final List<ISymbol> prog;
	private int nbOfTerminals;
	
	public Construct(IConstruct construct) {
		prog = new ArrayList<>(construct.getListOfSymbols());
		nbOfTerminals = construct.getNbOfTerminals();
	}
	
	public Construct(List<ISymbol> prog) {
		this.prog = prog;
		nbOfTerminals = setNbOfTerminals();
	}
	
	public Construct(String[] progStrings) {
		prog = new ArrayList<ISymbol>();
		for (String symString : progStrings) {
			if (symString.equals(ISymbolSeq.PLACEHOLDER))
				prog.add(new Variable(AVariable.DEFERRED_NAMING));
			else prog.add(new Terminal(symString));
		}
		int nbOfTerminals = 0;
		for (ISymbol symbol : prog) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		this.nbOfTerminals = nbOfTerminals;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Construct other = (Construct) obj;
		if (prog == null) {
			if (other.prog != null)
				return false;
		} else if (!prog.equals(other.prog))
			return false;
		return true;
	}

	@Override
	public Iterator<ISymbol> getIteratorOverSymbols(){
		return prog.iterator();
	}
	
	@Override
	public List<ISymbol> getListOfSymbols(){
		return prog;
	}

	@Override
	public List<ITerminal> getListOfTerminals() {
		return prog.stream()
				.filter(d -> d instanceof ITerminal)
				.map(s -> (ITerminal) s)
				.collect(Collectors.toList()); 
	}
	
	@Override
	public int getNbOfTerminals() {
		return nbOfTerminals;
	}
	
	@Override
	public List<AVariable> getVariables() {
		List<AVariable> variables = new ArrayList<>();
		for (ISymbol symbol : prog) {
			if (symbol instanceof AVariable)
				variables.add((AVariable) symbol);
		}
		return variables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prog == null) ? 0 : prog.hashCode());
		return result;
	}

	@Override
	public boolean isAbstract() {
		boolean isAbstract = false;
		Iterator<ISymbol> ite = prog.iterator();
		while (!isAbstract && ite.hasNext())
			isAbstract = (ite.next() instanceof AVariable);
		return isAbstract;
	}
	
	@Override
	public boolean meets(IConstruct constraint) {
		List<ISymbol> constraintSymbols = constraint.getListOfSymbols();
		if (this.nbOfTerminals >= constraintSymbols.size()) {
			int constraintIdx = 0;
			for (int constructIdx = 0 ; constructIdx < prog.size() 
					&& constraintIdx < constraintSymbols.size() ; constructIdx++) {
				if (prog.get(constructIdx).equals(constraintSymbols.get(constraintIdx)))
					constraintIdx++;
			}
			if (constraintIdx == constraintSymbols.size())
				return true;
		}
		return false;
	}
	
	@Override
	public void nameVariables() {
		for (ISymbol symbol : prog) {
			if (symbol instanceof AVariable)
				((AVariable) symbol).setName();
		}
	}

	@Override
	public List<String> toListOfStringsWithPlaceholders(){
		List<String> list = new ArrayList<String>();
		for (ISymbol sym : prog) {
			if (sym instanceof AVariable)
				list.add(ISymbolSeq.PLACEHOLDER);
			else list.add(sym.toString());
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < prog.size() ; i++) {
			sB.append(prog.get(i));
			if (i < prog.size() - 1)
				sB.append(" ");
		}
		return sB.toString();
	}
	
	protected int setNbOfTerminals() {
		int nbOfTerminals = 0;
		for (ISymbol symbol : prog) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		return nbOfTerminals;
	}

}
