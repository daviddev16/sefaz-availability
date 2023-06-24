package com.daviddev16.core;

public interface StatusObserver {

	/**
	 * Evento que dispara quando a aplicação tiver carregada e pronta para utilização
	 **/
	void onEnabled();
	
	/**
	 * Evento que dispara quando houver uma troca de estado no modelo de nota 
	 * fiscal relacionado ao evento.
	 **/
	void onStatusChanged( NFModality nfModality, Estado estado, TimeState oldState, TimeState newState, float statusTime );
	
	/**
	 * Evento dispara quando a modalidade fica mais de um periodo de tempo em estado DOWN ou TIMEOUT
	 **/
	void onCriticalStatus( NFModality nfModality, Estado estado, TimeState criticalState, float statusTime  );

	/**
	 * Executar onStatusChanged() caso ao iniciar classe, a disponibilidade
	 * estava marcada como false.
	 **/
	boolean alertUnavailabilityOnStart();

}
