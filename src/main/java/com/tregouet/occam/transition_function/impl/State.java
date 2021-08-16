package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.compiler.ITapeSet;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IState;

public class State implements IState {

	private final ICategory category;
	private final int extentSize;
	private Set<IContextObject> extent = null;
	private List<IOperator> transitions = null;
	private List<ITapeSet> evaluationQueue = new ArrayList<>();
	private int rank = 0;
	private int iD;
	
	
	public State(ICategory category, int extentSize) {
		this.category = category;
		this.extentSize = extentSize;
	}

	@Override
	public boolean acceptInput(ITapeSet tapeSet) {
		//Not implemented yet
		/*
		if (!isActive())
			return false;
		for (IOperator operator : transitions) {
			if (operator.operateOn(tapeSet.))
				
		}
		*/
		return false;
	}

	@Override
	public void addTransition(IOperator operator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateOutputLanguage() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<IIntentAttribute> getInputLanguage() {
		if (this.isActive()) {
			Set<IIntentAttribute> inputlanguage = new HashSet<>();
			for (IOperator operator : transitions) {
				for (IProduction prod : operator.operation())
					inputlanguage.add(prod.getSource());
			}
			return inputlanguage;
		}
		else {
			//not proper input language. For tests use. 
			return category.getIntent();
		}
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStateID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStateType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOperative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mergeTapeSetsWithSameInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void proceedTransitions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRank(int rank) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getExtentSize() {
		return extentSize;
	}

}