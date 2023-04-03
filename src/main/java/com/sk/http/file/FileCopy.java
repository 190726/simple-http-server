package com.sk.http.file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
	
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("d:/win10Dev/test.txt");
		File file = new File("d:/win10Dev/test2.txt");
		file.createNewFile();
		
		int read;
		long data = 0;
		byte[] buf = new byte[1024];
		
		int length = 6160;
		
		DataInputStream dis = new DataInputStream(fis);
		
		FileOutputStream fos = new FileOutputStream(file);
		while(length > 0 && (read = dis.read(buf))!=-1) {
			fos.write(buf, 0, read);
			data += read;
			length -= read;
			fos.flush();
		}
		
	}

}
