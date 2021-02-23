package com.shanmugavadivu.project.jsontoxml;

public class XMLJSONConverterMain {
	public static void main(String argv[]) {
		try {
			
			ConverterFactory factory = new ConverterFactory();
			XMLJSONConverterI convertedObj = factory.getInstance("CSWFormat");
			convertedObj.createXMLJSONConverter("C:\\Users\\Priyanga\\eclipse-workspace\\CSWASSIGNMENT\\com\\shanmugavadivu\\project\\jsontoxml\\inputjson.txt", "C:\\Users\\Priyanga\\eclipse-workspace\\CSWASSIGNMENT\\com\\shanmugavadivu\\project\\jsontoxml\\outputxml.txt");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
