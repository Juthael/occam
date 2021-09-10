package com.tregouet.occam.data.categories.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.ContextObject;

public class CategoryTest {

	private String[] prog1 = new String[] {"A", "B", "C"};
	private String[] prog2 = new String[] {"D", "E", "F"};
	private String[] prog3 = new String[] {"G", "H"};
	private IContextObject obj1;
	private IContextObject obj2;
	private IContextObject obj3;
	private ICategory cat;
	private ICategory cat2;
	
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
		cat = new Category(catIntent, cat1Extent);
	}

	@Test
	public void whenSpecifiedConstraintIsMetThenReturnsTrue() {
		List<String> constraint1 = Arrays.asList(new String[] {"A", "B", "C"});
		List<String> constraint2 = Arrays.asList(new String[] {"B", "C"});
		List<String> constraint3 = Arrays.asList(new String[] {"A", "C"});
		List<String> constraint4 = Arrays.asList(new String[] {"D", "E", "F"});
		List<String> constraint5 = Arrays.asList(new String[] {"H"});
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
		fail("Not yet implemented");
	}
	
	@Test
	public void whenSpecifiedConstraintIsMatchedThenOneAttributeIsReturned() {
		fail("Not yet implemented");
	}
	
	@Test
	public void whenSpecifiedConstraintIsNotMatchedThenNullIsReturned() {
		fail("Not yet implemented");
	}	

}
