package com.daviddev16;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddev16.core.Check;
import com.daviddev16.core.Config;
import com.daviddev16.core.IntegrationStatusObserver;
import com.daviddev16.core.MonitorAPI;
import com.daviddev16.nf.Estado;
import com.daviddev16.nf.EstadoType;
import com.daviddev16.nf.NFModality;
import com.daviddev16.nf.StatusObserver;

public class MainApplication {

	private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);
	
	public static void main(String[] args) {

		try {
			
			LOG.info("\n\n  _____ _______________ _____      ______________  ________  _______    ___ ____   ____ \r\n"
					+ "  / ___// ____/ ____/   /__  /     / ___/_  __/   |/_  __/ / / / ___/   <  // __ \\ / __ \\\r\n"
					+ "  \\__ \\/ __/ / /_  / /| | / /      \\__ \\ / / / /| | / / / / / /\\__ \\    / // / / // / / /\r\n"
					+ " ___/ / /___/ __/ / ___ |/ /__    ___/ // / / ___ |/ / / /_/ /___/ /   / // /_/ // /_/ / \r\n"
					+ "/____/_____/_/   /_/  |_/____/   /____//_/ /_/  |_/_/  \\____//____/   /_(_)____(_)____/  \r\n"
					+ "                                                                                         \n\n");
			LOG.info("Iniciando configurações.");
			
			Config.initialize("./internal_services.json");
			final double fetchTime = Config.get( Config.GLOBAL_FETCH_TIME );
			final StatusObserver observer = new IntegrationStatusObserver();
			final Map<EstadoType, Estado> estados = new HashMap<>();

			LOG.info("Iniciando ciclos de atualização.");
			LOG.info("Fetch time definido para {} minutos.", fetchTime);
			
			while (true) {
				
				for (NFModality nfModality : NFModality.values()) {
					
					LOG.info("Buscando atualizações da modalidade: {}.", nfModality.name());
					
					MonitorAPI.fetchAllWorkers(nfModality, (estadoType, status) -> 
					{
						boolean currentAvailability = Check.checkIsAvailable(status);
						Estado estado = estados.get(estadoType);

						if (estado == null) {
							estado = new Estado(estadoType, observer);	
							estados.put(estadoType, estado);
						}
						
						if (currentAvailability)
							estado.markAsAvailable(nfModality);
						else
							estado.markAsUnavailable(nfModality);
					
					});
				}
				Thread.sleep(TimeUnit.MINUTES.toMillis((long)fetchTime));
			}
			
		} catch (IOException | InterruptedException e) {
			LOG.info("Ciclo interrompido.");
			LOG.error(e.getMessage(), e);
			Runtime.getRuntime().exit(-1);
		}
	}

}
