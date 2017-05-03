package de.snuk.jdeps.model;

import java.util.List;

public class JdepsResult {

	private List<MyPackage> packages;
	private List<String> errorLines;

	public JdepsResult() {

	}

	public JdepsResult(List<String> errorLines) {
		this.errorLines = errorLines;
	}

	public JdepsResult(List<MyPackage> packages, List<String> errorLines) {
		this.packages = packages;
		this.errorLines = errorLines;
	}

	public List<MyPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<MyPackage> packages) {
		this.packages = packages;
	}

	public List<String> getErrorLines() {
		return errorLines;
	}

	public void setErrorLines(List<String> errorLines) {
		this.errorLines = errorLines;
	}
}
