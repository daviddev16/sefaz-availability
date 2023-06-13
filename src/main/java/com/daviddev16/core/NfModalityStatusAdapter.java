package com.daviddev16.core;

public abstract class NfModalityStatusAdapter {

	public abstract void setTimeState(NFModality modality, TimeState timeState);

	public abstract NFModalityStatus getStatusBasedOnModality(NFModality nfModality);
	
	public abstract boolean isModalityCompatible(NFModality nfModality);
	
}
