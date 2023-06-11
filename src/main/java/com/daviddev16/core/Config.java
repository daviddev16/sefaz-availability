package com.daviddev16.core;

import static com.daviddev16.core.Check.nonNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public final class Config {
	
	public static final String GLOBAL_FETCH_TIME = "global.fetch.time";

	private static Map<String, Object> configMap = Collections.synchronizedMap(new HashMap<>());

	private Config() {}
	
	public static <E> E get(String key) {
		return (E) configMap.get(nonNull(key, "key"));
	}
	
	public static void set(String key, Object value) {
		configMap.put(nonNull(key, "key"), nonNull(value, "value"));
	}
	
	public static void initialize(String configFileName) throws IOException {
		try {
			FileReader fileReader = new FileReader(nonNull(configFileName, "configFileName"));
			configMap = new Gson().fromJson(fileReader, Map.class);
		} catch (Exception e) {
			throw new IOException(String.format("Houve um erro na leitura "
					+ "do arquivo de configuração \"%s\".", configFileName), e);
		}
	}

}
