package com.tregouet.occam.data.concepts.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concept;
import com.tregouet.occam.data.concepts.impl.IntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.ContextObject;

public class ConceptTest {

	private String[] prog1 = new String[] {"A", "B", "C"};
	private String[] prog2 = new String[] {"D", "E", "F"};
	private String[] prog3 = new String[] {"G", "H", "C"};
	private IContextObject obj1;
	private IConcept cat;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		List<List<String>> obj1Intent = new ArrayList<>();
		
		obj1Intent.add(Arrays.asList(prog1));
		obj1Intent.add(Arrays.asList(prog2));
		obj1Intent.add(Arrays.asList(prog3));
		
		obj1 = new ContextObject(obj1Intent);
		
		Set<IConstruct> catIntent = new HashSet<>();
		catIntent.addAll(obj1.getConstructs());
		Set<IContextObject> cat1Extent = new HashSet<>();
		cat1Extent.add(obj1);
		cat = new Concept(catIntent, cat1Extent);
	}

	@Test
	public void whenSpecifiedConstraintIsMetThenReturnsTrue() {
		List<String> constraint1 = Arrays.asList(new String[] {"A", "B", "C"});
		List<String> constraint2 = Arrays.asList(new String[] {"B", "C"});
		List<String> constraint3 = Arrays.asList(new String[] {"A", "C"});
		List<String> constraint4 = Arrays.asList(new String[] {"D", "E", "F"});
		List<String> constraint5 = Arrays.asList(new String[] {"C"});
		List<String> constraint6 = new ArrayList<>();
		assertTrue(cat.meets(constraint1) 
				&& cat.meets(constraint2)
				&& cat.meets(constraint3)
				&& cat.meets(constraint4)
				&& cat.meets(constraint5)
				&& cat.meets(constraint6));
	}
	
	@Test
	public void whenSpecifiedConstraintIsNotMetThenReturnsFalse() {
		List<String> constraint1 = Arrays.asList(new String[] {"A", "B", "X"});
		List<String> constraint2 = Arrays.asList(new String[] {"A", "C", "B"});
		List<String> constraint3 = Arrays.asList(new String[] {"X"});
		List<String> constraint4 = Arrays.asList(new String[] {"D", "E", "F", "G"});
		List<String> constraint5 = Arrays.asList(new String[] {"B", "B"});
		assertTrue(!cat.meets(constraint1) 
				&& !cat.meets(constraint2)
				&& !cat.meets(constraint3)
				&& !cat.meets(constraint4)
				&& !cat.meets(constraint5));
	}
	
	@Test
	public void whenSpecifiedConstraintMatchedOnceThenExpectedAttributeReturned() throws IOException {
		IIntentAttribute expected = new IntentAttribute(new Construct(prog1), cat);
		IIntentAttribute returned = cat.getMatchingAttribute(Arrays.asList(new String[] {"B", "C"}));
		assertTrue(expected.equals(returned));
	}
	
	@Test
	public void whenSpecifiedConstraintIsMatchedTwiceThenExceptionThrown() {
		boolean exceptionThrown = false;
		try {
			cat.getMatchingAttribute(Arrays.asList(new String[] {"C"}));
		}
		catch (IOException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void whenSpecifiedConstraintIsNotMatchedThenNullIsReturned() throws IOException {
		IIntentAttribute returned = cat.getMatchingAttribute(Arrays.asList(new String[] {"X"}));
		assertTrue(returned == null);
	}	

}
