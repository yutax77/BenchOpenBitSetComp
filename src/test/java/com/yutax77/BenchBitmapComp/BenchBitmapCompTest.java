package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.apache.lucene.util.OpenBitSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BenchBitmapCompTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTargetBitmap(){
		BenchBitmapComp bencher = new BenchBitmapComp(10, 10);
		OpenBitSet bitmap = bencher.createBitmap();
		
		assertNotNull(bitmap);
		assertEquals(64, bitmap.length());
		assertEquals(10, bitmap.cardinality());
	}
	
	@Test
	public void testConvertToByte() throws IOException{
		long[] longArr = new long[1];
		longArr[0] = 1;
		
		byte[] expected = new byte[8];
		expected[0] = 0;
		expected[1] = 0;
		expected[2] = 0;
		expected[3] = 0;
		expected[4] = 0;
		expected[5] = 0;
		expected[6] = 0;
		expected[7] = 1;
		
		BenchBitmapComp bencher = new BenchBitmapComp(10, 10);
		byte[] actual = bencher.convertToByte(longArr);
		
		assertEquals(8, actual.length);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testConvertToLong() throws IOException{
		byte[] byteArr = new byte[8];
		byteArr[0] = 0;
		byteArr[1] = 0;
		byteArr[2] = 0;
		byteArr[3] = 0;
		byteArr[4] = 0;
		byteArr[5] = 0;
		byteArr[6] = 0;
		byteArr[7] = 1;
		
		long[] expected = new long[1];
		expected[0] = 1;
		
		BenchBitmapComp bencher = new BenchBitmapComp(10, 10);
		long[] actual = bencher.convertToLong(byteArr);
		
		assertEquals(1, actual.length);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testCompAndDecomp() throws IOException, DataFormatException{
		long[] longArr = new long[1];
		longArr[0] = 1;

		BenchBitmapComp bencher = new BenchBitmapComp(10, 10);
		byte[] actual = bencher.convertToCompByte(longArr);
		long[] decomped = bencher.convertFromCompByteToLong(actual);
		
		assertEquals(1, decomped.length);
		assertArrayEquals(longArr, decomped);
	}
}
