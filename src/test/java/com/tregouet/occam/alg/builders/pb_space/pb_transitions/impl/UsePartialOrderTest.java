package com.tregouet.occam.alg.builders.pb_space.pb_transitions.impl;

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
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.impl.ProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
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

	/*
	
	@Test
	public void test() {
		pbSpace.deepen(1);
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(pbSpace.getLatticeOfConcepts(), "lattice_DEBUG");
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpace.getProblemSpaceGraph(), "pb_space_DEBUG");
		pbSpace.display(21);
		IRepresentation r21 = pbSpace.getActiveRepresentation();
		Set<IPartition> r21Partitions = r21.getPartitions();
		System.out.println("***R. 21 partitions : " + FormattersAbstractFactory.INSTANCE.getProblemStateDisplayer().apply(r21));
		for (IPartition partition : r21Partitions) {
			print(partition);
		}
		System.out.println(System.lineSeparator());
		pbSpace.display(30);
		IRepresentation r30 = pbSpace.getActiveRepresentation();
		Set<IPartition> r30Partitions = r30.getPartitions();
		System.out.println("***R. 30 partitions : " + FormattersAbstractFactory.INSTANCE.getProblemStateDisplayer().apply(r30));
		for (IPartition partition : r30Partitions) {
			print(partition);
		}
		System.out.println(System.lineSeparator());
		System.out.println("***R. 21 / 30 partitions : ");
		for (IPartition partition : Sets.difference(r21Partitions, r30Partitions)) {
			print(partition);
		}
		IPartition r21ProblematicPartition = selectProblematicPartition(r21);
		IPartition r30ProblematicPartition = selectProblematicPartition(r30);
		Set<ADifferentiae> p21p30Diff = difference(r21ProblematicPartition, r30ProblematicPartition);
		Set<ADifferentiae> p31p21Diff = difference(r30ProblematicPartition, r21ProblematicPartition);
		System.out.println("here");
	}
	
	@Test
	public void testEverythingIsRule() {
		pbSpace.deepen(1);
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(pbSpace.getLatticeOfConcepts(), "lattice_DEBUG");
		VisualizersAbstractFactory.INSTANCE.getProblemSpaceViz().apply(pbSpace.getProblemSpaceGraph(), "pb_space_DEBUG");
		pbSpace.display(6);
		IRepresentation r6 = pbSpace.getActiveRepresentation();
		Set<IPartition> r6Partitions = r6.getPartitions();
		System.out.println("***R. 6 partitions : " + FormattersAbstractFactory.INSTANCE.getProblemStateDisplayer().apply(r6));
		for (IPartition partition : r6Partitions) {
			print(partition);
		}
		System.out.println(System.lineSeparator());
		pbSpace.display(43);
		IRepresentation r43 = pbSpace.getActiveRepresentation();
		Set<IPartition> r43Partitions = r43.getPartitions();
		System.out.println("***R. 43 partitions : " + FormattersAbstractFactory.INSTANCE.getProblemStateDisplayer().apply(r43));
		for (IPartition partition : r43Partitions) {
			print(partition);
		}
		System.out.println("here");
	}	
	
	*/
	
	private IPartition selectProblematicPartition(IRepresentation representation) {
		for (IPartition partition : representation.getPartitions()) {
			if (partition.toString().equals("{1}{3}{4}{2, 5}"))
				return partition;
		}
		return null;
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
				sB.append("   productions : " + nl);
				for (IContextualizedProduction production : property.getProductions()) {
					sB.append("      " + production.toString() + nl);
				}
				sB.append("   values : " + nl);
				for (IDenotation denotation : property.getResultingValues())
					sB.append("      " + denotation.toString() + nl);
			}
		}
		sB.append(nl);
	System.out.println(sB.toString());
	}
	
	Set<ADifferentiae> difference(IPartition p1, IPartition p2){
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
