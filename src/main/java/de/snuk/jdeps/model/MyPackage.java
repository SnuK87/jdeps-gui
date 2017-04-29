package de.snuk.jdeps.model;

import java.util.ArrayList;
import java.util.List;

public class MyPackage {

	private String name;
	private List<MyClass> classes;

	public MyPackage() {
		classes = new ArrayList<>();
	}

	public MyPackage(String name) {
		this.name = name;
		classes = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MyClass> getClasses() {
		return classes;
	}

	public void setClasses(List<MyClass> classes) {
		this.classes = classes;
	}

	public void addClass(MyClass c) {
		classes.add(c);
	}

	@Override
	public String toString() {
		return "MyPackage [name=" + name + ", classes=" + classes + "]";
	}
}
