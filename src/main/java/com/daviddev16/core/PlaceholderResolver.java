package com.daviddev16.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

import static com.daviddev16.util.Util.*;

/*TODO: make a proper card builder instead */
public final class PlaceholderResolver {

	private final Map<String, Object> placeholders;
	private final String rawContent;
	
	public PlaceholderResolver(String rawContent) {
		this.rawContent = nonNull(rawContent, "rawContent");
		this.placeholders = new HashMap<>();
	}
	
	public PlaceholderResolver assign(String name, Object value) {
		this.placeholders.put(nonNull(name, "name"), nonNull(value, "value"));
		return this;
	}
	
	public String resolved() {
		return new StringSubstitutor(placeholders).replace(rawContent);
	}
	
	public String getRawContent() {
		return rawContent;
	}
	
}
