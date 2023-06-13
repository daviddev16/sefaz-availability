package com.daviddev16.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IntegrationStatusObserver implements StatusObserver {

	private final GoogleIntegrationService integrationService;

	public IntegrationStatusObserver() {
		integrationService = new GoogleIntegrationService();
	}
	
	/*TODO: TO REIMPLEMENT
	@Override
	public void onStatusChanged(NFModality nfModality, Estado estado, TimeState oldState, TimeState newState) {
		
		StringBuilder messageBuilder = new StringBuilder()
				.append("```")
				.append("\n")
				.append("Tipo: ")
					.append(nfModality.name() + '\n')
				.append("Estado:")
					.append(estado.getEstadoType().getCustomName() + '\n')
				.append("Status: ")
					.append((newStatus) ? " ✅ EM FUNCIONAMENTO ✅ \n" : " ⛔ FORA DO AR ⛔ \n")
				.append("Horário: ")
					.append(SimpleDateFormat.getInstance().format(new Date()))
				.append("\n")
				.append("```")
					.append("<users/all>\n");	
		
		getIntegrationService().sendMessage(messageBuilder.toString());
	}
	*/

	@Override
	public boolean alertUnavailabilityOnStart() {
		return true;
	}

	public GoogleIntegrationService getIntegrationService() {
		return integrationService;
	}

	@Override
	public void onStatusChanged(NFModality nfModality, Estado changed, TimeState oldState, TimeState newState) {}

}
