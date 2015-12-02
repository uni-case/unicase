package tr.org.unicase.web.app.api;

public interface IUnicaseManager<SERVICE> {
	
	public String getDescription();
	
	public void addService(SERVICE service);

	public void removeService(SERVICE service);
	
	public void clear();
	
	public int size();
	
	public String getServiceNames();
	
}
