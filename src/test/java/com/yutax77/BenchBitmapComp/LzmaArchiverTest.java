package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class LzmaArchiverTest {
	@Test
	public void testCompressAndDecompress() throws IOException {
		Archiver archiver = new LzmaArchiver();
		
		byte[] input = new byte[1];
		input[0] = 1;
		
		byte[] comp = archiver.compress(input);
		assertNotNull(comp);
		assertFalse(comp.length == 0);
		
		byte[] decomp = archiver.decompress(comp);
		assertNotNull(decomp);
		
		assertArrayEquals(input, decomp);
	}
}
