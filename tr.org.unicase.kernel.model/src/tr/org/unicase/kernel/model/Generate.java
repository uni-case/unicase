package tr.org.unicase.kernel.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "core_generate", schema = "unicase")
public class Generate implements IEntity {

	private static final String ID_FORMAT = "%05d";
	
	@Basic
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Basic
	@Column(name = "ENTITY_TYPE")
	private String entityTypeName;

	@Basic
	@Column(name = "PREFIX")
	private String prefix;

	@Basic
	@Column(name = "GENERATED")
	private Long generated;

	@Basic
	@Column(name = "SUFFIX")
	private String suffix;

	public Generate() {
		this(null, null,-1l,null);
	}
	
	public Generate(String type, String prefix, Long generated, String suffix) {
		setEntityTypeName(type);
		setPrefix(prefix);
		setGenerated(generated);
		setSuffix(suffix);
	}
	
	@Transient
	public void incrementGeneratedValue() {
		this.generated += 1;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityTypeName() {
		return this.entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getGenerated() {
		return this.generated;
	}

	public void setGenerated(Long generated) {
		this.generated = generated;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public int compareTo(IEntity otherEntity) {
		final int EQUAL = 0;

		return EQUAL;
	}

	@Override
	public String toString() {
		return "" + ((prefix != null && !prefix.isEmpty()) ? (prefix + "/"): "") + String.format(ID_FORMAT, generated) + "" + ((suffix != null && !suffix.trim().isEmpty()) ? ("/" + suffix) : "");
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
