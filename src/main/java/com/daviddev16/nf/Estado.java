package com.daviddev16.nf;

import com.daviddev16.core.Check;

public final class Estado extends NfModalityStatusAdapter {

	private final EstadoType estadoType;

	private StatusObserver statusObserver;
	
	private NFModalityStatus nfeStatus;
	private NFModalityStatus nfceStatus;
	
	public Estado(EstadoType estadoType, StatusObserver statusObserver) {
		this.statusObserver = Check.nonNull(statusObserver, "statusObserver");
		this.estadoType = Check.nonNull(estadoType, "estadoType");
		
		this.nfeStatus = NFModalityStatus.create()
				.nfModality(NFModality.NFE)
				.initialAvailability(true)
				.getNfStatus();
		
		if (estadoType.checkNfCompatibility(NFModality.NFCE))
			this.nfceStatus = NFModalityStatus.create()
					.nfModality(NFModality.NFCE)
					.initialAvailability(true)
					.getNfStatus();
	}
	
	private void changeAvailabilityStatus(NFModality nfModality, 
										  boolean newAvailabilityStatus) throws ModalityException {

		NFModalityStatus modalityStatus = getStatusBasedOnModality(nfModality);
		
		if (!isModalityCompatible(nfModality))
			throw new ModalityException("Essa modalidade de nota fiscal não está disponível "
					+ "para essa região. ", estadoType, nfModality);
		
		if (modalityStatus.isAvailable() != newAvailabilityStatus && getStatusObserver() != null)
			getStatusObserver().onStatusChanged(nfModality, this, newAvailabilityStatus);
		
		modalityStatus.setAvailable(newAvailabilityStatus);
	
	}
	
	@Override
	public boolean isModalityCompatible(NFModality nfModality) {
		return (nfModality == NFModality.NFCE) ?  (nfceStatus != null) : (nfeStatus != null); 
	}
	
	@Override
	public NFModalityStatus getStatusBasedOnModality(NFModality nfModality) {
		return (nfModality == NFModality.NFCE) ? nfceStatus : nfeStatus;
	}
	
	@Override
	public void markAsAvailable(NFModality nfModality) {	
		changeAvailabilityStatus(nfModality, true);
	}

	@Override
	public void markAsUnavailable(NFModality nfModality) {
		changeAvailabilityStatus(nfModality, false);
	}

	public StatusObserver getStatusObserver() {
		return statusObserver;
	}

	public EstadoType getEstadoType() {
		return estadoType;
	}
}
