package com.daviddev16.core;

public enum TimeState {

	NORMAL    (1f,  "Normal", 	      "Estável / Em operação",                "#22c55e"),
	SLOW      (3f,  "Lento",  	      "Lento / Possível Instabilidade",       "#84cc16"),
	VERY_SLOW (5f, "Muito Lento",    "Com Instabilidade",                    "#eab308"),
	TIMEOUT   (6f, "Tempo esgotado", "Inacessível / Demorou para responder", "#f59e0b"),
	DOWN      (30f, "Fora do ar",     "Fora do ar / Inacessível",             "#ef4444");
	
	private String displayName;
	private String availability;
	private String hexColor;
	private float time;
	
	private TimeState(float time, String displayName, String availability, String hexColor) {
		this.displayName = displayName;
		this.availability = availability;
		this.hexColor = hexColor;
		this.time = time;
	}

	/* TODO: arrumar depois */
	public static  TimeState getState(float statusTime) {
		if (statusTime <= 2f)
			return NORMAL;
		else if (statusTime <= 3f)
			return SLOW;
		else if (statusTime <= 4f)
			return VERY_SLOW;
		else if (statusTime <= 5f)
			return DOWN;
		
		return TIMEOUT;
	}
	
	public String getDisplayName() {
		return displayName.toUpperCase();
	}
	
	public String getAvailability() {
		return availability;
	}

	public float getTime() {
		return time;
	}
	
	public String getHexColor() {
		return hexColor;
	}
	
}
