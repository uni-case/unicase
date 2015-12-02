package tr.org.unicase.kernel.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "core_field", schema = "unicase")
public class Field implements IEntity {

	@Basic
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Basic
	@Column(name = "LABEL_TEXT")
	private String labelText;

	@Basic
	@Column(name = "NAME")
	private String name;

	@Basic
	@Column(name = "ENTITY_TYPE_ID")
	private Long entityTypeId;

	@Basic
	@Column(name = "WIDTH")
	private String width;

	@Basic
	@Column(name = "HEIGHT")
	private String height;

	@Basic
	@Column(name = "VISIBLE")
	private Boolean visible;
	
	@Basic
	@Column(name = "READONLY")
	private String readonly;

	@Basic
	@Column(name = "COLUMN_INDEX")
	private Integer columnIndex;

	@Basic
	@Column(name = "REF_TYPE_ID")
	private Long refTypeId;

	@Basic
	@Column(name = "TYPE")
	private String type;

	@Basic
	@Column(name = "REF_COLUMN")
	private String refColumn;

	@Basic
	@Column(name = "PARENT_TYPE_ID")
	private Long parentTypeId;
	
	@Basic
	@Column(name = "ORDERNO")
	private Long orderNo;

	@Basic
	@Column(name = "DEPENDS")
	private String depends;
	
	@Basic
	@Column(name = "SEARCHABLE")
	private String searchable;
	
	@Basic
	@Column(name = "NOTNULL")
	private String notNull;
	
	@Basic
	@Column(name = "REPORT_ALIAS")
	private String reportAlias;
	
	@Basic
	@Column(name = "VALIDATE")
	private String validate;
	
	@Basic
	@Column(name = "VALIDATION_REGEX")
	private String validationRegex;
	
	@Basic
	@Column(name = "VALIDATION_ERROR_MESSAGE")
	private String validationErrorMessage;
	
	@Basic
	@Column(name = "DETAIL_ICON")
	private String detailIcon;
	
	@Basic
	@Column(name = "VISIBLE_AS_COLUMN")
	private String visibleInTableColumn;
	
	@Basic
	@Column(name = "GENERATED")
	private String generated;
	
	@Transient
	private Generate generate;
	
	public Field() {
		labelText = "id";
		name = "id";
		visible = false;
		width = "150px";
		height = "24px";
		searchable = "0";
		notNull = "0";
		refTypeId = null;
		setValidate("0");
		setVisibleInTableColumn(AbstractEntity.TRUE);
		setValidationErrorMessage("");
		setDetailIcon("details.png");
		setGenerated("0");
		setReadonly("0");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public String getReadonly() {
		return readonly;
	}
	
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	public Long getRefTypeId() {
		return refTypeId;
	}

	public void setRefTypeId(Long refTypeId) {
		this.refTypeId = refTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRefColumn() {
		return refColumn;
	}

	public void setRefColumn(String refColumn) {
		this.refColumn = refColumn;
	}
	public Long getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Long parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	@Override
	public int compareTo(IEntity arg0) {
		Field field = (Field) arg0;
		return this.getOrderNo().compareTo(field.getOrderNo());
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	public String getSearchable() {
		return searchable;
	}

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public String getNotNull() {
		return notNull;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}
	
	public String getreportAlias() {
		return reportAlias;
	}

	public void setreportAlias(String reportAlias) {
		this.reportAlias = reportAlias;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
	
	public void setValidationRegex(String regex) {
		this.validationRegex = regex;
	}

	public String getValidationRegex() {
		return this.validationRegex;
	}

	public String getValidationErrorMessage() {
		return validationErrorMessage;
	}

	public void setValidationErrorMessage(String validationErrorMessage) {
		this.validationErrorMessage = validationErrorMessage;
	}

	public String getDetailIcon() {
		return detailIcon;
	}

	public void setDetailIcon(String detailIcon) {
		this.detailIcon = detailIcon;
	}

	@Override
	public String toString() {
		return "Field [id=" + id + ", labelText=" + labelText + ", name=" + name + ", entityTypeId=" + entityTypeId + "]";
	}

	public String getVisibleInTableColumn() {
		return visibleInTableColumn;
	}

	public void setVisibleInTableColumn(String visibleInTableColumn) {
		this.visibleInTableColumn = visibleInTableColumn;
	}

	public String getGenerated() {
		return generated;
	}

	public Boolean isReadonly() {
		return (getReadonly().equals(AbstractEntity.TRUE));
	}
	
	public Boolean isGenerated() {
		return (getGenerated().equals(AbstractEntity.TRUE));
	}
	
	public void setGenerated(String generated) {
		this.generated = generated;
	}

	public Generate getGenerate() {
		return generate;
	}

	public void setGenerate(Generate generate) {
		this.generate = generate;
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
