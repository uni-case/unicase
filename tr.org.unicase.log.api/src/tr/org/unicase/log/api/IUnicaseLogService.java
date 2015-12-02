package tr.org.unicase.log.api;

public interface IUnicaseLogService {

	public int getFactoryId();

	public String getName();

	public void log(LogEvent logEvent);

}
