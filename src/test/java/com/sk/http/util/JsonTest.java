package com.sk.http.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.http.config.Configuration;
import com.sk.http.config.ConfigurationManager;

class JsonTest {
	
	@Test
	void json() throws Exception {
		ConfigurationManager manager = ConfigurationManager.INSTANCE;
		Configuration configuration = manager.configuration();
		assertEquals(configuration.getPort(), 8080);
	}
}