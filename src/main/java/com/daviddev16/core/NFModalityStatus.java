package com.daviddev16.core;

import java.time.LocalDateTime;

import com.daviddev16.util.Util;

public final class NFModalityStatus {

	private NFModality modality;
	private TimeState timeState;
	private float statusTime;
	/* usado para verificar quanto tempo tem de diferença desde a ultima atualização */
	private LocalDateTime lastTimeUpdated;

	private NFModalityStatus(NFModality modality, TimeState timeState, float statusTime) {
		this.modality = modality;
		this.timeState = timeState;
		this.statusTime = statusTime;
	}

	public static NFModalityStatus.Builder create() {
		return new Builder();
	}

	public void setTimeState(TimeState timeState) {
		this.timeState = timeState;
	}
	
	public void setStatusTime(float statusTime) {
		this.statusTime = statusTime;
	}

	public void setLastTimeUpdated(LocalDateTime lastTimeUpdated) {
		this.lastTimeUpdated = lastTimeUpdated;
	}
	
	private void setModality(NFModality modality) {
		this.modality = modality;
	}
	
	public LocalDateTime getLastTimeUpdated() {
		return lastTimeUpdated;
	}

	public NFModality getModality() {
		return modality;
	}

	public TimeState getTimeState() {
		return timeState;
	}
	
	public float getStatusTime() {
		return statusTime;
	}

	public static final class Builder {

		private final NFModalityStatus nfModalityStatus;

		public Builder() {
			nfModalityStatus = new NFModalityStatus(NFModality.NFE, TimeState.DOWN, -1);
			nfModalityStatus.setLastTimeUpdated(LocalDateTime.now());
		}

		public Builder initialTimeState(TimeState timeState) {
			nfModalityStatus.setTimeState(timeState);
			return this;
		}
		
		public Builder statusTime(float statusTime) {
			nfModalityStatus.setStatusTime(statusTime);
			return this;
		}

		public Builder nfModality(NFModality nfModality) {
			nfModalityStatus.setModality(Util.nonNull(nfModality, "nfModality"));
			return this;
		}

		public NFModalityStatus getNfStatus() {
			return nfModalityStatus;
		}

	}

}
