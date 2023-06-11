package com.daviddev16.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.daviddev16.nf.Estado;
import com.daviddev16.nf.NFModality;
import com.daviddev16.nf.StatusObserver;

public class IntegrationStatusObserver implements StatusObserver {

	private final GoogleIntegrationService integrationService;

	public IntegrationStatusObserver() {
		integrationService = new GoogleIntegrationService();
	}

	@Override
	public void onStatusChanged(NFModality nfModality, Estado estado, boolean newStatus) {
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

	@Override
	public boolean alertUnavailabilityOnStart() {
		return true;
	}

	public GoogleIntegrationService getIntegrationService() {
		return integrationService;
	}

}
