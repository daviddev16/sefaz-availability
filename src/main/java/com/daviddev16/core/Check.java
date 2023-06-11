package com.daviddev16.core;

public class Check {

	public static <E> E nonNull(E nonNullObject, String field) {
		if (nonNullObject == null)
			throw new NullPointerException(String.format("O campo \"%s\" não pode ser nulo.", field));
		return nonNullObject;
	}
	
	public static boolean checkIsAvailable(float status) {
		return (status > 0 && status < 5);
	}
	
}
