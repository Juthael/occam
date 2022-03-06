package com.tregouet.occam.data.languages.generic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.ITerminal;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Construct implements IConstruct {

	protected final List<ISymbol> symbols;
	private int nbOfTerminals;
	private Iterator<ISymbol> symbolIte = null;
	
	public Construct(IConstruct construct) {
		symbols = new ArrayList<>(construct.getListOfSymbols());
		nbOfTerminals = construct.getNbOfTerminals();
	}
	
	public Construct(List<ISymbol> prog) {
		this.symbols = prog;
		nbOfTerminals = setNbOfTerminals();
	}
	
	public Construct(String[] progStrings) {
		symbols = new ArrayList<ISymbol>();
		for (String symString : progStrings) {
			if (symString.equals(ISymbolSeq.PLACEHOLDER))
				symbols.add(new Variable(AVariable.DEFERRED_NAMING));
			else symbols.add(new Terminal(symString));
		}
		int nbOfTerminals = 0;
		for (ISymbol symbol : symbols) {
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
		if (symbols == null) {
			if (other.symbols != null)
				return false;
		} else if (!symbols.equals(other.symbols))
			return false;
		return true;
	}

	@Override
	public Iterator<ISymbol> getIteratorOverSymbols(){
		return symbols.iterator();
	}
	
	@Override
	public List<ISymbol> getListOfSymbols(){
		return symbols;
	}

	@Override
	public List<ITerminal> getListOfTerminals() {
		return symbols.stream()
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
		for (ISymbol symbol : symbols) {
			if (symbol instanceof AVariable)
				variables.add((AVariable) symbol);
		}
		return variables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbols == null) ? 0 : symbols.hashCode());
		return result;
	}

	@Override
	public boolean isAbstract() {
		boolean isAbstract = false;
		Iterator<ISymbol> ite = symbols.iterator();
		while (!isAbstract && ite.hasNext())
			isAbstract = (ite.next() instanceof AVariable);
		return isAbstract;
	}
	
	@Override
	public boolean meets(IConstruct constraint) {
		List<ISymbol> constraintSymbols = constraint.getListOfSymbols();
		if (this.nbOfTerminals >= constraintSymbols.size()) {
			int constraintIdx = 0;
			for (int constructIdx = 0 ; constructIdx < symbols.size() 
					&& constraintIdx < constraintSymbols.size() ; constructIdx++) {
				if (symbols.get(constructIdx).equals(constraintSymbols.get(constraintIdx)))
					constraintIdx++;
			}
			if (constraintIdx == constraintSymbols.size())
				return true;
		}
		return false;
	}
	
	@Override
	public void nameVariables() {
		for (ISymbol symbol : symbols) {
			if (symbol instanceof AVariable)
				((AVariable) symbol).setName();
		}
	}

	@Override
	public List<String> toListOfStringsWithPlaceholders(){
		List<String> list = new ArrayList<String>();
		for (ISymbol sym : symbols) {
			if (sym instanceof AVariable)
				list.add(ISymbolSeq.PLACEHOLDER);
			else list.add(sym.toString());
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < symbols.size() ; i++) {
			sB.append(symbols.get(i));
			if (i < symbols.size() - 1)
				sB.append(" ");
		}
		return sB.toString();
	}
	
	protected int setNbOfTerminals() {
		int nbOfTerminals = 0;
		for (ISymbol symbol : symbols) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		return nbOfTerminals;
	}

	@Override
	public boolean hasNext() {
		if (symbolIte == null)
			initializeSymbolIterator();
		return symbolIte.hasNext();
	}

	@Override
	public ISymbol next() {
		return symbolIte.next();
	}

	@Override
	public void initializeSymbolIterator() {
		symbolIte = symbols.iterator();
	}

	@Override
	public boolean appendSymbol(ISymbol symbol) {
		symbols.add(symbol);
		return true;
	}

}
