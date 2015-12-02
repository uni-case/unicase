package tr.org.unicase.reference.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import tr.org.unicase.kernel.model.AbstractEntity;

@Entity
@Table(name = "core_keyvalue", schema = "unicase")
public class ReferenceEntity extends AbstractEntity {

	@Basic
	@Column(name = "CODE")
	private String code;
	@Basic
	@Column(name = "VALUE")
	private String value;
	@Basic
	@Column(name = "SHORTVALUE")
	private String shortvalue;
	@Basic
	@Column(name = "DESCRIPTION")
	private String description;
	@Basic
	@Column(name = "ORDERNO")
	private String orderno;
	@Basic
	@Column(name = "STATUS")
	private String status;
	
	@Basic
	@Column(name = "LOCKED")
	private String lock;

	@ManyToOne
	@JoinColumn(name = "CK_OWNER")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_owner;

	@ManyToOne
	@JoinColumn(name = "CK_01")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_01;
	@ManyToOne
	@JoinColumn(name = "CK_02")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_02;
	@ManyToOne
	@JoinColumn(name = "CK_03")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_03;
	@ManyToOne
	@JoinColumn(name = "CK_04")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_04;
	@ManyToOne
	@JoinColumn(name = "CK_05")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_05;
	@ManyToOne
	@JoinColumn(name = "CK_06")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_06;
	@ManyToOne
	@JoinColumn(name = "CK_07")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_07;
	@ManyToOne
	@JoinColumn(name = "CK_08")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_08;
	@ManyToOne
	@JoinColumn(name = "CK_09")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_09;
	@ManyToOne
	@JoinColumn(name = "CK_10")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_10;
	@ManyToOne
	@JoinColumn(name = "CK_11")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_11;
	@ManyToOne
	@JoinColumn(name = "CK_12")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_12;
	@ManyToOne
	@JoinColumn(name = "CK_13")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_13;
	@ManyToOne
	@JoinColumn(name = "CK_14")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_14;
	@ManyToOne
	@JoinColumn(name = "CK_15")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private ReferenceEntity ck_15;
	
	@Basic
	@Column(name = "DESC_01")
	private String desc_01;
	@Basic
	@Column(name = "DESC_02")
	private String desc_02;
	@Basic
	@Column(name = "DESC_03")
	private String desc_03;
	@Basic
	@Column(name = "DESC_04")
	private String desc_04;
	@Basic
	@Column(name = "DESC_05")
	private String desc_05;
	@Basic
	@Column(name = "DESC_06")
	private String desc_06;
	@Basic
	@Column(name = "DESC_07")
	private String desc_07;
	@Basic
	@Column(name = "DESC_08")
	private String desc_08;
	@Basic
	@Column(name = "DESC_09")
	private String desc_09;
	@Basic
	@Column(name = "DESC_10")
	private String desc_10;

	@Basic
	@Column(name = "INT_01")
	private Long int_01;
	@Basic
	@Column(name = "INT_02")
	private Long int_02;
	@Basic
	@Column(name = "INT_03")
	private Long int_03;
	@Basic
	@Column(name = "INT_04")
	private Integer int_04;
	@Basic
	@Column(name = "INT_05")
	private Integer int_05;
	@Basic
	@Column(name = "INT_06")
	private Integer int_06;
	@Basic
	@Column(name = "INT_07")
	private Integer int_07;
	@Basic
	@Column(name = "INT_08")
	private Integer int_08;
	@Basic
	@Column(name = "INT_09")
	private Integer int_09;
	@Basic
	@Column(name = "INT_10")
	private Integer int_10;

	@Basic
	@Column(name = "MON_01")
	private Double mon_01;
	@Basic
	@Column(name = "MON_02")
	private Double mon_02;
	@Basic
	@Column(name = "MON_03")
	private Double mon_03;
	@Basic
	@Column(name = "MON_04")
	private Double mon_04;
	@Basic
	@Column(name = "MON_05")
	private Double mon_05;
	@Basic
	@Column(name = "MON_06")
	private Double mon_06;
	@Basic
	@Column(name = "MON_07")
	private Double mon_07;
	@Basic
	@Column(name = "MON_08")
	private Double mon_08;
	@Basic
	@Column(name = "MON_09")
	private Double mon_09;
	@Basic
	@Column(name = "MON_10")
	private Double mon_10;

	@Basic
	@Column(name = "DATE_01")
	private Date date_01;
	@Basic
	@Column(name = "DATE_02")
	private Date date_02;
	@Basic
	@Column(name = "DATE_03")
	private Date date_03;
	@Basic
	@Column(name = "DATE_04")
	private Date date_04;
	@Basic
	@Column(name = "DATE_05")
	private Date date_05;

	@Basic
	@Column(name = "BYTE_01")
	@JsonIgnore
	private byte[] byte_01;

	@Basic
	@Column(name = "BYTE_01_NAME")
	private String byte_01_name;

	@Basic
	@Column(name = "VALUE_01")
	private String value_01;
	@Basic
	@Column(name = "VALUE_02")
	private String value_02;
	@Basic
	@Column(name = "VALUE_03")
	private String value_03;
	@Basic
	@Column(name = "VALUE_04")
	private String value_04;
	@Basic
	@Column(name = "VALUE_05")
	private String value_05;
	@Basic
	@Column(name = "VALUE_06")
	private String value_06;
	@Basic
	@Column(name = "VALUE_07")
	private String value_07;
	@Basic
	@Column(name = "VALUE_08")
	private String value_08;
	@Basic
	@Column(name = "VALUE_09")
	private String value_09;
	@Basic
	@Column(name = "VALUE_10")
	private String value_10;
	
	public ReferenceEntity() {

	}

	public ReferenceEntity(Long id) {
		setId(id);

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getShortvalue() {
		return shortvalue;
	}

	public void setShortvalue(String shortvalue) {
		this.shortvalue = shortvalue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ReferenceEntity getCk_owner() {
		return ck_owner;
	}

	public void setCk_owner(ReferenceEntity ck_owner) {
		this.ck_owner = ck_owner;
	}

	public ReferenceEntity getCk_01() {
		return ck_01;
	}

	public void setCk_01(ReferenceEntity ck_01) {
		this.ck_01 = ck_01;
	}

	public ReferenceEntity getCk_02() {
		return ck_02;
	}

	public void setCk_02(ReferenceEntity ck_02) {
		this.ck_02 = ck_02;
	}

	public ReferenceEntity getCk_03() {
		return ck_03;
	}

	public void setCk_03(ReferenceEntity ck_03) {
		this.ck_03 = ck_03;
	}

	public ReferenceEntity getCk_04() {
		return ck_04;
	}

	public void setCk_04(ReferenceEntity ck_04) {
		this.ck_04 = ck_04;
	}

	public ReferenceEntity getCk_05() {
		return ck_05;
	}

	public void setCk_05(ReferenceEntity ck_05) {
		this.ck_05 = ck_05;
	}

	public ReferenceEntity getCk_06() {
		return ck_06;
	}

	public void setCk_06(ReferenceEntity ck_06) {
		this.ck_06 = ck_06;
	}

	public ReferenceEntity getCk_07() {
		return ck_07;
	}

	public void setCk_07(ReferenceEntity ck_07) {
		this.ck_07 = ck_07;
	}

	public ReferenceEntity getCk_08() {
		return ck_08;
	}

	public void setCk_08(ReferenceEntity ck_08) {
		this.ck_08 = ck_08;
	}

	public ReferenceEntity getCk_09() {
		return ck_09;
	}

	public void setCk_09(ReferenceEntity ck_09) {
		this.ck_09 = ck_09;
	}

	public ReferenceEntity getCk_10() {
		return ck_10;
	}

	public void setCk_10(ReferenceEntity ck_10) {
		this.ck_10 = ck_10;
	}

	public ReferenceEntity getCk_11() {
		return ck_11;
	}
	public void setCk_11(ReferenceEntity ck_11) {
		this.ck_11 = ck_11;
	}
	public ReferenceEntity getCk_12() {
		return ck_12;
	}
	public void setCk_12(ReferenceEntity ck_12) {
		this.ck_12 = ck_12;
	}
	
	public ReferenceEntity getCk_13() {
		return ck_13;
	}
	public void setCk_13(ReferenceEntity ck_13) {
		this.ck_13 = ck_13;
	}

	public ReferenceEntity getCk_14() {
		return ck_14;
	}
	public void setCk_14(ReferenceEntity ck_14) {
		this.ck_14 = ck_14;
	}
	public ReferenceEntity getCk_15() {
		return ck_15;
	}
	public void setCk_15(ReferenceEntity ck_15) {
		this.ck_15 = ck_15;
	}
	public String getDesc_01() {
		return desc_01;
	}

	public void setDesc_01(String desc_01) {
		this.desc_01 = desc_01;
	}

	public String getDesc_02() {
		return desc_02;
	}

	public void setDesc_02(String desc_02) {
		this.desc_02 = desc_02;
	}

	public String getDesc_03() {
		return desc_03;
	}

	public void setDesc_03(String desc_03) {
		this.desc_03 = desc_03;
	}

	public String getDesc_04() {
		return desc_04;
	}

	public void setDesc_04(String desc_04) {
		this.desc_04 = desc_04;
	}

	public String getDesc_05() {
		return desc_05;
	}

	public void setDesc_05(String desc_05) {
		this.desc_05 = desc_05;
	}

	public String getDesc_06() {
		return desc_06;
	}

	public void setDesc_06(String desc_06) {
		this.desc_06 = desc_06;
	}

	public String getDesc_07() {
		return desc_07;
	}

	public void setDesc_07(String desc_07) {
		this.desc_07 = desc_07;
	}

	public String getDesc_08() {
		return desc_08;
	}

	public void setDesc_08(String desc_08) {
		this.desc_08 = desc_08;
	}

	public String getDesc_09() {
		return desc_09;
	}

	public void setDesc_09(String desc_09) {
		this.desc_09 = desc_09;
	}

	public String getDesc_10() {
		return desc_10;
	}

	public void setDesc_10(String desc_10) {
		this.desc_10 = desc_10;
	}

	public Long getInt_01() {
		return int_01;
	}

	public void setInt_01(Long int_01) {
		this.int_01 = int_01;
	}

	public Long getInt_02() {
		return int_02;
	}

	public void setInt_02(Long int_02) {
		this.int_02 = int_02;
	}

	public Long getInt_03() {
		return int_03;
	}

	public void setInt_03(Long int_03) {
		this.int_03 = int_03;
	}

	public Integer getInt_04() {
		return int_04;
	}

	public void setInt_04(Integer int_04) {
		this.int_04 = int_04;
	}

	public Integer getInt_05() {
		return int_05;
	}

	public void setInt_05(Integer int_05) {
		this.int_05 = int_05;
	}

	public Integer getInt_06() {
		return int_06;
	}

	public void setInt_06(Integer int_06) {
		this.int_06 = int_06;
	}

	public Integer getInt_07() {
		return int_07;
	}

	public void setInt_07(Integer int_07) {
		this.int_07 = int_07;
	}

	public Integer getInt_08() {
		return int_08;
	}

	public void setInt_08(Integer int_08) {
		this.int_08 = int_08;
	}

	public Integer getInt_09() {
		return int_09;
	}

	public void setInt_09(Integer int_09) {
		this.int_09 = int_09;
	}

	public Integer getInt_10() {
		return int_10;
	}

	public void setInt_10(Integer int_10) {
		this.int_10 = int_10;
	}

	public Double getMon_01() {
		return mon_01;
	}

	public void setMon_01(Double mon_01) {
		this.mon_01 = mon_01;
	}

	public Double getMon_02() {
		return mon_02;
	}

	public void setMon_02(Double mon_02) {
		this.mon_02 = mon_02;
	}

	public Double getMon_03() {
		return mon_03;
	}

	public void setMon_03(Double mon_03) {
		this.mon_03 = mon_03;
	}

	public Double getMon_04() {
		return mon_04;
	}

	public void setMon_04(Double mon_04) {
		this.mon_04 = mon_04;
	}

	public Double getMon_05() {
		return mon_05;
	}

	public void setMon_05(Double mon_05) {
		this.mon_05 = mon_05;
	}

	public Double getMon_06() {
		return mon_06;
	}

	public void setMon_06(Double mon_06) {
		this.mon_06 = mon_06;
	}

	public Double getMon_07() {
		return mon_07;
	}

	public void setMon_07(Double mon_07) {
		this.mon_07 = mon_07;
	}

	public Double getMon_08() {
		return mon_08;
	}

	public void setMon_08(Double mon_08) {
		this.mon_08 = mon_08;
	}

	public Double getMon_09() {
		return mon_09;
	}

	public void setMon_09(Double mon_09) {
		this.mon_09 = mon_09;
	}

	public Double getMon_10() {
		return mon_10;
	}

	public void setMon_10(Double mon_10) {
		this.mon_10 = mon_10;
	}

	public Date getDate_01() {
		return date_01;
	}

	public void setDate_01(Date date_01) {
		this.date_01 = date_01;
	}
	
	public void setDate_01(String date_01) {
		try {
			Date uploadedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date_01);
			setDate_01(uploadedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getDate_02() {
		return date_02;
	}

	public void setDate_02(Date date_02) {
		this.date_02 = date_02;
	}

	public void setDate_02(String date_02) {
		try {
			Date uploadedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date_02);
			setDate_02(uploadedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Date getDate_03() {
		return date_03;
	}

	public void setDate_03(Date date_03) {
		this.date_03 = date_03;
	}
	
	public void setDate_03(String date_03) {
		try {
			Date uploadedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date_03);
			setDate_03(uploadedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getDate_04() {
		return date_04;
	}

	public void setDate_04(Date date_04) {
		this.date_04 = date_04;
	}

	public void setDate_04(String date_04) {
		try {
			Date uploadedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date_04);
			setDate_04(uploadedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getDate_05() {
		return date_05;
	}

	public void setDate_05(Date date_05) {
		this.date_05 = date_05;
	}

	public void setDate_05(String date_05) {
		try {
			Date uploadedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date_05);
			setDate_05(uploadedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] getByte_01() {
		return byte_01;
	}

	public void setByte_01(byte[] byte_01) {
		this.byte_01 = byte_01;
	}
	

	public String getValue_01() {
		return value_01;
	}

	public void setValue_01(String desc_01) {
		this.value_01 = desc_01;
	}

	public String getValue_02() {
		return value_02;
	}

	public void setValue_02(String desc_02) {
		this.value_02 = desc_02;
	}

	public String getValue_03() {
		return value_03;
	}

	public void setValue_03(String desc_03) {
		this.value_03 = desc_03;
	}

	public String getValue_04() {
		return value_04;
	}

	public void setValue_04(String desc_04) {
		this.value_04 = desc_04;
	}

	public String getValue_05() {
		return value_05;
	}

	public void setValue_05(String desc_05) {
		this.value_05 = desc_05;
	}

	public String getValue_06() {
		return value_06;
	}

	public void setValue_06(String desc_06) {
		this.value_06 = desc_06;
	}

	public String getValue_07() {
		return value_07;
	}

	public void setValue_07(String desc_07) {
		this.value_07 = desc_07;
	}

	public String getValue_08() {
		return value_08;
	}

	public void setValue_08(String desc_08) {
		this.value_08 = desc_08;
	}

	public String getValue_09() {
		return value_09;
	}

	public void setValue_09(String desc_09) {
		this.value_09 = desc_09;
	}

	public String getValue_10() {
		return value_10;
	}

	public void setValue_10(String desc_10) {
		this.value_10 = desc_10;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Transient
	private String getValueAsString(String fieldName) {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + "" + fieldName.substring(1);
		try {
			Method method = this.getClass().getMethod(methodName);
			Object invoke = method.invoke(this);
			if (invoke instanceof ReferenceEntity)
				return ((ReferenceEntity) invoke).toString();
			return (String) invoke;
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}

	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		List<String> list = this.getEntityTypeId().getStringElementsAsList();

		String valueAsString = null;
		for (String element : list) {
			valueAsString = getValueAsString(element);
			buffer.append((valueAsString == null ? "" : valueAsString + " "));
		}

		return buffer.toString().trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof ReferenceEntity)) {
			return false;
		}
		if (this.getId() == null || ((ReferenceEntity) obj).getId() == null) {
			return false;
		}
		ReferenceEntity k = (ReferenceEntity) obj;

		return this.getId().equals(k.getId());

	}

	public String getByte_01_name() {
		return byte_01_name;
	}

	public void setByte_01_name(String byte_01_name) {
		this.byte_01_name = byte_01_name;
	}

	@Override
	public String getLock() {
		return lock;
	}
	
	public void setLock(String lock) {
		this.lock = lock;
	}

	@Override
	public Boolean isLocked() {
		boolean result = this.lock != null && !this.lock.trim().isEmpty() && this.lock.equals("111");
		return result;
	}

}
