# CSWAssignment
Round 2 Assignment

FILES INSIDE ZIP
- CSWASSIGNMENT –  folder consists of implemented Java files inside the package com.shanmugavadivu.project.jsontoxml
- jsonxmlconverter.jar - Final Executable jar with compiled classes of the project (compiled with jdk 1.7 version)
- json-simple-1.1.jar - Dependency jar
- manifest.txt - File with main class and class path information. This file is bundled while creating jsonxmlconverter.jar
- inputjson.txt - Text file which contains JSON to be given as input
- outputxml.txt - Text file to which output xml result is written
HOW TO RUN - Run the below command
java -jar jsonxmlconverter.jar {path of inputjson.txt} {path of outputxml.txt}
example:
java -jar jsonxmlconverter.jar C:\Users\Priyanga\Desktop\CSWAssignment\inputjson.txt C:\Users\Priyanga\Desktop\CSWAssignment\outputxml.txt
Check outputxml.txt for the result xml
USECASES COVERED
  - Single value json (Integer, String, Boolean, Null)
  - JSON Elements with values as Integer, String, Boolean, JSONArray, JSONObject, Null
  - Nested JSONObject and JSONArray (JSONObject inside JSONObject, JSONArray inside nested JSONObject, JSONObject inside JSONArray etc..)
  - Null values handling
DESIGN IMPLEMENTATION
  - Factory Java design pattern is implemented to hide the implemention part with client.
  - XMLJSONConverterI Interface created with method createXMLJSONConverter.
  - Factory class gives instance of the implementation class which client needs.
  - Input JSONObject is constructed by parsing the values in inputjson.txt using JSONParser.
  - DOMParser Document is used for creating xml attributes, elements, nodes.
  - Recursive calls are made for converting nested JSONObject & JSONArray values.
  - TransformerFactory is used for document transformations
  - Result XML is written to output filepath provided

