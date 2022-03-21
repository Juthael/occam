package com.tregouet.occam.data.languages.specific.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.builders.representations.properties.transitions.impl.ProductionSetBuilder;
import com.tregouet.occam.data.languages.specific.ICompositeEdgeProduction;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class CompositeEdgeProductionTest {
	
	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IPreconcepts preconcepts;
	private DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
	}

	@Before
	public void setUp() throws Exception {
		preconcepts = new Preconcepts(shapes2Obj);
		List<IStronglyContextualized> stronglyContextualizeds = new ProductionSetBuilder(preconcepts).getProductions();
		stronglyContextualizeds.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Test
	public void whenSwitchVariableCalledThenProceeds() {
		List<IStronglyContextualized> compositeProds = new ArrayList<>();
		List<ICompositeEdgeProduction> compositeSwitchers = new ArrayList<>();
		List<ICompositeEdgeProduction> compositeProdsAfterSwitch = new ArrayList<>();
		int switchCount = 0;
		for (IStronglyContextualized prod : denotations.edgeSet()) {
			if (prod instanceof ICompositeEdgeProduction) {
				ICompositeEdgeProduction compositeProd = (ICompositeEdgeProduction) prod;
				if (compositeProd.isVariableSwitcher())
					compositeSwitchers.add(compositeProd);
				else compositeProds.add(compositeProd);
			}
		}
		for (IStronglyContextualized compositeProduction : compositeProds) {
			/*
			System.out.println("Composite production at test : " + compositeProduction.toString());
			System.out.println("Source category hashCode : " + compositeProduction.getSourceCategory().hashCode());
			System.out.println("Target category hashCode: " + compositeProduction.getTargetCategory().hashCode());
			System.out.println("Source attribute : " + compositeProduction.getSource().toString());
			System.out.println("Target attribute : " + compositeProduction.getTarget().toString());
			System.out.println(System.lineSeparator());
			*/
			IStronglyContextualized compositeProdAfterSwitch = null;
			for (ICompositeEdgeProduction varSwitcher : compositeSwitchers) {
				if (compositeProdAfterSwitch == null) {
					/*
					System.out.println("Variable switcher at test : " + varSwitcher.toString());
					*/
					IStronglyContextualized switched = compositeProduction.switchVariableOrReturnNull(varSwitcher);
					if (switched != null) {
						compositeProdAfterSwitch = switched;
						switchCount++;
						/*
						System.out.println("SWITCH : ");
						System.out.println("Variable switcher source category hashCode : " + varSwitcher.getSourceCategory().hashCode());
						System.out.println("Variable switcher target category hashCode: " + varSwitcher.getTargetCategory().hashCode());
						System.out.println("Variable switcher source attribute : " + varSwitcher.getSource().toString());
						System.out.println("Variable switcher target attribute : " + varSwitcher.getTarget().toString());
						System.out.println("RESULT : " + compositeProdAfterSwitch.toString());
						System.out.println("Switched production source category hashCode : " + compositeProdAfterSwitch.getSourceCategory().hashCode());
						System.out.println("Switched production target category hashCode: " + compositeProdAfterSwitch.getTargetCategory().hashCode());
						System.out.println("Switched production source attribute : " + compositeProdAfterSwitch.getSource().toString());
						System.out.println("VSwitched production target attribute : " + compositeProdAfterSwitch.getTarget().toString());
						System.out.println(System.lineSeparator());
						*/
					}
				}
				else {
					/*
					System.out.println("FAIL");
					*/
				}
			}
			if (compositeProdAfterSwitch == null) {
				compositeProdAfterSwitch = compositeProduction;
				/*
				System.out.println("NO SWITCH" + System.lineSeparator());
				*/
			}
			compositeProdsAfterSwitch.add((ICompositeEdgeProduction) compositeProdAfterSwitch);
		}
		assertTrue(switchCount > 0 && !compositeProds.equals(compositeProdsAfterSwitch));
	}

}
