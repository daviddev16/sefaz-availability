package com.daviddev16.core;


public enum TimeState {

	NORMAL    (2f, "Normal"),
	SLOW      (5f, "Lento"),
	VERY_SLOW (29f, "Muito Lento"),
	TIMEOUT   (30f, "Tempo esgotado"),
	DOWN      (31f, "Fora do ar");
	
	private float time;
	private String displayName;
	
	private TimeState(float time, String displayName) {
		this.time = time;
		this.displayName = displayName;
	}

	public static  TimeState getState(float statusTime) {
		for (TimeState tempTimeState : values()) {
			if (statusTime <= tempTimeState.getSeconds())
				return tempTimeState;
		}
		return TimeState.DOWN;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public float getSeconds() {
		return this.time;
	}

}
