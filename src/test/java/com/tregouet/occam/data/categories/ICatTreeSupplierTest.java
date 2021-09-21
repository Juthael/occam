package com.tregouet.occam.data.categories;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class ICatTreeSupplierTest {

	private static final Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private ICategories categories;
	private ICatTreeSupplier treeSupplier;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
	}

	@Before
	public void setUp() throws Exception {
		categories = new Categories(shapes2Obj);
		treeSupplier = categories.getCatTreeSupplier();
	}

	@Test
	public void test() {
		fail();
	}

}
