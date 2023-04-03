package com.sk.http.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("localhost", 8000);
		
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		
		//upload target path
		outputStream.writeUTF("d:/temp/test");
		
		//upload target name
		outputStream.writeUTF("java-file-socket_upload.xlsx");
		
		//upload source path+name
		File file = new File("d:/java-file-socket.xlsx");
		
		FileInputStream fis = new FileInputStream(file);
		
		long fileLengthForBytes = file.length();
		outputStream.writeLong(fileLengthForBytes);
		
		int read;
		byte[] buf = new byte[1024];
		while((read = fis.read(buf))!=-1) {
			outputStream.write(buf, 0, read);
		}
		
		outputStream.flush();
		outputStream.close();
		
		fis.close();
		socket.close();
		
		
	}
}
