package com.yutax77.BenchBitmapComp;

import java.io.IOException;

public class NoArchiver implements Archiver {
	public byte[] compress(byte[] input) throws IOException {
		//何もしない
		return input;
	}

	public byte[] decompress(byte[] input) throws IOException {
		//何もしない
		return input;
	}
}
