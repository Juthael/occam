package com.tregouet.occam.alg.builders.representations.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class FindEveryClassificationFirstTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String NL = System.lineSeparator();
	private List<IContextObject> context;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);	
	}

	@Test
	public void whenCompleteRepresentationsRequestedThenReturned() {
		ICompleteRepresentations representations = FindEveryClassificationFirst.INSTANCE.apply(context);
		//HERE
		display(representations);
		//HERE
		assertTrue(representations != null);
	}
	
	private void display(ICompleteRepresentations representations) {
		int idx = 0;
		StringBuilder sB = new StringBuilder();
		for (ICompleteRepresentation completeRepresentation : representations.getSortedRepresentations()) {
			VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(
					completeRepresentation.getDescription(), "FindEveryClassificationFirst_" + Integer.toString(idx));
			sB.append("***************") 
				.append("REPRESENTATION n. " + Integer.toString(idx) + " : ")
				.append(completeRepresentation.getDescription().toString() + NL)
				.append("Score : " + completeRepresentation.score().toString() + NL + NL)
				.append("Facts : " + NL + NL);
			Map<Integer, String> acceptStateIDToFactualDesc = 
					completeRepresentation.mapParticularIDsToFactualDescription(
							FormattersAbstractFactory.INSTANCE.getFactDisplayer());
			TreeSet<Integer> sortedParticularIDs = new TreeSet<>();
			sortedParticularIDs.addAll(acceptStateIDToFactualDesc.keySet());
			for (Integer particularID : sortedParticularIDs) {
				sB.append("***Object n." + Integer.toString(particularID) 
					+ NL
					+ acceptStateIDToFactualDesc.get(particularID)
					+ NL + NL);
			}
			idx++;
			sB.append(NL);
		}
		System.out.println(sB.toString());
	}

}
