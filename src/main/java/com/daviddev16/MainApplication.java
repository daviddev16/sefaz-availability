package com.daviddev16;

import com.daviddev16.nf.EstadoType;
import com.daviddev16.nf.NFModality;
import com.daviddev16.nf.StatusObserver;

public class MainApplication {

	public static void main(String[] args) {
		
		final StatusObserver observer = new StatusObserver() {
			@Override
			public void onStatusChanged(NFModality modality, Estado changed, boolean newStatus) {
				System.out.println(modality.toString() + ": "+changed.getEstadoType().getDisplayName() +": " + newStatus);
			}
			@Override
			public boolean alertUnavailabilityOnStart() {
				return false;
			}
		};
		
		for (EstadoType estados : EstadoType.values()) {
			Estado estado = new Estado(estados, observer, false);
			estado.markAsAvailable(NFModality.NFCE);
			System.out.println();
		}
	}
	
}
