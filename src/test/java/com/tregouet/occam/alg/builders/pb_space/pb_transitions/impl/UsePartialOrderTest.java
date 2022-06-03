package com.tregouet.occam.alg.builders.pb_space.pb_transitions.impl;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.builders.pb_space.impl.AutomaticallyExpandTrivialLeaves;
import com.tregouet.occam.alg.builders.pb_space.impl.RemoveUninformative;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.impl.ProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class UsePartialOrderTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "tabletop1.txt");
	private List<IContextObject> context;
	private IProblemSpace pbSpace;		

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		Occam.initialize();
		context = GenericFileReader.getContextObjects(SHAPES6);
		pbSpace = new ProblemSpace(new HashSet<>(context));
	}

	@Test
	public void test() {
		pbSpace.deepen(1);
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpace.getProblemSpaceGraph(), "pb_space_DEBUG");
		pbSpace.display(19);
		IRepresentation r19 = pbSpace.getActiveRepresentation();
		System.out.println("***R. 19 productions : ");
		for (IConceptTransition conceptTransition : r19.getTransitionFunction().getTransitions()) {
			if (conceptTransition.getInputConfiguration().getInputStateID() == 105 
					&& conceptTransition.getOutputInternConfiguration().getOutputStateID() == 103) {
				System.out.println(conceptTransition.getInputConfiguration().getInputSymbol().toString());
			}
		}
		pbSpace.display(33);
		IRepresentation r33 = pbSpace.getActiveRepresentation();
		System.out.println("***R. 33 productions : ");
		for (IConceptTransition conceptTransition : r33.getTransitionFunction().getTransitions()) {
			if (conceptTransition.getInputConfiguration().getInputStateID() == 105 
					&& conceptTransition.getOutputInternConfiguration().getOutputStateID() == 103) {
				System.out.println(conceptTransition.getInputConfiguration().getInputSymbol().toString());
			}
		}
		System.out.println(System.lineSeparator());	
	}

}
