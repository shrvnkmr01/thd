package com.homer.runner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import com.homer.setup.RunManager;

public class HomerRunner {

	private static JsonWriterFactory FACTORY_INSTANCE;
	public static void main(String[] args) throws Exception {	
		
		//Use below method to set PIP UI class to be called - AB testing
		//GenericUtil.setPIPUIClass();
		
		//Use below method to set AUT features - AUT testing
		//GenericUtil.setAUTSwitch();
		RunManager rm = new RunManager();
		rm.runTestCases(args);
		
		System.out.println("Automation execution completed successfully");
			
		System.exit(0);
	}
}
