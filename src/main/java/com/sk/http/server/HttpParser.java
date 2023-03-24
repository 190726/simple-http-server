package com.sk.http.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpParser {
	
	private static final int SP = 0x20;//32 , 스페이스
	private static final int CR = 0x0D;//13, 캐리지 리턴(해당 첫 처음으로)
	private static final int LF = 0x0A;//10, LineFeed(줄바꿈)
	
	public HttpRequest parseHttpRequest(InputStream inputStream) {
		InputStreamReader reader = new InputStreamReader(inputStream);
		HttpRequest request = new HttpRequest();
		try {
			parseRequestLine(reader, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HttpRequest();
	}

	public void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
		//TODO
	}
	
	public static void main(String[] args) throws IOException {
		HttpParser httpParser = new HttpParser();
		httpParser.parseRequestLine(new InputStreamReader(httpParser.getSample()), new HttpRequest());
	}
	
	private InputStream getSample() {
		return new ByteArrayInputStream( 
			"""
			GET / HTTP/1.1
			Host: localhost:8080
			Connection: keep-alive
			Cache-Control: max-age=0
			sec-ch-ua: ".Not/A)Brand";v="99", "Google Chrome";v="103", "Chromium";v="103"
			sec-ch-ua-mobile: ?0
			sec-ch-ua-platform: "Windows"
			Upgrade-Insecure-Requests: 1
			User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.66 Safari/537.36
			Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
			Sec-Fetch-Site: none
			Sec-Fetch-Mode: navigate
			Sec-Fetch-User: ?1
			Sec-Fetch-Dest: document
			Accept-Encoding: gzip, deflate, br
			Accept-Language: ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7
				""".getBytes());
	}
}
