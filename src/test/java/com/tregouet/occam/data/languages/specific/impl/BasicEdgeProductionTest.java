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

import com.tregouet.occam.alg.transition_function_gen.impl.ProductionBuilder;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.impl.Concepts;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class BasicEdgeProductionTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private final DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
	}

	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes2Obj);
		List<IBasicProductionAsEdge> basicProductionAsEdges = new ProductionBuilder(concepts).getProductions();
		basicProductionAsEdges.stream().forEach(p -> {
			denotations.addVertex(p.getSource());
			denotations.addVertex(p.getTarget());
			denotations.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Test
	public void whenSwitchVariableMethodCalledThenProceeds() throws Exception {
		List<IBasicProductionAsEdge> basicProds = new ArrayList<>();
		List<ISimpleEdgeProduction> basicSwitchers = new ArrayList<>();
		List<ISimpleEdgeProduction> basicProdsAfterSwitch = new ArrayList<>();
		int switchCount = 0;
		for (IBasicProductionAsEdge prod : denotations.edgeSet()) {
			if (prod instanceof ISimpleEdgeProduction) {
				ISimpleEdgeProduction basicProd = (ISimpleEdgeProduction) prod;
				if (basicProd.isVariableSwitcher())
					basicSwitchers.add(basicProd);
				else basicProds.add(basicProd);
			}
		}
		for (IBasicProductionAsEdge basicProduction : basicProds) {
			/*
			System.out.println("Basic production at test : " + basicProduction.toString());
			System.out.println("Source category hashCode : " + basicProduction.getSourceCategory().hashCode());
			System.out.println("Target category hashCode: " + basicProduction.getTargetCategory().hashCode());
			System.out.println("Source attribute : " + basicProduction.getSource().toString());
			System.out.println("Target attribute : " + basicProduction.getTarget().toString());
			System.out.println(System.lineSeparator());
			*/
			IBasicProductionAsEdge basicProdAfterSwitch = null;
			for (ISimpleEdgeProduction varSwitcher : basicSwitchers) {
				if (basicProdAfterSwitch == null) {
					/*
					System.out.println("Variable switcher at test : " + varSwitcher.toString());
					*/
					IBasicProductionAsEdge switched = basicProduction.switchVariableOrReturnNull(varSwitcher);
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
