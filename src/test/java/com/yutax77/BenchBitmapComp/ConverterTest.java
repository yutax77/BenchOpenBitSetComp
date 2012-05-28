package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConvertToByte() throws IOException {
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
		
		byte[] actual = Converter.convertToByte(longArr);
		
		assertEquals(8, actual.length);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testConvertToLong() throws IOException {
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
		
		long[] actual = Converter.convertToLong(byteArr);
		
		assertEquals(1, actual.length);
		assertArrayEquals(expected, actual);
	}
}
