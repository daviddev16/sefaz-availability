package com.daviddev16.core;

import java.awt.Dialog.ModalExclusionType;

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
				.initialTimeState(TimeState.DOWN)
				.getNfStatus();
		
		if (estadoType.checkNfCompatibility(NFModality.NFCE))
			this.nfceStatus = NFModalityStatus.create()
					.nfModality(NFModality.NFCE)
					.initialTimeState(TimeState.DOWN)
					.getNfStatus();
	}
	
	private void changeTimeState(NFModality nfModality, 
							     TimeState newTimeState) throws ModalityException {

		NFModalityStatus modalityStatus = getStatusBasedOnModality(nfModality);
		TimeState oldTimeState = modalityStatus.getTimeState();
		
		if (!isModalityCompatible(nfModality))
			throw new ModalityException("Essa modalidade de nota fiscal não está disponível "
					+ "para essa região. ", estadoType, nfModality);
		
		if (oldTimeState != newTimeState && getStatusObserver() != null)
			getStatusObserver().onStatusChanged(nfModality, this, oldTimeState, newTimeState);
		
		modalityStatus.setTimeState(newTimeState);

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
	public void setTimeState(NFModality modality, TimeState newTimeState) {
		changeTimeState(modality, newTimeState);
	}
	
	public StatusObserver getStatusObserver() {
		return statusObserver;
	}

	public EstadoType getEstadoType() {
		return estadoType;
	}
}
