package com.yutax77.BenchBitmapComp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import lzma.streams.LzmaOutputStream;

public class LzmaArchiver implements Archiver {

	@Override
	public byte[] compress(byte[] input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		LzmaOutputStream comp = new LzmaOutputStream.Builder(out)
								.useMediumDictionarySize()
								.useEndMarkerMode(true)
								.useBT4MatchFinder()
								.build();
		//out.write(input);
		comp.write(input);
		comp.close();
		
		return out.toByteArray();
	}

	@Override
	public byte[] decompress(byte[] input) throws IOException {
		LzmaInputStream compIn = new LzmaInputStream(new ByteArrayInputStream(input), new Decoder());
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int n = 0;
		while(-1 != (n = compIn.read(buf))){
			out.write(buf, 0, n);
		}
		
		return out.toByteArray();
	}

}
