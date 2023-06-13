package com.daviddev16.core;

public enum NFModality { 

	NFE  ("NFe (Nota Fiscal Eletrônica)"),
	NFCE ("NFCe (Nota Fiscal de Consumidor Eletrônica)");
	
	private String extendedName;
	
	private NFModality(String extendedName) {
		this.extendedName = extendedName;
	}
	
	public String getExtendedName() {
		return extendedName;
	}
}
