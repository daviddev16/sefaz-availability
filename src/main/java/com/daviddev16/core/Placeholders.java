package com.daviddev16.core;

public final class Placeholders {

	public static final String DISPLAY_NAME = "DISPLAY_NAME";
	public static final String SECONDS = "SECONDS";
	public static final String VERSION = "VERSION";
	public static final String NAME = "NAME";
	public static final String EXTENDED = "EXTENDED";
	public static final String APPLICATION = "APPLICATION";
	public static final String TEXT_COLOR = "TEXT_COLOR";
	public static final String STATUS_TIME = "STATUS_TIME";
	
	public static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
	
	public static final String NF_MODALITY = "NF_MODALITY";
	public static final String OLD_TIME_STATE = "OLD_TIME_STATE";
	public static final String NEW_TIME_STATE = "NEW_TIME_STATE";
	public static final String RESPONSE_TIME_STATE = "RESPONSE_TIME_STATE";
	public static final String AVAILABILITY = "AVAILABILITY";
	public static final String ESTADO = "ESTADO";
	
	public static String type(String original, String option) {
		return original.concat(":").concat(option);
	}
	
}
