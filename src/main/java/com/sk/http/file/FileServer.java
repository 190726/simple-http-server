package com.sk.http.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileServer {
	
	private final static Logger logger = LoggerFactory.getLogger(FileServer.class);
	private final static int BUFFER_SIZE = 1024;
	
	static class FileTransferWorker implements Runnable{
		
		private Socket socket;
		
		public FileTransferWorker(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String dirPath = in.readUTF();
				String fileName = in.readUTF();
				long fileLengthForBytes = in.readLong();
				
				Path newDir = Paths.get(dirPath);
				if(Files.notExists(newDir)) {
					Files.createDirectories(newDir);
					logger.info("created new dir, " + dirPath);
				}
				
				Path fullPath = Paths.get(dirPath, fileName);
				
				byte[] buffer = new byte[BUFFER_SIZE];
				int read;
				int toRead = BUFFER_SIZE;
				
				FileOutputStream fos = new FileOutputStream(fullPath.toAbsolutePath().toFile());
				
				while(fileLengthForBytes > 0 && (read = in.read(buffer, 0, toRead))!=-1) {
					
					fos.write(buffer, 0, read);
					
					fileLengthForBytes -= read;
					
					//남은 데이터가 버퍼보다 작은 경우, 남은 데이터만큼 읽어내기 위함
					if(fileLengthForBytes < BUFFER_SIZE) {
						toRead = (int) fileLengthForBytes;
					}
					fos.flush();
				}
				fos.close();
						
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		try(ServerSocket serverSocket = new ServerSocket(8000)) {
			
			while(serverSocket.isBound() && !serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				logger.info("connnection accepted: {}", socket.getInetAddress());
				threadPool.execute(new FileTransferWorker(socket));
			}
			threadPool.awaitTermination(60, TimeUnit.SECONDS);
			threadPool.shutdown();
		}catch(IOException | InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		
		/*
		ServerSocket serverSocket = new ServerSocket(8000);
		Socket socket = serverSocket.accept();
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		
		int readInt = dis.readInt();
		System.out.println(readInt);
		String readStr1 = dis.readUTF();
		System.out.println(readStr1);
		String readStr2 = dis.readUTF();
		System.out.println(readStr2);
		long length = dis.readLong();
		System.out.println(length);
		int read;
		long data = 0;
		byte[] buf = new byte[1024];
		File file = new File("d:/win10Dev/test2.txt");
		file.createNewFile();
		int disread = 32;
		
		FileOutputStream fos = new FileOutputStream(file);
		while(length > 0 && (read = dis.read(buf, 0, disread))!=-1) {
			fos.write(buf, 0, read);
			data += read;
			length -= read;
			if(length < 32) {
				disread = (int) length;
			}
			fos.flush();
		}
		
		System.out.println(data);
		
		String readStr3 = dis.readUTF();
		System.out.println(readStr3);
		*/
	}

}
