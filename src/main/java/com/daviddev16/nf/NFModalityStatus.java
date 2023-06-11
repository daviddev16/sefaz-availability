package com.daviddev16.nf;

import com.daviddev16.Check;
import com.daviddev16.Estado;

public final class NFModalityStatus {

	private NFModality modality;
	private boolean available;

	private NFModalityStatus(NFModality modality, boolean available) {
		this.modality = modality;
		this.available = available;
	}

	public static NFModalityStatus.Builder create() {
		return new Builder();
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	private void setModality(NFModality modality) {
		this.modality = modality;
	}

	public NFModality getModality() {
		return modality;
	}

	public boolean isAvailable() {
		return available;
	}

	public static final class Builder {

		private final NFModalityStatus nfModalityStatus;

		public Builder() {
			nfModalityStatus = new NFModalityStatus(NFModality.NFE, false);
		}

		public Builder initialAvailability(boolean available, Estado estado, StatusObserver statusObserver) {
			Check.nonNull(estado, "estado");
			Check.nonNull(statusObserver, "statusObserver");
			if (statusObserver.alertUnavailabilityOnStart() && !available) {
				statusObserver.onStatusChanged(nfModalityStatus.getModality(), estado, false);
			}
			nfModalityStatus.setAvailable(available);
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
