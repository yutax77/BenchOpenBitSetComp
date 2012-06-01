package com.yutax77.BenchBitmapComp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.lucene.util.OpenBitSet;
import org.junit.Test;

public class BenchBitmapCompTest {
	@Test
	public void testTargetBitmap(){
		OpenBitSet bitmap = BenchBitmapComp.createBitmap(10, 10);
		
		assertNotNull(bitmap);
		assertEquals(64, bitmap.length());
		assertEquals(10, bitmap.cardinality());
	}

	@Test
	public void testTargetBitmap2(){
		OpenBitSet bitmap = BenchBitmapComp.createBitmap(10000, 5000);
		
		assertNotNull(bitmap);
		assertEquals(10048, bitmap.length());
		assertEquals(5000, bitmap.cardinality());
	}
}
