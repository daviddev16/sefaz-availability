package com.daviddev16.core;

import com.daviddev16.util.Util;

public final class NFModalityStatus {

	private NFModality modality;
	private TimeState timeState;
	private float statusTime;

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
	
	private void setModality(NFModality modality) {
		this.modality = modality;
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
