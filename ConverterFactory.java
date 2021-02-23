package com.shanmugavadivu.project.jsontoxml;

public class ConverterFactory {
	
	XMLJSONConverterI getInstance(String format) {
		if (format.equals("CSWFormat")) {
			return new CSWFormatConverter();
		}
		return null;
	}

}
