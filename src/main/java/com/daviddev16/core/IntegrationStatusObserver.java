package com.daviddev16.core;

import com.daviddev16.MainApplication;

import static com.daviddev16.core.Placeholders.*;

public class IntegrationStatusObserver implements StatusObserver {

	public final static String GOOGLE_CHAT_SPACE_ID = "googlechat.space.id";
	public final static String GOOGLE_CHAT_WEBHOOK_KEY = "googlechat.space.webhook.key";
	
	private final GoogleChatWebhookIntegration webhookIntegration;
	
	public IntegrationStatusObserver() {
		String spaceId = Config.get(GOOGLE_CHAT_SPACE_ID);
		String webhookKey = Config.get(GOOGLE_CHAT_WEBHOOK_KEY);
		this.webhookIntegration = new GoogleChatWebhookIntegration(spaceId, webhookKey);
	}
	
	@Override
	public void onStatusChanged(NFModality nfModality, Estado estado, TimeState oldState, TimeState newState, float statusTime) {
		
		final String baseCardJson = Config.get(Config.INTERNAL_BASE_CARD_CONTENT);
		EstadoType estadoType = estado.getEstadoType();

		String jsonText = new PlaceholderResolver(baseCardJson)
				.assign(type(NF_MODALITY, NAME), nfModality.name())
				.assign(type(NF_MODALITY, EXTENDED), nfModality.getExtendedName())
				
				.assign(type(ESTADO, EXTENDED), estadoType.getExtendedName())
				.assign(type(ESTADO, STATUS_TIME), statusTime)
				
				.assign(type(OLD_TIME_STATE, DISPLAY_NAME), oldState.getDisplayName())
				.assign(type(OLD_TIME_STATE, TEXT_COLOR), oldState.getHexColor())
				
				.assign(type(NEW_TIME_STATE, DISPLAY_NAME), newState.getDisplayName())
				.assign(type(NEW_TIME_STATE, TEXT_COLOR), newState.getHexColor())
				.assign(type(NEW_TIME_STATE, AVAILABILITY), newState.getAvailability())
				
				.assign(type(APPLICATION, VERSION), MainApplication.version())
				.resolved();
		
		 getGoogleChatSpace().sendJson(jsonText);
	}
	
	@Override
	public void onCriticalStatus(NFModality nfModality, Estado estado, TimeState criticalState, float statusTime) {
		// TODO: Adicionar card customizado para estado critico
		onStatusChanged(nfModality, estado, criticalState, criticalState, statusTime);
	}
	
	@Override
	public void onEnabled() {
		final String bannerCardJson = Config.get(Config.INTERNAL_BANNER_CARD_CONTENT);
		getGoogleChatSpace().sendJson(bannerCardJson);
	}
	
	public GoogleChatWebhookIntegration getGoogleChatSpace() {
		return webhookIntegration;
	}
	
	@Override @Deprecated
	public boolean alertUnavailabilityOnStart() {
		return true;
	}

	

}
