package com.daviddev16.core;

public enum TimeState {

	NORMAL    (2f,  "Normal", 	      "Estável / Em operação",                "#22c55e"),
	SLOW      (5f,  "Lento",  	      "Lento / Possível Instabilidade",       "#84cc16"),
	VERY_SLOW (29f, "Muito Lento",    "Com Instabilidade",                    "#eab308"),
	TIMEOUT   (30f, "Tempo esgotado", "Inacessível / Demorou para responder", "#f59e0b"),
	DOWN      (31f, "Fora do ar",     "Fora do ar / Inacessível",             "#ef4444");
	
	private float time;
	private String displayName;
	private String availability;
	private String hexColor;
	
	private TimeState(float time, String displayName, String availability, String hexColor) {
		this.time = time;
		this.displayName = displayName;
		this.availability = availability;
		this.hexColor = hexColor;
	}

	public static  TimeState getState(float statusTime) {
		for (TimeState tempTimeState : values()) {
			if (statusTime <= tempTimeState.getSeconds())
				return tempTimeState;
		}
		return TimeState.DOWN;
	}
	
	public String getAvailability() {
		return availability;
	}

	public String getDisplayName() {
		return displayName.toUpperCase();
	}
	
	public String getHexColor() {
		return hexColor;
	}
	
	public float getSeconds() {
		return this.time;
	}

}
