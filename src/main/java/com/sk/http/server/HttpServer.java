package com.sk.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
			Thread serveListener = new Thread(new ServerListener(conf.getPort(),conf.getWebroot() ));
			serveListener.start();
		} catch (IOException e) {
			throw new HttpServerLoadException("Starting Failed");
		}
	}
	
	static class ServerListener implements Runnable{
		
		private ServerSocket serverSocket;
		
		public ServerListener(int port, String webRoot) throws IOException {
			serverSocket = new ServerSocket(port);
		}

		@Override
		public void run() {
			
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			try {
				while(serverSocket.isBound() && !serverSocket.isClosed()) {
					Socket socket = serverSocket.accept();
					log.info("connnection accepted: {}", socket.getInetAddress());
					executorService.execute(new HttpConnectionWorker(serverSocket.accept()));
				}
			}catch(IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	static class HttpConnectionWorker implements Runnable{
		
		private Socket socket;
		
		public HttpConnectionWorker(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			
			try(InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();) {
				
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
				
				outputStream.write(response.getBytes());
				
				log.info("connection reset");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
