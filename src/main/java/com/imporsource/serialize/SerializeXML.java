package com.imporsource.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;

//参考:http://www.cnblogs.com/bluesky5304/archive/2010/04/07/1706061.html
public class SerializeXML {

	public static void main(String[] args) {
		SerializeXML ser = new SerializeXML();
		ser.serializeToXml();
		ser.deSerializeFromXml();
	}
	public void serializeToXml(){
		Person[] myPersons = new Person[2];
		myPersons[0] = new Person("Jay", 24);
		myPersons[1] = new Person("Tom", 23);
		
		XStream xStream = new XStream();
		xStream.alias("Person", Person.class);
		try{
			FileOutputStream foStream = new FileOutputStream("persons.xml");
			xStream.toXML(myPersons,foStream);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void deSerializeFromXml(){
		XStream xStream = new XStream();
		xStream.alias("Person", Person.class);
		Person[] myPersons = null;
		try{
			FileInputStream flStream = new FileInputStream("persons.xml");
			myPersons = (Person[])xStream.fromXML(flStream);
			if(myPersons!=null){
				for(Person person:myPersons){
					System.out.println(person.getName());
					System.out.println(person.getAge());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}