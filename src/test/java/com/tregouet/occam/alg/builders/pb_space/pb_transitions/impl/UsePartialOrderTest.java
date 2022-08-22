package com.tregouet.occam.alg.builders.pb_space.pb_transitions.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.impl.ProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
public class UsePartialOrderTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "tabletop1.txt");
	private List<IContextObject> context;
	private IProblemSpace pbSpace;

	@Before
	public void setUp() throws Exception {
		Occam.initialize();
		context = GenericFileReader.getContextObjects(SHAPES6);
		pbSpace = new ProblemSpace(new HashSet<>(context));
	}

	@Test
	public void whenDevelopmentRequestedThenProceeded() {
		boolean asExpected = true;
		try {
			pbSpace.develop(1);
		}
		catch (Exception e) {
			asExpected = false;
		}
		assertTrue(asExpected);
	}

	private void print(IPartition partition) {
		String nl = System.lineSeparator();
		StringBuilder sB = new StringBuilder();
		sB.append(partition.toString() + nl)
			.append("genus : " + partition.getGenusID().toString() + nl)
			.append("species : " + Arrays.toString(partition.getSpeciesIDs()) + nl)
			.append("differentiae : " + nl);
		for (ADifferentiae diff : partition.asGraph().edgeSet()) {
			for (IProperty property : diff.getProperties()) {
				sB.append("   function : " + property.getFunction().toString() + nl);
				sB.append("   computations : " + nl);
				for (IComputation computation : property.getComputations())
					sB.append(FormattersAbstractFactory.INSTANCE.getComputationLabeller().apply(computation));
				sB.append("   values : " + nl);
				for (IComputation computation : property.getComputations())
					sB.append(computation.getOutput().toString());
			}
		}
		sB.append(nl);
	System.out.println(sB.toString());
	}

	private IPartition selectProblematicPartition(IRepresentation representation) {
		for (IPartition partition : representation.getPartitions()) {
			if (partition.toString().equals("{1}{3}{4}{2, 5}"))
				return partition;
		}
		return null;
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

	private static Set<ADifferentiae> difference(IPartition p1, IPartition p2){
		Set<ADifferentiae> p1Diff = new HashSet<>();
		for (ADifferentiae diff : p1.getDifferentiae())
			p1Diff.add(diff);
		Set<ADifferentiae> p2Diff = new HashSet<>();
		for (ADifferentiae diff : p2.getDifferentiae())
			p2Diff.add(diff);
		Set<ADifferentiae> p1p2Diff = new HashSet<>(Sets.difference(p1Diff, p2Diff));
		return p1p2Diff;
	}

}
