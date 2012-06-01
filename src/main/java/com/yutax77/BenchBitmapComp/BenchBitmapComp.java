package com.yutax77.BenchBitmapComp;

import java.io.IOException;
import java.util.Random;
import java.util.zip.DataFormatException;

import org.apache.lucene.util.OpenBitSet;

public class BenchBitmapComp {
	private static final int REPEAT_NUM = 10;
	
	public static void main(String[] args) throws IOException, DataFormatException{
		//圧縮方式
		//Bitmapのサイズ
		//カーディナリティ
		if(args.length < 3)
			throw new IllegalArgumentException();
		
		int size = Integer.parseInt(args[1]);
		int cardinality = Integer.parseInt(args[2]);
		
		System.out.println("Type: " + args[0] +", Size: " + size + ", Cardinality: " + cardinality);
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
				n = rdm.nextInt(size);				
			}while(result.fastGet(n));
			
			result.fastSet(n);
		}
		
		return result;
	}
	
	private static void showResults(String name, long elapsedNanoTime){
		System.out.println(name + ", " + elapsedNanoTime / 1000 /1000);
	}
	
	private Archiver archiver;
	private OpenBitSet bitmap;
	
	public BenchBitmapComp(Archiver archiver, OpenBitSet bitmap){
		this.archiver = archiver;
		this.bitmap = bitmap;
	}
	
	public void bench() throws IOException, DataFormatException {
		for(int i = 0; i < REPEAT_NUM; i++){
			//圧縮
			long startComp = System.nanoTime();
			byte[] byteArray = Converter.convertToByte(bitmap.getBits());
			
			//dumpByte(byteArray);

			byte[] comp = archiver.compress(byteArray);
			long endComp = System.nanoTime();
			System.out.println("Bytes: " + comp.length);
			showResults("Comp(ms)", (endComp - startComp));
			
			//解凍
			long startFromCompToLong = System.nanoTime();
			byte[] decomp = archiver.decompress(comp);
			Converter.convertToLong(decomp);
			long endFromCompToLong = System.nanoTime();
			showResults("Decomp(ms)", (endFromCompToLong - startFromCompToLong));			
		}
	}

	void dumpByte(byte[] in) {
		for(int i = 0; i < in.length; i++){
			System.err.print(in[i]);
		}
		System.err.println();
	}
}
