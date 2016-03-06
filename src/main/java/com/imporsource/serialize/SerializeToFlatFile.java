package com.imporsource.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeToFlatFile {
	public static void main(String[] args) {
		SerializeToFlatFile ser = new SerializeToFlatFile();
		ser.savePerson();
		ser.restorePerson();
	}
	
	public void savePerson(){
		Person myPerson = new Person("Jay", 24);
		try{
			FileOutputStream fos = new FileOutputStream("person.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			System.out.println("Person--Jay,24---Written");
			
			oos.writeObject(myPerson);
			oos.flush();
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void restorePerson(){
		try{
			FileInputStream fls = new FileInputStream("person.txt");
			ObjectInputStream ois = new ObjectInputStream(fls);
			
			Person myPerson = (Person)ois.readObject();
			System.out.println("\n---------------------\n");
			System.out.println("Person --read:");
			System.out.println("Name is:"+myPerson.getName());
			System.out.println("Age is :"+myPerson.getAge());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}