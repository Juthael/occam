package com.tregouet.occam.io.output;

public class Paths {
	
	public static final Paths INSTANCE = new Paths();
	private String targetFolderPath = "C:\\Users\\TREGOUET\\Documents\\Sandbox";
	
	private Paths() {
	}
	
	public void setTargetFolderPath(String targetFolderPath) {
		this.targetFolderPath = targetFolderPath;
	}
	
	public String getTargetFolderPath() {
		return new String(targetFolderPath);
	}

}
