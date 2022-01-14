package com.tregouet.occam.data.abstract_machines.transitions.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class CompositeProductionTest {
	
	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IDenotationSets denotationSets;
	private DirectedAcyclicGraph<IDenotation, IProduction> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
	}

	@Before
	public void setUp() throws Exception {
		denotationSets = new DenotationSets(shapes2Obj);
		List<IProduction> productions = new ProductionBuilder(denotationSets).getProductions();
		productions.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Test
	public void whenSwitchVariableCalledThenProceeds() {
		List<IProduction> compositeProds = new ArrayList<>();
		List<ICompositeProduction> compositeSwitchers = new ArrayList<>();
		List<ICompositeProduction> compositeProdsAfterSwitch = new ArrayList<>();
		int switchCount = 0;
		for (IProduction prod : denotations.edgeSet()) {
			if (prod instanceof ICompositeProduction) {
				ICompositeProduction compositeProd = (ICompositeProduction) prod;
				if (compositeProd.isVariableSwitcher())
					compositeSwitchers.add(compositeProd);
				else compositeProds.add(compositeProd);
			}
		}
		for (IProduction compositeProduction : compositeProds) {
			/*
			System.out.println("Composite production at test : " + compositeProduction.toString());
			System.out.println("Source category hashCode : " + compositeProduction.getSourceCategory().hashCode());
			System.out.println("Target category hashCode: " + compositeProduction.getTargetCategory().hashCode());
			System.out.println("Source attribute : " + compositeProduction.getSource().toString());
			System.out.println("Target attribute : " + compositeProduction.getTarget().toString());
			System.out.println(System.lineSeparator());
			*/
			IProduction compositeProdAfterSwitch = null;
			for (ICompositeProduction varSwitcher : compositeSwitchers) {
				if (compositeProdAfterSwitch == null) {
					/*
					System.out.println("Variable switcher at test : " + varSwitcher.toString());
					*/
					IProduction switched = compositeProduction.switchVariableOrReturnNull(varSwitcher);
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
			compositeProdsAfterSwitch.add((ICompositeProduction) compositeProdAfterSwitch);
		}
		assertTrue(switchCount > 0 && !compositeProds.equals(compositeProdsAfterSwitch));
	}

}
