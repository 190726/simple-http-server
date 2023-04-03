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
		outputStream.writeInt(1000000);
		outputStream.writeUTF("d:/temp/test");
		outputStream.writeUTF("2222_CY2023_개인목표설정_생명정보서비스팀_v1.0_20230403_배포본2.xlsx");
		File file = new File("d:/2222_CY2023_개인목표설정_생명정보서비스팀_v1.0_20230403_배포본.xlsx");
		
		FileInputStream fis = new FileInputStream(file);
		
		long fileLengthForBytes = file.length();
		outputStream.writeLong(fileLengthForBytes);
		
		int read;
		long data = 0;
		
		byte[] buf = new byte[1024];
		while((read = fis.read(buf))!=-1) {
			outputStream.write(buf, 0, read);
			data += read;
			System.out.print("=");
		}
		System.out.println(data);

		outputStream.flush();
	}
}