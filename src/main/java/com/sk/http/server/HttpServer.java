package com.sk.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sk.http.config.Configuration;
import com.sk.http.config.ConfigurationManager;

public class HttpServer {
	
	private final static Logger log = LoggerFactory.getLogger(HttpServer.class);
	
	public static void main(String[] args) {
		log.info("Http Server Starting...");
		
		Configuration conf = ConfigurationManager.INSTANCE.configuration();
		log.info("server Port : " + conf.getPort());
		log.info("server WebRoot : " + conf.getWebroot());
		
		try {
			ServerSocket serverSocket = new ServerSocket(conf.getPort());
			Socket socket = null;
			while(!Thread.interrupted()) {
				socket = serverSocket.accept();
				log.info("connnection accepted: {}", socket.getInetAddress());
				
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				
				String html = """
						<!DOCTYPE html>
						<html lang="ko">
						<head>
						    <meta charset="UTF-8">
						    <title>Document</title>
						</head>
						<body>
						    Simple Http Server
						</body>
						</html>
						""";
				final String CRLF = "\n\r";
				String response = String.format("""
						HTTP/1.1 200 OK
						Content-Length: %d %s
						%s %s
						""", html.getBytes().length,CRLF, html, CRLF);
				
				System.out.println(response);
				outputStream.write(response.getBytes());
				outputStream.close();
			}
		} catch (IOException e) {
			throw new HttpServerLoadException("Starting Failed");
		}
	}
}
