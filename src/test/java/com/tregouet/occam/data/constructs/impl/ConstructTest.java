package com.tregouet.occam.data.constructs.impl;

import static org.junit.Assert.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class ConstructTest {

	private static Path shapes3 = Paths.get(".", "src", "test", "java", "files_used_for_tests", "shapes3.txt");
	List<IContextObject> shapes3Obj;
	
	@Before
	public void setUp() throws Exception {
		shapes3Obj = GenericFileReader.getContextObjects(shapes3);
	}
	
	@Test
	public void whenTwoConstructWithSameProgComparedThenNeverEquals() {
		
	}
	
	@Test
	public void whenTwoConstructWithSameProgComparedThenNeverSameHashCode() {
		
	}

	

}
