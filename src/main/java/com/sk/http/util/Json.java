package com.sk.http.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json<T> {
	
	ObjectMapper mapper = new ObjectMapper();
	
	T result;
	
	public T convertObject(String json, Class<T> clazz) {
		
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new IllegalStateException("http.json parsing error", e);
		} catch (JsonMappingException e) {
			throw new IllegalStateException("http.json jsonMapping failed", e);
		} catch (IOException e) {
			throw new IllegalStateException("http.json mapped failed", e);
		}
	}

}
