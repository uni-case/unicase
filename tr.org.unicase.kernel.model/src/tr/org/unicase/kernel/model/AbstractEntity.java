package tr.org.unicase.kernel.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements IEntity {

	public static final String FALSE = "0";
	public static final String TRUE = "1";

	public AbstractEntity() {
		entityTypeId = new EntityType();
	}

	@Basic
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ENTITY_TYPE_ID")
	private EntityType entityTypeId;

	@Override
	public Long getId() {
		return id;
	}

	public int compareTo(IEntity o) {
		return 0;

	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntityType getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(EntityType entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	
}
