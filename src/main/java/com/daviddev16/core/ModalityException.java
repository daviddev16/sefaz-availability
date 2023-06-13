package com.daviddev16.core;

public final class ModalityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ModalityException(String message, EstadoType estadoType, NFModality nfModality) {
		super(String.format("%s [%s é incompativel com %s]", message, nfModality.name(), estadoType.getDisplayName()));
	}
	
}
