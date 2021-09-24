package com.minhojang.ilikethispagebackend.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class IOUtils {

	public static String inputStreamToString(InputStream input, Charset charset) {
		return new BufferedReader(new InputStreamReader(input, charset))
				.lines()
				.collect(Collectors.joining("\n"));
	}

}
