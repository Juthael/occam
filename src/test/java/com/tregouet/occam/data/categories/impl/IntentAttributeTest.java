package com.tregouet.occam.data.categories.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.ContextObject;

public class IntentAttributeTest {

	private String[] prog1 = new String[] {"A", "B", "C"};
	private String[] prog2 = new String[] {"D", "E", "F"};
	private String[] prog3 = new String[] {"G", "H"};
	private String[] prog4 = new String[] {"I", "J", "K"};
	private IContextObject obj1;
	private IContextObject obj2;
	private IContextObject obj3;
	private ICategory cat1;
	private ICategory cat2;
	private ICategory cat3;
	
	
	@Before
	public void setUp() throws Exception {
		List<List<String>> obj1Intent = new ArrayList<>();
		List<List<String>> obj2Intent = new ArrayList<>();
		List<List<String>> obj3Intent = new ArrayList<>();
		
		obj1Intent.add(Arrays.asList(prog1));
		obj1Intent.add(Arrays.asList(prog2));
		obj1Intent.add(Arrays.asList(prog3));
		
		obj2Intent.add(Arrays.asList(prog2));
		obj2Intent.add(Arrays.asList(prog3));
		obj2Intent.add(Arrays.asList(prog4));
		
		obj3Intent.add(Arrays.asList(prog3));
		obj3Intent.add(Arrays.asList(prog4));
		obj3Intent.add(Arrays.asList(prog1));
		
		obj1 = new ContextObject(obj1Intent);
		obj2 = new ContextObject(obj2Intent);
		obj3 = new ContextObject(obj3Intent);
		
		Set<IConstruct> cat1Intent = new HashSet<>();
		cat1Intent.addAll(obj1.getConstructs());
		Set<IContextObject> cat1Extent = new HashSet<>();
		cat1Extent.add(obj1);
		cat1 = new Category(cat1Intent, cat1Extent);
		
		Set<IConstruct> cat2Intent = new HashSet<>();
		cat2Intent.addAll(obj2.getConstructs());
		Set<IContextObject> cat2Extent = new HashSet<>();
		cat2Extent.add(obj2);
		cat2 = new Category(cat2Intent, cat2Extent);
		
		Set<IConstruct> cat3Intent = new HashSet<>();
		cat3Intent.addAll(obj3.getConstructs());
		Set<IContextObject> cat3Extent = new HashSet<>();
		cat3Extent.add(obj3);
		cat3 = new Category(cat3Intent, cat3Extent);
	}

	@Test
	public void whenFromDifferentCategoriesThenConstructsCanBeEqualButNotIntentAttributes() {
		Set<IConstruct> constructsCat1 = cat1.getIntent().stream().map(i -> new Construct(i)).collect(Collectors.toSet());
		Set<IConstruct> constructsCat2 = cat2.getIntent().stream().map(i -> new Construct(i)).collect(Collectors.toSet());
		Set<IConstruct> constructsCat3 = cat3.getIntent().stream().map(i -> new Construct(i)).collect(Collectors.toSet());
		Set<IConstruct> commonConstructsCat1Cat2 = new HashSet<>(constructsCat1);
		commonConstructsCat1Cat2.retainAll(constructsCat2);
		Set<IConstruct> commonConstructsCat2Cat3 = new HashSet<>(constructsCat2);
		commonConstructsCat2Cat3.retainAll(constructsCat3);
		Set<IConstruct> commonConstructsCat1Cat3 = new HashSet<>(constructsCat1);
		commonConstructsCat1Cat3.retainAll(constructsCat3);
		Set<IIntentAttribute> commonAttributesCat1Cat2 = new HashSet<>(cat1.getIntent());
		commonAttributesCat1Cat2.retainAll(cat2.getIntent());
		Set<IIntentAttribute> commonAttributesCat2Cat3 = new HashSet<>(cat2.getIntent());
		commonAttributesCat2Cat3.retainAll(cat3.getIntent());
		Set<IIntentAttribute> commonAttributesCat1Cat3 = new HashSet<>(cat1.getIntent());
		commonAttributesCat1Cat3.retainAll(cat3.getIntent());		
		assertTrue(!commonConstructsCat1Cat2.isEmpty() && commonAttributesCat1Cat2.isEmpty() 
				&& !commonConstructsCat2Cat3.isEmpty() && commonAttributesCat2Cat3.isEmpty() 
				&& !commonConstructsCat1Cat3.isEmpty() && commonAttributesCat1Cat3.isEmpty());
	}

}
