package com.tregouet.occam.data.denotations.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.impl.Concept;
import com.tregouet.occam.data.concepts.impl.ContextObject;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public class DenotationTest {

	private String[] prog1 = new String[] {"A", "B", "C"};
	private String[] prog2 = new String[] {"D", "E", "F"};
	private String[] prog3 = new String[] {"G", "H"};
	private String[] prog4 = new String[] {"I", "J", "K"};
	private IContextObject obj1;
	private IContextObject obj2;
	private IContextObject obj3;
	private IConcept dS1;
	private IConcept dS2;
	private IConcept dS3;
	
	
	@Before
	public void setUp() throws Exception {
		List<List<String>> obj1ConstructsAsStrings = new ArrayList<>();
		List<List<String>> obj2ConstructsAsStrings = new ArrayList<>();
		List<List<String>> obj3ConstructsAsStrings = new ArrayList<>();
		
		obj1ConstructsAsStrings.add(Arrays.asList(prog1));
		obj1ConstructsAsStrings.add(Arrays.asList(prog2));
		obj1ConstructsAsStrings.add(Arrays.asList(prog3));
		
		obj2ConstructsAsStrings.add(Arrays.asList(prog2));
		obj2ConstructsAsStrings.add(Arrays.asList(prog3));
		obj2ConstructsAsStrings.add(Arrays.asList(prog4));
		
		obj3ConstructsAsStrings.add(Arrays.asList(prog3));
		obj3ConstructsAsStrings.add(Arrays.asList(prog4));
		obj3ConstructsAsStrings.add(Arrays.asList(prog1));
		
		obj1 = new ContextObject(obj1ConstructsAsStrings);
		obj2 = new ContextObject(obj2ConstructsAsStrings);
		obj3 = new ContextObject(obj3ConstructsAsStrings);
		
		Set<IConstruct> dS1Constructs = new HashSet<>();
		dS1Constructs.addAll(obj1.getConstructs());
		Set<IContextObject> cat1Extent = new HashSet<>();
		cat1Extent.add(obj1);
		dS1 = new Concept(dS1Constructs, cat1Extent);
		
		Set<IConstruct> dS2Constructs = new HashSet<>();
		dS2Constructs.addAll(obj2.getConstructs());
		Set<IContextObject> cat2Extent = new HashSet<>();
		cat2Extent.add(obj2);
		dS2 = new Concept(dS2Constructs, cat2Extent);
		
		Set<IConstruct> dS3Constructs = new HashSet<>();
		dS3Constructs.addAll(obj3.getConstructs());
		Set<IContextObject> cat3Extent = new HashSet<>();
		cat3Extent.add(obj3);
		dS3 = new Concept(dS3Constructs, cat3Extent);
	}

	@Test
	public void whenFromDifferentDenotationSetsThenConstructsCanBeEqualButNotDenotations() {
		Set<IConstruct> constructsDS1 = dS1.getDenotations().stream().map(i -> new Construct(i)).collect(Collectors.toSet());
		Set<IConstruct> constructsDS2 = dS2.getDenotations().stream().map(i -> new Construct(i)).collect(Collectors.toSet());
		Set<IConstruct> constructsDS3 = dS3.getDenotations().stream().map(i -> new Construct(i)).collect(Collectors.toSet());
		Set<IConstruct> commonConstructsDS1DS2 = new HashSet<>(constructsDS1);
		commonConstructsDS1DS2.retainAll(constructsDS2);
		Set<IConstruct> commonConstructsDS2DS3 = new HashSet<>(constructsDS2);
		commonConstructsDS2DS3.retainAll(constructsDS3);
		Set<IConstruct> commonConstructsdS1dS3 = new HashSet<>(constructsDS1);
		commonConstructsdS1dS3.retainAll(constructsDS3);
		Set<IDenotation> commonAttributesDS1DS2 = new HashSet<>(dS1.getDenotations());
		commonAttributesDS1DS2.retainAll(dS2.getDenotations());
		Set<IDenotation> commonAttributesDS2DS3 = new HashSet<>(dS2.getDenotations());
		commonAttributesDS2DS3.retainAll(dS3.getDenotations());
		Set<IDenotation> commonAttributesDS1DS3 = new HashSet<>(dS1.getDenotations());
		commonAttributesDS1DS3.retainAll(dS3.getDenotations());		
		assertTrue(!commonConstructsDS1DS2.isEmpty() && commonAttributesDS1DS2.isEmpty() 
				&& !commonConstructsDS2DS3.isEmpty() && commonAttributesDS2DS3.isEmpty() 
				&& !commonConstructsdS1dS3.isEmpty() && commonAttributesDS1DS3.isEmpty());
	}

}
