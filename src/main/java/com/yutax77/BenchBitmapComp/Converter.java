package com.yutax77.BenchBitmapComp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Converter {
	private Converter(){}
	
	public static byte[] convertToByte(long[] longArray) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(out);
		
		for(int i = 0; i < longArray.length; i++){
			data.writeLong(longArray[i]);
		}
		
		return out.toByteArray();
	}

	public static long[] convertToLong(byte[] byteArray) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
		DataInputStream data = new DataInputStream(in);
		
		int length = byteArray.length / 8;
		long[] result = new long[length];
		for(int i = 0; i < length; i++){
			result[i] = data.readLong();
		}
		
		return result;
	}
}
