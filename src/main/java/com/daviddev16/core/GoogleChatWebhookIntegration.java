package com.daviddev16.core;

import static com.daviddev16.core.Check.nonNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;

public class GoogleChatWebhookIntegration {

	private static final String BASE_URL = "https://chat.googleapis.com/v1/spaces/";
	private static final HttpClient CLIENT = HttpClient.newHttpClient();
	private static final Gson GSON = new Gson();

	private final URI webhookUri;

	public GoogleChatWebhookIntegration(String spaceId, String webhookKey) {
		webhookUri = URI.create(createWebhookUrl(
				nonNull(spaceId, "spaceId"), 
				nonNull(webhookKey, "webhookKey")));
	}

	public synchronized Optional<String> sendMessage(String message) {
		String  jsonNessage = GSON.toJson(Map.of("text", nonNull(message, "message")));
		HttpRequest request = HttpRequest.newBuilder(webhookUri)
				.header("accept", "application/json; charset=UTF-8")
				.POST(HttpRequest.BodyPublishers.ofString(jsonNessage))
				.build();
		try {
			HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			return Optional.of(response.body());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	private String createWebhookUrl(String spaceId, String key) {
		return new StringBuilder()
				.append(BASE_URL)
				.append(spaceId.concat("/"))
				.append("messages?")
				.append("key=".concat(key))
				.toString();
	}

}