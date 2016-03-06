package com.imporsource.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

//参考:http://www.cnblogs.com/hoojo/archive/2011/04/22/2025197.html
public class SerializeJSON {

	public static void main(String[] args) {
		SerializeJSON serializeJSON = new SerializeJSON();
		serializeJSON.writeJSON();
		serializeJSON.readJSON();
	}

	public void writeJSON(){
		XStream xStream = new XStream(new DomDriver());
		Person person = new Person("geniushehe", 16);
		try {
			FileOutputStream fos = new FileOutputStream("json.js");
			xStream.setMode(XStream.NO_REFERENCES);
			xStream.alias("Person", Person.class);
			xStream.toXML(person, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void readJSON(){
		XStream xStream = new XStream(new DomDriver());
		Person person = null;
		try {
			FileInputStream fis = new FileInputStream("json.js");
			xStream.setMode(XStream.NO_REFERENCES);
			xStream.alias("Person", Person.class);
			person = (Person)xStream.fromXML(fis);
			System.out.println(person.getName());
			System.out.println(person.getAge());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}