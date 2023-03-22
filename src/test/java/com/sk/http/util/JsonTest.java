package com.sk.http.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class JsonTest {

	@Test
	void test() throws IOException {
		File file = new File("src/main/resources/http.json");
		System.out.println(file.exists());
		URL resource = getClass().getClassLoader().getResource("http.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));

		StringBuilder builder = new StringBuilder();
		
		String line = "";
		while((line = reader.readLine()) != null) {
			builder.append(line);
			builder.append("\n");
		}
		reader.close();
		
		String json = builder.toString();
		ObjectMapper mapper = new ObjectMapper();
		Configuration readValue = mapper.readValue(json, Configuration.class);
		
		System.out.println(builder.toString());
		System.out.println(readValue);
		
	}

}
