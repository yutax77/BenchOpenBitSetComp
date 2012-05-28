package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class SnappyArchiverTest {
	@Test
	public void testCompressAndDecompress() throws IOException {
		Archiver archiver = new SnappyArchiver();
		
		byte[] input = new byte[1];
		input[0] = 1;
		
		byte[] comp = archiver.compress(input);
		assertNotNull(comp);
		
		byte[] decomp = archiver.decompress(comp);
		assertNotNull(decomp);
		
		assertArrayEquals(input, decomp);
	}
}
