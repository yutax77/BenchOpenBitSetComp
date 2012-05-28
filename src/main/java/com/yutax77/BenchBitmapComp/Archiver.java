package com.yutax77.BenchBitmapComp;

import java.io.IOException;

public interface Archiver {
	public byte[] compress(byte[] input) throws IOException;
	public byte[] decompress(byte[] input) throws IOException;
}
