package com.tregouet.occam.data.structures.automata.heads;

import com.tregouet.occam.data.structures.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.structures.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.IWord;

public interface IPushdownAutomatonHead<TransitionFunc extends IPushdownAutomatonTF<InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition>, InputSymbol extends ISymbol, StackSymbol extends ISymbol, InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>, OutputConfig extends IPushdownAutomatonOIC<StackSymbol>, Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>, TapeSet extends IPushdownAutomatonTapeSet<InputSymbol, StackSymbol, TapeSet>, Word extends IWord<InputSymbol>, Head extends IPushdownAutomatonHead<TransitionFunc, InputSymbol, StackSymbol, InputConfig, OutputConfig, Transition, TapeSet, Word, Head>>
		extends IHead<TransitionFunc, TapeSet, InputSymbol, Word, InputConfig, OutputConfig, Transition, Head> {

}
