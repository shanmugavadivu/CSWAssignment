package com.shanmugavadivu.project.jsontoxml;

import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.json.simple.parser.JSONParser;

public class CSWFormatConverter implements XMLJSONConverterI {

	@Override
	public void createXMLJSONConverter(String jsonfilepath, String xmlfilepath) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootelement = null;
			
			JSONParser parser = new JSONParser();
			Object parseobj = parser.parse(new FileReader(jsonfilepath));
			doc = checkforsinglevalue(parseobj, doc, rootelement);
			if (doc == null) {
				doc = dBuilder.newDocument();
				JSONObject obj = (JSONObject) parseobj;
				System.out.println(obj.get("car") instanceof Integer);
				doc = createxmlforJSONObject(obj, doc, rootelement);
			}
			getXMLResult(doc, xmlfilepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Document checkforsinglevalue(Object parseobj, Document doc, Element rootelement) {
		if (parseobj instanceof Integer) {
			rootelement = doc.createElement("number");
		} else if (parseobj instanceof Boolean) {
			rootelement = doc.createElement("boolean");
		} else if (parseobj instanceof String) {
			rootelement = doc.createElement("string");
		}
		if (rootelement != null) {
			rootelement.appendChild(doc.createTextNode(parseobj.toString()));
			doc.appendChild(rootelement);
			return doc;
		}
		return null;
	}
	Document createxmlforJSONObject(JSONObject obj, Document doc, Element rootelement) {
		
		if(rootelement == null) {
			rootelement = doc.createElement("object");
			doc.appendChild(rootelement);
		}
		for (Object keyStr : obj.keySet()) {
			Element nextelement = null;
			if (obj.get(keyStr) == null) {
				nextelement = doc.createElement("null");
			} else if (obj.get(keyStr) instanceof Long || obj.get(keyStr) instanceof Integer) {
				nextelement = doc.createElement("number");
			} else if (obj.get(keyStr) instanceof Boolean) {
				nextelement = doc.createElement("boolean");
			} else if (obj.get(keyStr) instanceof String) {
				nextelement = doc.createElement("string");
			} else if (obj.get(keyStr) instanceof JSONObject) {
				nextelement = doc.createElement("object");
				Attr attr = doc.createAttribute("name");
				attr.setValue(keyStr.toString());
				nextelement.setAttributeNode(attr);
				rootelement.appendChild(nextelement);
				rootelement = nextelement;
				JSONObject subObj = (JSONObject) obj.get(keyStr);
				createxmlforJSONObject(subObj, doc, rootelement);
				continue;
			} else if (obj.get(keyStr) instanceof JSONArray) {
				nextelement = doc.createElement("array");
				Attr attr = doc.createAttribute("name");
				attr.setValue(keyStr.toString());
				nextelement.setAttributeNode(attr);
				JSONArray arr = (JSONArray) obj.get(keyStr);
				Element arrayelement = null;
				for (int i = 0; i < arr.size(); i++) {
					if (arr.get(i) instanceof Long || arr.get(i) instanceof Integer) {
						arrayelement = doc.createElement("number");
						arrayelement.appendChild(doc.createTextNode(arr.get(i).toString()));
					} else if (arr.get(i) instanceof String) {
						arrayelement = doc.createElement("string");
						arrayelement.appendChild(doc.createTextNode(arr.get(i).toString()));
					} else if (arr.get(i) instanceof Boolean) {
						arrayelement = doc.createElement("boolean");
						arrayelement.appendChild(doc.createTextNode(arr.get(i).toString()));
					} else if (arr.get(i) instanceof JSONObject) {
						arrayelement = doc.createElement("object");
						nextelement.appendChild(arrayelement);
						JSONObject subObj = (JSONObject) arr.get(i);
						createxmlforJSONObject(subObj, doc, arrayelement);
					}
					nextelement.appendChild(arrayelement);
				}
				rootelement.appendChild(nextelement);
				continue;
			} 
			Attr attr = doc.createAttribute("name");
			attr.setValue(keyStr.toString());
			nextelement.setAttributeNode(attr);
			if (obj.get(keyStr) != null) {
				nextelement.appendChild(doc.createTextNode(obj.get(keyStr).toString()));
			} else {
				nextelement.appendChild(doc.createTextNode(null));
			}
			rootelement.appendChild(nextelement);
		}
		return doc;
	}

	void getXMLResult(Document doc, String filepath) {
		try {
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			 StreamResult result = new StreamResult(new File(filepath));
			 transformer.transform(source, result);
			// Output to console for testing
			 StreamResult consoleResult = new StreamResult(System.out);
			 transformer.transform(source, consoleResult);
			//StringWriter writer = new StringWriter();
			//StreamResult xmlresult = new StreamResult(writer);
			//transformer.transform(source, xmlresult);
			//return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
