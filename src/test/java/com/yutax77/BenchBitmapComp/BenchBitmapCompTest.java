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
}
