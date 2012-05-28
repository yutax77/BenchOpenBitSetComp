package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ZlibArchiverTest {
	@Test
	public void testCompressAndDecompress() throws IOException {
		ZlibArchiver archiver = new ZlibArchiver();
		
		byte[] input = new byte[1];
		input[0] = 1;
		
		byte[] comp = archiver.compress(input);
		assertNotNull(comp);
		
		byte[] decomp = archiver.decompress(comp);
		assertNotNull(decomp);
		
		assertArrayEquals(input, decomp);
	}
}
