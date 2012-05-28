package com.yutax77.BenchBitmapComp;

import java.io.IOException;

import org.xerial.snappy.Snappy;

public class SnappyArchiver implements Archiver {
	public byte[] compress(byte[] input) throws IOException {
		return Snappy.compress(input);
	}

	public byte[] decompress(byte[] input) throws IOException {
		return Snappy.uncompress(input);
	}
}
