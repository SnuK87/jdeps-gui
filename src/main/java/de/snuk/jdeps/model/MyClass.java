package de.snuk.jdeps.model;

public class MyClass {

	private String name;
	private MyPackage pack;

	public MyClass(String name, MyPackage pack) {
		super();
		this.name = name;
		this.pack = pack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MyPackage getPack() {
		return pack;
	}

	public void setPack(MyPackage pack) {
		this.pack = pack;
	}

	@Override
	public String toString() {
		return "MyClass [name=" + name + "]";
	}
}
