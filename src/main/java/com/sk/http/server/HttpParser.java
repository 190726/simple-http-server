package com.sk.http.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
	
	private static final int SP = 0x20;//32 , 스페이스
	private static final int CR = 0x0D;//13, 캐리지 리턴(해당 첫 처음으로)
	private static final int LF = 0x0A;//10, LineFeed(줄바꿈)
	
	public HttpRequest parseHttpRequest(InputStream inputStream) {
		InputStreamReader reader = new InputStreamReader(inputStream);
		HttpRequest request = new HttpRequest();
		
		try {
			HttpRequest parseRequestLine = parseRequestLine(reader, request);
			System.out.println(parseRequestLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HttpRequest();
	}

	//HTTP request first line parsing
	public HttpRequest parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
		
		HttpRequest httpRequest = new HttpRequest();
		StringBuilder builder = new StringBuilder();
		
		boolean methodParsed = false;
		boolean requestTargetParsed = false;
		
		int _byte;
		
		while((_byte = reader.read()) >=0 ) {
			
			if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                	httpRequest.setHttpVersion(builder.toString());
                	break;
                } else {
                	throw new HttpParsingException();
                }
            }
            if (_byte == SP) {
                if (!methodParsed) {
                	httpRequest.setMethod(builder.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                	httpRequest.setTarget(builder.toString());
                    requestTargetParsed = true;
                }
                builder.delete(0, builder.length());
            } else {
            	builder.append((char)_byte);
                if (!methodParsed) {
                    if (builder.length() > 4) {
                    	throw new HttpParsingException();
                    }
                }
            }
		}
		return httpRequest;
	}
	
	public static void main(String[] args) throws IOException {
		
	}
	
	
}
