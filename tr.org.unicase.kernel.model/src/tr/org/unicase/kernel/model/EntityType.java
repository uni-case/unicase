package tr.org.unicase.kernel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "core_entity_type", schema = "unicase")
public class EntityType implements IEntity {

	@Basic
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Basic
	@Column(name = "ENTITY_TYPE")
	private String entityType;

	@Basic
	@Column(name = "CLASS_NAME")
	private String className;

	@Basic
	@Column(name = "OWNER_ID")
	private Long ownerId;

	@Basic
	@Column(name = "ORDERNO")
	private Integer orderno;

	@Basic
	@Column(name = "NAME")
	private String name;

	@Basic
	@Column(name = "STATUS")
	private Boolean status;

	@Basic
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Basic
	@Column(name = "STRING_ELEMENTS")
	private String stringElements;
	
	public EntityType() {
		
	}
	
	public EntityType(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public String getDescription() {
		return description;
	}

	public String getEntityType() {
		return entityType;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public int compareTo(IEntity o) {
		return 0;
	}

	public String getStringElements() {
		return stringElements;
	}

	public void setStringElements(String stringElements) {
		this.stringElements = stringElements;
	}

	@Transient
	public List<String> getStringElementsAsList() {
		List<String> result = new ArrayList<String>();
		if (stringElements != null && !stringElements.trim().isEmpty()) {
			String[] strings = stringElements.split(",");
			for (String string : strings)
				result.add(string.trim());
		} else {
			result.add("code");
			result.add("value");
		}
		
		return result;
	}

	@Override
	@Transient
	public String getLock() {
		return null;
	}

	@Override
	@Transient
	public Boolean isLocked() {
		return false;
	}

	@Override
	public void setLock(String lock) {
		
	}

	@Override
	public IEntity getCk_owner() {
		return null;
	}
}
