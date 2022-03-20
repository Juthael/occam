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

import com.tregouet.occam.alg.generators.representations.transitions_gen.impl.ProductionSetBuilder;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class BasicEdgeProductionTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IPreconcepts preconcepts;
	private final DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations = 
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
	public void whenSwitchVariableMethodCalledThenProceeds() throws Exception {
		List<IStronglyContextualized> basicProds = new ArrayList<>();
		List<ISimpleEdgeProduction> basicSwitchers = new ArrayList<>();
		List<ISimpleEdgeProduction> basicProdsAfterSwitch = new ArrayList<>();
		int switchCount = 0;
		for (IStronglyContextualized prod : denotations.edgeSet()) {
			if (prod instanceof ISimpleEdgeProduction) {
				ISimpleEdgeProduction basicProd = (ISimpleEdgeProduction) prod;
				if (basicProd.isVariableSwitcher())
					basicSwitchers.add(basicProd);
				else basicProds.add(basicProd);
			}
		}
		for (IStronglyContextualized basicProduction : basicProds) {
			/*
			System.out.println("Basic production at test : " + basicProduction.toString());
			System.out.println("Source category hashCode : " + basicProduction.getSourceCategory().hashCode());
			System.out.println("Target category hashCode: " + basicProduction.getTargetCategory().hashCode());
			System.out.println("Source attribute : " + basicProduction.getSource().toString());
			System.out.println("Target attribute : " + basicProduction.getTarget().toString());
			System.out.println(System.lineSeparator());
			*/
			IStronglyContextualized basicProdAfterSwitch = null;
			for (ISimpleEdgeProduction varSwitcher : basicSwitchers) {
				if (basicProdAfterSwitch == null) {
					/*
					System.out.println("Variable switcher at test : " + varSwitcher.toString());
					*/
					IStronglyContextualized switched = basicProduction.switchVariableOrReturnNull(varSwitcher);
					if (switched != null) {
						basicProdAfterSwitch = switched;
						switchCount++;
						/*
						System.out.println("SWITCH : ");
						System.out.println("Variable switcher source category hashCode : " + varSwitcher.getSourceCategory().hashCode());
						System.out.println("Variable switcher target category hashCode: " + varSwitcher.getTargetCategory().hashCode());
						System.out.println("Variable switcher source attribute : " + varSwitcher.getSource().toString());
						System.out.println("Variable switcher target attribute : " + varSwitcher.getTarget().toString());
						System.out.println("RESULT : " + basicProdAfterSwitch.toString());
						System.out.println("Switched production source category hashCode : " + basicProdAfterSwitch.getSourceCategory().hashCode());
						System.out.println("Switched production target category hashCode: " + basicProdAfterSwitch.getTargetCategory().hashCode());
						System.out.println("Switched production source attribute : " + basicProdAfterSwitch.getSource().toString());
						System.out.println("VSwitched production target attribute : " + basicProdAfterSwitch.getTarget().toString());
						System.out.println(System.lineSeparator());
						*/
					}
					else {
						/*
						System.out.println("FAIL");
						*/
					}
				}
			}
			if (basicProdAfterSwitch == null) {
				basicProdAfterSwitch = basicProduction;
				/*
				System.out.println("NO SWITCH" + System.lineSeparator());
				*/
			}
			basicProdsAfterSwitch.add((ISimpleEdgeProduction) basicProdAfterSwitch);
		}
		assertTrue(switchCount > 0 && !basicProds.equals(basicProdsAfterSwitch));
	}

}
