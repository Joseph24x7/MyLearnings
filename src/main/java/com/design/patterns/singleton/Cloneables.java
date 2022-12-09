package com.design.patterns.singleton;

public class Cloneables {

	public static void main(String[] args) throws CloneNotSupportedException, Exception {
		Singleton instanceOne = Singleton.instance;
		Singleton instanceTwo = (Singleton) instanceOne.clone();
		System.out.println("hashCode of instance 1 - " + instanceOne.hashCode());
		System.out.println("hashCode of instance 2 - " + instanceTwo.hashCode());
	}

}

class Singleton implements Cloneable{

	public static Singleton instance = new Singleton();

	private Singleton() {
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}