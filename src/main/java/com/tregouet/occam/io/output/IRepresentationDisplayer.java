package com.tregouet.occam.io.output;

import java.io.IOException;
import java.nio.file.Path;

public interface IRepresentationDisplayer {
	
	String generateAsymmetricalSimilarityMatrix(String alinea);
	
	void generateDenotationSetLatticeGraph() throws IOException;
	
	String generateConceptualCoherenceArray(String alinea);
	
	void generateHTML() throws IOException;
	
	String generateInputHTMLTranslation(String alinea);
	
	void generatePorphyrianTree() throws IOException;
	
	String generateSimilarityMatrix(String alinea);
	
	void generateTransitionFunctionGraph() throws IOException;
	
	void generateTreeOfDenotationSets() throws IOException;
	
	void generateTreeOfDenotations() throws IOException;
	
	double getCoherenceScore();
	
	int getDenotationSetTreeIndex();
	
	int getTransitionFunctionIndex();
	
	boolean hasNextConceptualStructure();
	
	boolean hasNextTransitionFunctionOverCurrentConceptualStructure();
	
	void nextTreeOfDenotationSets() throws IOException;
	
	void nextTransitionFunctionOverCurrentConceptualStructure() throws IOException;
	
	boolean represent(Path contextPath) throws IOException;

}
