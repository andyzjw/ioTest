package com.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(ClassLoader.getSystemResourceAsStream("com/util/tt.properties"));
		System.out.println(p.get("name"));
	}
}
