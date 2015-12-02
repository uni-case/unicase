package tr.org.unicase.log.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import tr.org.unicase.kernel.model.IEntity;

public class LogEvent {
	
	private static final String DATE_FORMAT = "dd-MM-yy:HH:mm:SS";
	
	private Types type;
	private String userName;
	private String ip;
	private String viewName;
	private String entityClass;
	private String action;
	private Date createDate;
	private IEntity entity;

	private String result;

	public LogEvent() {
		setIp(null);
		setResult(null);
		setCreateDate(new Date());
		setEntity(null);
		setUserName(null);
	}
	
	public LogEvent(String ip, String operationString) {
		setIp(ip);
		setResult(operationString);
		setCreateDate(new Date());
		setEntity(null);
	}
	
	public LogEvent(String userName, String ip, String operationString) {
		setIp(ip);
		setResult(operationString);
		setCreateDate(new Date());
		setEntity(null);
		setUserName(userName);
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private String formatDate() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.format(createDate);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("## Kullanici Adi: ");
		buffer.append(this.getUserName());
		buffer.append(", IP Adresi: ");
		buffer.append(this.getIp());
		buffer.append(", Islem Tarihi: ");
		buffer.append(formatDate());
		buffer.append(", Islem Aciklamasi: ");
		buffer.append(result);
		
		return buffer.toString();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}
	
	public String format() {
		return String.format(getType().type(), toString());
	}

	public IEntity getEntity() {
		return entity;
	}

	public void setEntity(IEntity entity) {
		this.entity = entity;
		if (this.entity != null)
			setEntityClass(this.entity.getClass().getName());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEntityClass() {
		return entityClass;
	}

	private void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public static enum Types {
		 ERROR(1, "<i><font size=\"2\" color= \"red\">%s</font></i>"), DEBUG(2, "<i><font size=\"2\" color= \"blue\">%s</font></i>"), INFO(3, "<i><font size=\"2\" color= \"gray\">%s</font></i>");

	        private String format;
	        private int id;

	        Types(int id, String aType) {
	                this.format = aType;
	        }

	        public String type() {
	                return this.format;
	        }
	        
	        public int id() {
	        	return this.id;
	        }
	        
	        

	}
	

}
