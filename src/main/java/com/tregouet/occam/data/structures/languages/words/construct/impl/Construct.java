package com.tregouet.occam.data.structures.languages.words.construct.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.productions.IBasicProduction;
import com.tregouet.occam.data.structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.structures.lambda_terms.impl.LambdaAbstrApp;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.structures.languages.alphabets.impl.Variable;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
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
	public ILambdaExpression abstractAndApply(IAbstractionApplication abstrApp, boolean safeMode) {
		if (safeMode) {
			List<AVariable> varToBind = new ArrayList<>();
			for (IBasicProduction prod : abstrApp.getArguments()) {
				if (!prod.isEpsilon() && !prod.isIdentityProd())
					varToBind.add(prod.getVariable());
			}
			if (!this.getFreeVariables().containsAll(varToBind))
				return null;
		}
		return new LambdaAbstrApp(this, abstrApp);
	}

	@Override
	public List<ISymbol> asList() {
		return symbols;
	}

	@Override
	public Integer compareTo(IConstruct other) {
		return ConstructComparator.INSTANCE.compare(this, other);
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
	public Set<AVariable> getFreeVariables() {
		return new HashSet<>(getVariables());
	}

	@Override
	public Iterator<ISymbol> getIteratorOverSymbols() {
		return symbols.iterator();
	}

	@Override
	public List<ITerminal> getListOfTerminals() {
		List<ITerminal> listOfTerminals = new ArrayList<>();
		for (ISymbol symbol : symbols) {
			if (symbol instanceof ITerminal)
				listOfTerminals.add((ITerminal) symbol);
		}
		return listOfTerminals;
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
	public boolean isAbstractionApplication() {
		return false;
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
	public boolean meets(IConstruct constraint) {
		Integer comparison = this.compareTo(constraint);
		if (comparison != null && comparison <= 0)
			return true;
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
	public int size() {
		return size;
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

}
