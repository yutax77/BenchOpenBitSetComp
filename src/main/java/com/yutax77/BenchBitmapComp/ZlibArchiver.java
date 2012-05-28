package com.yutax77.BenchBitmapComp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class ZlibArchiver implements Archiver {

	public byte[] compress(byte[] input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Deflater comp = new Deflater();
		comp.setLevel(Deflater.BEST_COMPRESSION);
		
		DeflaterOutputStream dos = new DeflaterOutputStream(out, comp);
		
		dos.write(input);
		dos.finish();
		return out.toByteArray();
	}

	public byte[] decompress(byte[] input) throws IOException {
		Inflater decomp = new Inflater();
		decomp.setInput(input);
		ByteArrayOutputStream decomped = new ByteArrayOutputStream();
		
		byte[] buf = new byte[4096];
		int count = 0;
		while(!decomp.finished()) {
			try {
				count = decomp.inflate(buf);
			} catch (DataFormatException e) {
				e.printStackTrace();
			}
			decomped.write(buf, 0, count);
		}
		
		return decomped.toByteArray();
	}
}
