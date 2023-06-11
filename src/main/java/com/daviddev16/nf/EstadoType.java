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

	public boolean checkNfCompatibility(NFModality modality) {
		if ( (this == SC || this == CE) && modality == NFModality.NFCE ) {
			return false;
		}
		return true;
	}

	public String getDisplayName() {
		return displayName;
	}
}
