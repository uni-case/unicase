package tr.org.unicase.kernel.model;

import java.io.Serializable;

public interface IEntity extends Serializable, Comparable<IEntity> {

	Long getId();
	
	String getLock();
	
	void setLock(String lock);	
	
	Boolean isLocked();
	
	IEntity getCk_owner();

}
