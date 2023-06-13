package com.daviddev16.core;

public interface StatusObserver {

	/**
	 * Evento que dispara quando houver uma troca de estado no modelo de nota 
	 * fiscal relacionado ao evento.
	 **/
	void onStatusChanged( NFModality nfModality, Estado changed, TimeState oldState, TimeState newState );

	/**
	 * Executar onStatusChanged() caso ao iniciar classe, a disponibilidade
	 * estava marcada como false.
	 **/
	boolean alertUnavailabilityOnStart();

}
