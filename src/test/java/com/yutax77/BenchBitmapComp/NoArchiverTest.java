package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class NoArchiverTest {
	private Archiver archiver;
	
	@Before
	public void setUp() throws Exception {
		archiver = new NoArchiver();
	}

	@Test
	public void testCompress() throws IOException {
		byte[] input = new byte[1];
		input[0] = 1;
		
		byte[] actual = archiver.compress(input);
		
		assertArrayEquals(input, actual);
	}

	@Test
	public void testDecompress() throws IOException {
		byte[] input = new byte[1];
		input[0] = 1;
		
		byte[] actual = archiver.decompress(input);
		
		assertArrayEquals(input, actual);
	}
}
