package com.yutax77.BenchBitmapComp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

import org.apache.lucene.util.OpenBitSet;

public class BenchBitmapComp {
	public static void main(String[] args) throws IOException, DataFormatException{
		//Bitmapのサイズ
		//占有率
		if(args.length < 2)
			throw new IllegalArgumentException();
		
		BenchBitmapComp bench = new BenchBitmapComp(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		bench.bench();
		
	}
	
	private int size;
	private int occ;
	
	public BenchBitmapComp(int size, int occ){
		this.size = size;
		this.occ = occ;
	}
	
	public void bench() throws IOException, DataFormatException {
		System.out.println("Size: " + size + ", Cardinality: " + occ);
		
		OpenBitSet bitmap = createBitmap();
		
		//圧縮
		
		long startComp = System.nanoTime();
		byte[] comp = convertToCompByte(bitmap.getBits());
		long endComp = System.nanoTime();
		showResults("Comp", comp.length, (endComp - startComp));
		
		long startNoCmp = System.nanoTime();
		byte[] nocomp = convertToByte(bitmap.getBits());
		long endNoComp = System.nanoTime();
		showResults("No Comp", nocomp.length, (endNoComp - startNoCmp));

		
		//解凍
		long startFromCompToLong = System.nanoTime();
		long[] decompedFromcomp = convertFromCompByteToLong(comp);
		OpenBitSet decompedFomCompBitmap = new OpenBitSet(decompedFromcomp, decompedFromcomp.length);
		long endFromCompToLong = System.nanoTime();
		boolean isEqual = bitmap.equals(decompedFomCompBitmap);
		showDecompResults("From comp " + isEqual, (endFromCompToLong - startFromCompToLong));
		
		long startToLong = System.nanoTime();
		long[] decomped = convertToLong(nocomp);
		OpenBitSet decompedBitmap = new OpenBitSet(decomped, decomped.length);
		long endToLong = System.nanoTime();
		isEqual = bitmap.equals(decompedBitmap);
		showDecompResults("From no comp " + isEqual, (endToLong - startToLong));

	}
	
	private static void showResults(String name, int length, long elapsedNanoTime){
		System.out.println(name + " " + length + " byte(s), Elapsed time: " + elapsedNanoTime / 1000 /1000 + " ms");
	}
	
	private static void showDecompResults(String name, long elapsedNanoTime){
		System.out.println(name + " Elapsed time: " + elapsedNanoTime / 1000 /1000 + " ms");
	}
	
	public OpenBitSet createBitmap() {
		OpenBitSet result = new OpenBitSet(size);
		
		Random rdm = new Random();
		for(int i = 0; i < occ; i++){
			int n = 0;
			do{
				n = rdm.nextInt(occ);				
			}while(result.fastGet(n));
			
			result.fastSet(n);
		}
		
		return result;
	}
	
	public byte[] convertToByte(long[] longArray) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(out);
		
		for(int i = 0; i < longArray.length; i++){
			data.writeLong(longArray[i]);
		}
		
		return out.toByteArray();
	}
	
	public long[] convertToLong(byte[] byteArray) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
		DataInputStream data = new DataInputStream(in);
		
		int length = byteArray.length / 8;
		long[] result = new long[length];
		for(int i = 0; i < length; i++){
			result[i] = data.readLong();
		}
		
		return result;
	}
	
	public byte[] convertToCompByte(long[] longArray) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Deflater comp = new Deflater();
		comp.setLevel(Deflater.BEST_COMPRESSION);
		
		DeflaterOutputStream dos = new DeflaterOutputStream(out, comp);
		DataOutputStream data = new DataOutputStream(dos);
		
		for(int i = 0; i < longArray.length; i++){
			data.writeLong(longArray[i]);
		}
		
		dos.finish();
		return out.toByteArray();
	}
	
	public long[] convertFromCompByteToLong(byte[] byteArray) throws IOException, DataFormatException {
		Inflater decomp = new Inflater();
		decomp.setInput(byteArray);
		ByteArrayOutputStream decomped = new ByteArrayOutputStream();
		
		byte[] buf = new byte[4096];
		int count = 0;
		while(!decomp.finished()) {
			count = decomp.inflate(buf);
			decomped.write(buf, 0, count);
		}
		
		byte[] decompedArray = decomped.toByteArray();
		return convertToLong(decompedArray);
	}
}
