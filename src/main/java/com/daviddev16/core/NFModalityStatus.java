package com.daviddev16.core;

public final class NFModalityStatus {

	private NFModality modality;
	private TimeState timeState;

	private NFModalityStatus(NFModality modality, TimeState timeState) {
		this.modality = modality;
		this.timeState = timeState;
	}

	public static NFModalityStatus.Builder create() {
		return new Builder();
	}

	public void setTimeState(TimeState timeState) {
		this.timeState = timeState;
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

	public static final class Builder {

		private final NFModalityStatus nfModalityStatus;

		public Builder() {
			nfModalityStatus = new NFModalityStatus(NFModality.NFE, TimeState.DOWN);
		}

		public Builder initialTimeState(TimeState timeState) {
			nfModalityStatus.setTimeState(timeState);
			return this;
		}

		public Builder nfModality(NFModality nfModality) {
			nfModalityStatus.setModality(Check.nonNull(nfModality, "nfModality"));
			return this;
		}

		public NFModalityStatus getNfStatus() {
			return nfModalityStatus;
		}

	}

}
