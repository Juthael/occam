package com.tregouet.occam.io.output;

public class LocalPaths {

	public static final LocalPaths INSTANCE = new LocalPaths();
	private String targetFolderPath = "C:\\Users\\TREGOUET\\Documents\\Sandbox";

	private LocalPaths() {
	}

	public String getTargetFolderPath() {
		return new String(targetFolderPath);
	}

	public void setTargetFolderPath(String targetFolderPath) {
		this.targetFolderPath = targetFolderPath;
	}

}
