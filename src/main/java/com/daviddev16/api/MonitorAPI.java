package com.daviddev16.api;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddev16.core.EstadoType;
import com.daviddev16.core.NFModality;
import com.daviddev16.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public final class MonitorAPI {
	
	public static final int MAX_ATTEMPTS = 3;
	
	public static final Logger LOG = LoggerFactory.getLogger(MonitorAPI.class);

	public static final String MONITOR_ENDPOINT = "https://monitor.tecnospeed.com.br/monitores?stateStatus=true&doc=";

	private static final HttpClient CLIENT = HttpClient.newHttpClient();
	private static final Gson GSON = new Gson();

	private static void fetchAllWorkers(NFModality nfModality, BiConsumer<EstadoType, Float> statusesConsumer, int attempt) 
			throws IOException, InterruptedException {
		
		String nfModalityName = Util.nonNull(nfModality, "nfModality").name();
		URI apiEndpointUri = URI.create(MONITOR_ENDPOINT + nfModalityName);

		LOG.info("Iniciando busca na API [Dominio: monitor.tecnospeed.com.br] "
				+ "[Modalidade: {}] [Tentativa: {}/{}].", nfModalityName, attempt, MAX_ATTEMPTS);
		
		HttpRequest request = HttpRequest.newBuilder(apiEndpointUri)
				.timeout(Duration.ofMinutes(5))
				.header("accept", "*/*")
				.GET()
				.build();

		HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		
		if (response.statusCode() != 200 && attempt < MAX_ATTEMPTS) 
		{
			LOG.error("A API retornou o código {}, aguardando 1 segundo para tentar "
					+ "novamente novamente...", response.statusCode());
			
			Thread.sleep(TimeUnit.SECONDS.toMillis(1L));
			
			fetchAllWorkers(nfModality, statusesConsumer, attempt + 1);
			return;
		}
		
		LOG.debug(response.body());
		
		GSON.fromJson(response.body(), JsonArray.class).forEach(jsonElement -> 
		{
			JsonObject workerJsonObject = jsonElement.getAsJsonObject();
			String workerId = workerJsonObject.get("id_worker").getAsString();
			float status = workerJsonObject.get("status").getAsFloat();

			EstadoType estadoType = EstadoType.findByWorkerId(workerId, nfModality);

			if (estadoType != null && statusesConsumer != null)
				statusesConsumer.accept(estadoType, status);
		});
	}
	
	public static void fetchAllWorkers(NFModality nfModality, BiConsumer<EstadoType, Float> statusesConsumer) 
			throws IOException, InterruptedException {
		fetchAllWorkers(nfModality, statusesConsumer, 1);
	}

}
