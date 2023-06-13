package com.daviddev16;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddev16.api.MonitorAPI;
import com.daviddev16.core.Config;
import com.daviddev16.core.Estado;
import com.daviddev16.core.EstadoType;
import com.daviddev16.core.IntegrationStatusObserver;
import com.daviddev16.core.NFModality;
import com.daviddev16.core.StatusObserver;
import com.daviddev16.core.TimeState;
import com.daviddev16.core.Versioned;
import com.daviddev16.util.Util;

@Versioned(id = "1.0.1-SNAPSHOT")
public final class MainApplication {

	private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);
	
	private static final String SPACE_ID_OPTION = "space-id";
	private static final String WEBHOOK_KEY_OPTION = "webhook-key";
	private static final String FETCH_TIME_OPTION = "fetch-time";
	
	public static void main(String[] args) {

		try {
			
			LOG.info("\n\n  _____ _______________ _____      ______________  ________  _______    ___ ____   ____ \r\n"
					+ "  / ___// ____/ ____/   /__  /     / ___/_  __/   |/_  __/ / / / ___/   <  // __ \\ / __ \\\r\n"
					+ "  \\__ \\/ __/ / /_  / /| | / /      \\__ \\ / / / /| | / / / / / /\\__ \\    / // / / // / / /\r\n"
					+ " ___/ / /___/ __/ / ___ |/ /__    ___/ // / / ___ |/ / / /_/ /___/ /   / // /_/ // /_/ / \r\n"
					+ "/____/_____/_/   /_/  |_/____/   /____//_/ /_/  |_/_/  \\____//____/   /_(_)____(_)____/  \r\n"
					+ "                                                                                         \n\n");
			LOG.info("Iniciando configurações.");
			
			if (args.length > 0) {
			
				LOG.info("Foi detectado o uso de argumentos na aplicação, os valores passados como argumento serão usados.");
			
				DefaultParser parser = new DefaultParser();
				Options options = new Options();
				
				options.addOption(Option.builder()
						.longOpt(SPACE_ID_OPTION)
						.desc("O id do espaço no Google Chat")
						.required(true)
						.hasArg(true)
						.build());
			
				options.addOption(Option.builder()
						.longOpt(WEBHOOK_KEY_OPTION)
						.desc("A chave do webhook do espaço no Google Chat")
						.required(true)
						.hasArg(true)
						.build());
				
				options.addOption(Option.builder()
						.longOpt(FETCH_TIME_OPTION)
						.desc("Tempo de atualização/busca na API")
						.type(Number.class)
						.required(true)
						.hasArg(true)
						.build());
				
				final CommandLine commandLine = parser.parse(options, args);
				
				String spaceId = commandLine.getOptionValue(SPACE_ID_OPTION);
				String webhookKey = commandLine.getOptionValue(WEBHOOK_KEY_OPTION);
				Number fetchTime = ((Number)commandLine.getParsedOptionValue(FETCH_TIME_OPTION));
				
				Config.set(IntegrationStatusObserver.GOOGLE_CHAT_SPACE_ID, spaceId);
				Config.set(IntegrationStatusObserver.GOOGLE_CHAT_WEBHOOK_KEY, webhookKey);
				Config.set(Config.GLOBAL_FETCH_TIME, fetchTime);
				
				
				LOG.info("A chave do webhook e id do espaço foram alterados.");
				
			} else {
				Config.initialize("./services.json");
			}

			
			LOG.info("Carregando card de apresentação.");
			String bannerCardJsonContent = Util.read(MainApplication.class.getResourceAsStream("/banner_card.json"));
			Config.set(Config.INTERNAL_BANNER_CARD_CONTENT, bannerCardJsonContent);
			
			LOG.info("Carregando card de template interno.");
			String cardJsonContent = Util.read(MainApplication.class.getResourceAsStream("/base_card.json"));
			Config.set(Config.INTERNAL_BASE_CARD_CONTENT, cardJsonContent);
			
			
			LOG.info("Preparando observador e fetch time...");
			final Number fetchTime = Config.get( Config.GLOBAL_FETCH_TIME );
			final StatusObserver observer = new IntegrationStatusObserver();

			final Map<EstadoType, Estado> estados = new HashMap<>();

			LOG.info("Iniciando ciclos de atualização.");
			LOG.info("Fetch time definido para {} minutos.", fetchTime);

			observer.onEnabled();
			
			while (true) {
				
				for (NFModality nfModality : NFModality.values()) {
					
					MonitorAPI.fetchAllWorkers(nfModality, (estadoType, statusTime) -> 
					{
						TimeState timeState = TimeState.getState(statusTime);
						Estado estado = estados.get(estadoType);

						if (estado == null) {
							estado = new Estado(estadoType, observer);	
							estados.put(estadoType, estado);
						}
						
						estado.setTimeState(nfModality, timeState, statusTime);
					
					});
				}
				
				Thread.sleep(TimeUnit.MINUTES.toMillis(fetchTime.longValue()));
			}
			
		} catch (IOException | InterruptedException | ParseException e) {
			LOG.info("Ciclo interrompido.");
			LOG.error(e.getMessage(), e);
			Runtime.getRuntime().exit(-1);
		}
	}
	
	public static String version() {
		Versioned versionedAnn = MainApplication.class.getAnnotation(Versioned.class);
		return versionedAnn != null ? versionedAnn.id() : "-/-";
	}
	

}
