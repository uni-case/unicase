package tr.org.unicase.graylog2.log.internals;

import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageBuilder;

import tr.org.unicase.graylog2.log.config.Configuration;
import tr.org.unicase.graylog2.log.config.GrayLog2Config;
import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.log.api.IUnicaseLogService;
import tr.org.unicase.log.api.LogEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GrayLog2LogServiceImpl implements IUnicaseLogService {

	@Override
	public void log(LogEvent logEvent) {
		GelfMessage message = convertLogEvent(logEvent);
		try {
			GrayLog2Config.getInstance().send(message);
		} catch (InterruptedException e) {
			System.out.println("GrayLog2 uzerine bilgiler gonderilemedi ");
			e.printStackTrace();
		}
	}
	
	private GelfMessage convertLogEvent(LogEvent logEvent) {
		GelfMessageBuilder messageBuilder = GrayLog2Config.getInstance().getMessageBuilder()
				.message(logEvent.getAction())
				.additionalField(Configuration.FIELDS.IP, logEvent.getIp())
				.additionalField(Configuration.FIELDS.USER, logEvent.getUserName())
				.additionalField(Configuration.FIELDS.TYPE, logEvent.getType().name())
				.additionalField(Configuration.FIELDS.DATE, logEvent.getCreateDate())
				.additionalField(Configuration.FIELDS.RESULT, logEvent.getResult())
				.additionalField(Configuration.FIELDS.ACTION, logEvent.getAction());
		if (logEvent.getEntity() != null) 
			messageBuilder = messageBuilder.additionalField(Configuration.FIELDS.ENTITY, convert(logEvent.getEntity()));
		return messageBuilder.build();
	}
	
	private String convert(IEntity entity) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String string = mapper.writeValueAsString(entity);
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return "HATA";
		}
	}

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

}
