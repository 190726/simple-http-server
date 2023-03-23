package com.sk.http.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.http.util.Json;

public enum ConfigurationManager {
	
	//use ENUM for singleton
	INSTANCE;
	
	private Configuration configuration;
	
	public Configuration configuration() {
		if(configuration==null) {
			loadConfigurationFile();
		}
		return configuration;
	}

	void loadConfigurationFile() {
		URL resource = getClass().getClassLoader().getResource("http.json");
		String json = "";
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));) {
			StringBuilder builder = new StringBuilder();
			String line = "";
			while((line = reader.readLine()) != null) 
				builder.append(line + "\n");
			
			json = builder.toString();
		} catch (IOException e) {
			throw new IllegalStateException("http.json file loading failed", e);
		}
		Json<Configuration> mapper = new Json<>();
		this.configuration = mapper.convertObject(json, Configuration.class);
	}
}