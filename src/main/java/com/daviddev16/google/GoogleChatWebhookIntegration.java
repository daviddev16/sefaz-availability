package com.daviddev16.google;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import static com.daviddev16.Check.nonNull;

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

	public static void main(String[] args) {
		try {
			Map<String, Object> json = new Gson().fromJson(new FileReader("./services.json"), Map.class);
			String spaceId = json.get("googlechat.space.id").toString();
			String webhookKey = json.get("googlechat.space.webhook.key").toString();
			GoogleChatWebhookIntegration integration = new GoogleChatWebhookIntegration(spaceId, webhookKey);
			final int size = 30;
			System.out.println(size);
			StringBuilder builder = new StringBuilder();
			builder.append("```");
			builder.append("\n");
			builder.append("Tipo: NFE\n");
			builder.append("Estado: Tocantins (TO)\n");
			builder.append("Status: ⛔ FORA DO AR ⛔\n");
			builder.append("Horário: " + SimpleDateFormat.getInstance().format(new Date()));
			builder.append("\n");
			builder.append("```");
			builder.append("<users/all>\n");
			integration.sendMessage(builder.toString()).ifPresent(reponse -> {
				System.out.println(reponse);
			});
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}