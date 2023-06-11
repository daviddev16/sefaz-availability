package com.daviddev16.core;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.daviddev16.nf.EstadoType;
import com.daviddev16.nf.NFModality;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public final class MonitorAPI {

	public static final String MONITOR_ENDPOINT = "https://monitor.tecnospeed.com.br/monitores?stateStatus=true&doc=";

	private static final HttpClient CLIENT = HttpClient.newHttpClient();
	private static final Gson GSON = new Gson();

	public static void fetchAllWorkers(NFModality nfModality, BiConsumer<EstadoType, Float> statusesConsumer) throws IOException, InterruptedException {

		String nfModalityName = Check.nonNull(nfModality, "nfModality").name();
		URI apiEndpointUri = URI.create(MONITOR_ENDPOINT + nfModalityName);

		HttpRequest request = HttpRequest.newBuilder(apiEndpointUri)
				.header("accept", "application/json; charset=UTF-8")
				.GET()
				.build();

		HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

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

	public static Map<EstadoType, Float> fetchAllWorkers(NFModality nfModality) throws IOException, InterruptedException {
		Map<EstadoType, Float> workerStatuses = new HashMap<>();
		fetchAllWorkers(nfModality, (estadoType, status) -> { workerStatuses.put(estadoType, status); });
		return workerStatuses;
	}

}
