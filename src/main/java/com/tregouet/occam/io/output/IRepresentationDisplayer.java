package com.tregouet.occam.io.output;

import java.io.IOException;
import java.nio.file.Path;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IGenusDifferentiaDefinition;
import com.tregouet.tree_finder.data.Tree;

public interface IRepresentationDisplayer {
	
	String generateAsymmetricalSimilarityMatrix(String alinea);
	
	void generateConceptLatticeGraph() throws IOException;
	
	void generateConceptTreeGraph() throws IOException;
	
	String generateConceptualCoherenceArray(String alinea);
	
	void generateHTML() throws IOException;
	
	String generateInputHTMLTranslation(String alinea);
	
	String generateSimilarityMatrix(String alinea);
	
	void generateTransitionFunctionGraph() throws IOException;
	
	double getCoherenceScore();
	
	int getConceptTreeIndex();
	
	int getTransitionFunctionIndex();
	
	boolean hasNextConceptualStructure();
	
	boolean hasNextTransitionFunctionOverCurrentConceptualStructure();
	
	void nextConceptTree() throws IOException;
	
	void nextTransitionFunctionOverCurrentCategoricalStructure() throws IOException;
	
	boolean represent(Path contextPath) throws IOException;
	
	Tree<IConcept, IGenusDifferentiaDefinition> whatIsThere();

}
