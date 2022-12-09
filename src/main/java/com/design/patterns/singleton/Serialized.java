package com.design.patterns.singleton;

public class Serialized {

	/*public static void main(String[] args) {
		try {
			Singleton instance1 = Singleton.instance;
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream("file.text"));
			out.writeObject(instance1);
			out.close();

			// deserialize from file to object
			ObjectInput in = new ObjectInputStream(new FileInputStream("file.text"));

			Singleton instance2 = (Singleton) in.readObject();
			in.close();

			System.out.println("instance1 hashCode:- " + instance1.hashCode());
			System.out.println("instance2 hashCode:- " + instance2.hashCode());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}

/*class Singleton implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static Singleton instance = new Singleton();

	private Singleton(){}

	//To overcome this issue, we need to override readResolve() method in the Singleton class and return the same Singleton instance
	protected Object readResolve() {
		return instance;
	}
}*/
