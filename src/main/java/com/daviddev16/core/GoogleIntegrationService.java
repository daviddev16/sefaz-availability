package com.daviddev16.core;

public final class GoogleIntegrationService {

	public final static String GOOGLE_CHAT_SPACE_ID = "googlechat.space.id";
	public final static String GOOGLE_CHAT_WEBHOOK_KEY = "googlechat.space.webhook.key";
	
	private final GoogleChatWebhookIntegration webhookIntegration;
	
	public GoogleIntegrationService() {
		String spaceId = Config.get(GOOGLE_CHAT_SPACE_ID);
		String webhookKey = Config.get(GOOGLE_CHAT_WEBHOOK_KEY);
		this.webhookIntegration = new GoogleChatWebhookIntegration(spaceId, webhookKey);
	}
	
	public void sendMessage(String message) {
		getWebhookIntegration().sendMessage(message);
	}
	
	private GoogleChatWebhookIntegration getWebhookIntegration() {
		return webhookIntegration;
	}
	
}
