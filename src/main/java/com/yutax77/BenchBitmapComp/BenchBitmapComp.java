package com.yutax77.BenchBitmapComp;

import java.io.IOException;
import java.util.Random;
import java.util.zip.DataFormatException;

import org.apache.lucene.util.OpenBitSet;

public class BenchBitmapComp {
	public static void main(String[] args) throws IOException, DataFormatException{
		//圧縮方式
		//Bitmapのサイズ
		//カーディナリティ
		if(args.length < 3)
			throw new IllegalArgumentException();
		
		int size = Integer.parseInt(args[1]);
		int cardinality = Integer.parseInt(args[2]);
		
		System.out.println("Size: " + size + ", Cardinality: " + cardinality);
		OpenBitSet bitmap = createBitmap(size, cardinality);
		BenchBitmapComp bench = new BenchBitmapComp(createArchiver(args[0]),
													bitmap);
		bench.bench();
	}
	
	static Archiver createArchiver(String type){
		if("NO".equalsIgnoreCase(type))
			return new NoArchiver();
		else if("ZLIB".equalsIgnoreCase(type))
			return new ZlibArchiver();
		else if("SNAPPY".equalsIgnoreCase(type))
			return new SnappyArchiver();
		else
			throw new IllegalArgumentException(type);
	}
	
	public static OpenBitSet createBitmap(int size, int cardinality) {
		OpenBitSet result = new OpenBitSet(size);
		
		Random rdm = new Random();
		for(int i = 0; i < cardinality; i++){
			int n = 0;
			do{
				n = rdm.nextInt(cardinality);				
			}while(result.fastGet(n));
			
			result.fastSet(n);
		}
		
		return result;
	}
	
	private static void showResults(String name, int length, long elapsedNanoTime){
		System.out.println(name + " " + length + " byte(s), Elapsed time: " + elapsedNanoTime / 1000 /1000 + " ms");
	}
	
	private static void showDecompResults(String name, long elapsedNanoTime){
		System.out.println(name + " Elapsed time: " + elapsedNanoTime / 1000 /1000 + " ms");
	}
	
	private Archiver archiver;
	private OpenBitSet bitmap;
	
	public BenchBitmapComp(Archiver archiver, OpenBitSet bitmap){
		this.archiver = archiver;
		this.bitmap = bitmap;
	}
	
	public void bench() throws IOException, DataFormatException {
		//圧縮
		long startComp = System.nanoTime();
		byte[] byteArray = Converter.convertToByte(bitmap.getBits());
		byte[] comp = archiver.compress(byteArray);
		long endComp = System.nanoTime();
		showResults("Comp", comp.length, (endComp - startComp));
		
		//解凍
		long startFromCompToLong = System.nanoTime();
		byte[] decomp = archiver.decompress(comp);
		long[] longArray = Converter.convertToLong(decomp);
		OpenBitSet decompedFomCompBitmap = new OpenBitSet(longArray, longArray.length);
		long endFromCompToLong = System.nanoTime();
		boolean isEqual = bitmap.equals(decompedFomCompBitmap);
		showDecompResults("From comp " + isEqual, (endFromCompToLong - startFromCompToLong));
	}

}
