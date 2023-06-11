package com.daviddev16.nf;

public enum EstadoType {

	AC ("Acre"),
	AL ("Alagoas"),
	AP ("Amapá"),
	AM ("Amazonas"),
	BA ("Bahia"),
	CE ("Ceará"),
	ES ("Espírito Santo"),
	GO ("Goiás"),
	MA ("Maranhão"),
	MT ("Mato Grosso"),
	MS ("Mato Grosso do Sul"),
	MG ("Minas Gerais"),
	PA ("Pará"),
	PB ("Paraíba"),
	PR ("Paraná"),
	PE ("Pernambuco"),
	PI ("Piauí"),
	RJ ("Rio de Janeiro"),
	RN ("Rio Grande do Norte"),
	RS ("Rio Grande do Sul"),
	RO ("Rondônia"),
	RR ("Roraima"),
	SC ("Santa Catarina"),
	SP ("São Paulo"),
	SE ("Sergipe"),
	TO ("Tocantins");

	private String displayName;

	private EstadoType(String displayName) {
		this.displayName = displayName;
	}
	
	public static EstadoType findByWorkerId(String workerId, NFModality nfModality) {
		for (EstadoType estadoType : values()) {
			if (estadoType.asWorkerId(nfModality).equalsIgnoreCase(workerId)) {
				return estadoType;
			}
		}
		return null;
	}

	public boolean checkNfCompatibility(NFModality modality) {
		if ( (this == SC || this == CE) && modality == NFModality.NFCE ) {
			return false;
		}
		return true;
	}

	/* Esse nome será utilizado para relacionar com o "id_worker" da API do monitor */
	public String asWorkerId(NFModality nfModality) {
		return String.format("sefaz_%s_envio_%s", nfModality.name(), this.name());
	}
	
	public String getCustomName() {
		return String.format("%s - %s", getDisplayName(), name());
	}

	public String getDisplayName() {
		return displayName;
	}
}
