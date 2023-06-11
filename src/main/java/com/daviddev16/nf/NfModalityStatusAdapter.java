package com.daviddev16.nf;

public abstract class NfModalityStatusAdapter {

	public abstract void markAsAvailable(NFModality modality);
	
	public abstract void markAsUnavailable(NFModality modality);

	public abstract NFModalityStatus getStatusBasedOnModality(NFModality nfModality);
	
	public abstract boolean isModalityCompatible(NFModality nfModality);
	
}
