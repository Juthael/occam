package com.tregouet.occam.data.operators.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class BasicProductionTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private static ICategories categories;
	private static final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		categories = new Categories(shapes2Obj);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenSwitchVariableMethodCalledThenProceeds() {
		List<IProduction> basicProds = new ArrayList<>();
		List<IBasicProduction> basicSwitchers = new ArrayList<>();
		List<IBasicProduction> basicProdsAfterSwitch = new ArrayList<>();
		int switchCount = 0;
		for (IProduction prod : constructs.edgeSet()) {
			if (prod instanceof IBasicProduction) {
				IBasicProduction basicProd = (IBasicProduction) prod;
				if (basicProd.isVariableSwitcher())
					basicSwitchers.add(basicProd);
				else basicProds.add(basicProd);
			}
		}
		for (IProduction basicProduction : basicProds) {
			/*
			System.out.println("Basic production at test : " + basicProduction.toString());
			System.out.println("Source category hashCode : " + basicProduction.getSourceCategory().hashCode());
			System.out.println("Target category hashCode: " + basicProduction.getTargetCategory().hashCode());
			System.out.println("Source attribute : " + basicProduction.getSource().toString());
			System.out.println("Target attribute : " + basicProduction.getTarget().toString());
			System.out.println(System.lineSeparator());
			*/
			IProduction basicProdAfterSwitch = null;
			for (IBasicProduction varSwitcher : basicSwitchers) {
				if (basicProdAfterSwitch == null) {
					/*
					System.out.println("Variable switcher at test : " + varSwitcher.toString());
					*/
					IProduction switched = basicProduction.switchVariableOrReturnNull(varSwitcher);
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
			basicProdsAfterSwitch.add((IBasicProduction) basicProdAfterSwitch);
		}
		assertTrue(switchCount > 0 && !basicProds.equals(basicProdsAfterSwitch));
	}

}
