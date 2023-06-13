package com.daviddev16.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

	public static <E> E nonNull(E nonNullObject, String field) {
		if (nonNullObject == null)
			throw new NullPointerException(String.format("O campo \"%s\" não pode ser nulo.", field));
		return nonNullObject;
	}
	
	public static String read(InputStream inputStream) throws IOException {
		nonNull(inputStream, "inputStream");
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line).append('\n');
		}
		return builder.toString();
	}
	
}
