package com.tregouet.occam.data.logical_structures.languages.words.construct.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Construct implements IConstruct {

	protected final List<ISymbol> symbols;
	private int size;
	private int nbOfTerminals;

	public Construct(IConstruct construct) {
		symbols = new ArrayList<>(construct.asList());
		size = construct.size();
		nbOfTerminals = construct.getNbOfTerminals();
	}

	public Construct(List<ISymbol> prog) {
		this.symbols = prog;
		size = symbols.size();
		nbOfTerminals = nbOfTerminals();
	}

	public Construct(String[] progStrings) {
		symbols = new ArrayList<>();
		for (String symString : progStrings) {
			if (symString.equals(ISymbolSeq.PLACEHOLDER))
				symbols.add(new Variable(AVariable.DEFERRED_NAMING));
			else
				symbols.add(new Terminal(symString));
		}
		size = symbols.size();
		int nbOfTerminals = 0;
		for (ISymbol symbol : symbols) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		this.nbOfTerminals = nbOfTerminals;
	}

	private Construct(List<ISymbol> symbols, int nbOfTerminals) {
		this.symbols = symbols;
		size = symbols.size();
		this.nbOfTerminals = nbOfTerminals;
	}

	@Override
	public List<ISymbol> asList() {
		return symbols;
	}

	@Override
	public IConstruct copy() {
		return new Construct(new ArrayList<>(symbols), nbOfTerminals);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
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
	public String getFunctionType() {
		List<AVariable> vars = getVariables();
		if (vars.isEmpty())
			return null;
		StringBuilder sB = new StringBuilder();
		sB.append("τ_(");
		Iterator<AVariable> varIte = vars.iterator();
		while (varIte.hasNext()) {
			sB.append(varIte.next().toString());
			if (varIte.hasNext())
				sB.append(", ");
		}
		sB.append(")");
		return sB.toString();
	}

	@Override
	public Iterator<ISymbol> getIteratorOverSymbols() {
		return symbols.iterator();
	}

	@Override
	public List<ITerminal> getListOfTerminals() {
		return symbols.stream().filter(d -> d instanceof ITerminal).map(s -> (ITerminal) s)
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
		return (nbOfTerminals < size);
	}

	@Override
	public boolean meets(IConstruct constraint) {
		List<ISymbol> constraintSymbols = constraint.asList();
		if (this.nbOfTerminals >= constraintSymbols.size()) {
			int constraintIdx = 0;
			for (int constructIdx = 0; constructIdx < symbols.size()
					&& constraintIdx < constraintSymbols.size(); constructIdx++) {
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
	public List<String> toListOfStringsWithPlaceholders() {
		List<String> list = new ArrayList<>();
		for (ISymbol sym : symbols) {
			if (sym instanceof AVariable)
				list.add(ISymbolSeq.PLACEHOLDER);
			else
				list.add(sym.toString());
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0; i < symbols.size(); i++) {
			sB.append(symbols.get(i));
			if (i < symbols.size() - 1)
				sB.append(" ");
		}
		return sB.toString();
	}

	protected int nbOfTerminals() {
		int nbOfTerminals = 0;
		for (ISymbol symbol : symbols) {
			if (symbol instanceof ITerminal)
				nbOfTerminals++;
		}
		return nbOfTerminals;
	}

	@Override
	public boolean isAlphaConvertibleWith(IConstruct other) {
		List<ISymbol> otherSymbols = other.asList();
		if (this.symbols.size() == otherSymbols.size()) {
			for (int i = 0 ; i < symbols.size() ; i++) {
				ISymbol thisSymbol = symbols.get(i);
				ISymbol otherSymbol = otherSymbols.get(i);
				if (!thisSymbol.equals(otherSymbol) 
						&& !(thisSymbol instanceof AVariable && otherSymbol instanceof AVariable))
						return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

}
