package tr.org.unicase.reference.web.controller.activator;

import java.io.IOException;

import tr.org.unicase.reference.model.ReferenceEntity;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

public class ReferenceEntitySerializer implements StreamSerializer<ReferenceEntity> {

	@Override
	public int getTypeId() {
		return ReferenceEntity.class.getName().hashCode();
	}

	@Override
	public ReferenceEntity read(ObjectDataInput in) throws IOException {
		ReferenceEntity entity = new ReferenceEntity(in.readLong());

		entity.setCode(in.readUTF());
		entity.setValue(in.readUTF());
		entity.setShortvalue(in.readUTF());
		entity.setDescription(in.readUTF());
		entity.setOrderno(in.readUTF());

		Long owner = in.readLong();
		entity.setCk_owner(new ReferenceEntity((owner.longValue() > 0 ? owner : null)));

		Long ck_01= ((in.readLong()));
		Long ck_02= ((in.readLong()));
		Long ck_03= ((in.readLong()));
		Long ck_04= ((in.readLong()));
		Long ck_05= ((in.readLong()));
		Long ck_06= ((in.readLong()));
		Long ck_07= ((in.readLong()));
		Long ck_08= ((in.readLong()));
		Long ck_09= ((in.readLong()));
		Long ck_10= ((in.readLong()));
		Long ck_11= ((in.readLong()));
		Long ck_12= ((in.readLong()));
		Long ck_13= ((in.readLong()));
		Long ck_14= ((in.readLong()));
		Long ck_15= ((in.readLong()));
		
		entity.setCk_01(new ReferenceEntity((ck_01.longValue() > 0 ? ck_01 : null)));
		entity.setCk_02(new ReferenceEntity((ck_02.longValue() > 0 ? ck_02 : null)));
		entity.setCk_03(new ReferenceEntity((ck_03.longValue() > 0 ? ck_03 : null)));
		entity.setCk_04(new ReferenceEntity((ck_04.longValue() > 0 ? ck_04 : null)));
		entity.setCk_05(new ReferenceEntity((ck_05.longValue() > 0 ? ck_05 : null)));
		entity.setCk_06(new ReferenceEntity((ck_06.longValue() > 0 ? ck_06 : null)));
		entity.setCk_07(new ReferenceEntity((ck_07.longValue() > 0 ? ck_07 : null)));
		entity.setCk_08(new ReferenceEntity((ck_08.longValue() > 0 ? ck_08 : null)));
		entity.setCk_09(new ReferenceEntity((ck_09.longValue() > 0 ? ck_09 : null)));
		entity.setCk_10(new ReferenceEntity((ck_10.longValue() > 0 ? ck_10 : null)));
		entity.setCk_11(new ReferenceEntity((ck_11.longValue() > 0 ? ck_11 : null)));
		entity.setCk_12(new ReferenceEntity((ck_12.longValue() > 0 ? ck_12 : null)));
		entity.setCk_13(new ReferenceEntity((ck_13.longValue() > 0 ? ck_13 : null)));
		entity.setCk_14(new ReferenceEntity((ck_14.longValue() > 0 ? ck_14 : null)));
		entity.setCk_15(new ReferenceEntity((ck_15.longValue() > 0 ? ck_15 : null)));

		entity.setDesc_01(in.readUTF());
		entity.setDesc_02(in.readUTF());
		entity.setDesc_03(in.readUTF());
		entity.setDesc_04(in.readUTF());
		entity.setDesc_05(in.readUTF());
		entity.setDesc_06(in.readUTF());
		entity.setDesc_07(in.readUTF());
		entity.setDesc_08(in.readUTF());
		entity.setDesc_09(in.readUTF());
		entity.setDesc_10(in.readUTF());

		entity.setValue_01(in.readUTF());
		entity.setValue_02(in.readUTF());
		entity.setValue_03(in.readUTF());
		entity.setValue_04(in.readUTF());
		entity.setValue_05(in.readUTF());
		entity.setValue_06(in.readUTF());
		entity.setValue_07(in.readUTF());
		entity.setValue_08(in.readUTF());
		entity.setValue_09(in.readUTF());
		entity.setValue_10(in.readUTF());

		return entity;
	}

	@Override
	public void write(ObjectDataOutput out, ReferenceEntity entity) throws IOException {

		out.writeLong(entity.getId());
		out.writeUTF(entity.getCode());
		out.writeUTF(entity.getValue());
		out.writeUTF(entity.getShortvalue());
		out.writeUTF(entity.getDescription());
		out.writeUTF(entity.getOrderno());

		Long ownerId = ((entity.getCk_owner() != null && entity.getCk_owner().getId() != null)? entity.getCk_owner().getId() : -1l);
		out.writeLong(ownerId);

		Long ck1 =  ((entity.getCk_01() != null && entity.getCk_01().getId()!= null)? entity.getCk_01().getId() : -1l);
		Long ck2 =  ((entity.getCk_02() != null && entity.getCk_02().getId()!= null)? entity.getCk_02().getId() : -1l);
		Long ck3 =  ((entity.getCk_03() != null && entity.getCk_03().getId()!= null)? entity.getCk_03().getId() : -1l);
		Long ck4 =  ((entity.getCk_04() != null && entity.getCk_04().getId()!= null)? entity.getCk_04().getId() : -1l);
		Long ck5 =  ((entity.getCk_05() != null && entity.getCk_05().getId()!= null)? entity.getCk_05().getId() : -1l);
		Long ck6 =  ((entity.getCk_06() != null && entity.getCk_06().getId()!= null)? entity.getCk_06().getId() : -1l);
		Long ck7 =  ((entity.getCk_07() != null && entity.getCk_07().getId()!= null)? entity.getCk_07().getId() : -1l);
		Long ck8 =  ((entity.getCk_08() != null && entity.getCk_08().getId()!= null)? entity.getCk_08().getId() : -1l);
		Long ck9 =  ((entity.getCk_09() != null && entity.getCk_09().getId()!= null)? entity.getCk_09().getId() : -1l);
		Long ck10 = ((entity.getCk_10() != null && entity.getCk_10().getId()!= null)? entity.getCk_10().getId() : -1l);
		Long ck11 = ((entity.getCk_11() != null && entity.getCk_11().getId()!= null)? entity.getCk_11().getId() : -1l);
		Long ck12 = ((entity.getCk_12() != null && entity.getCk_12().getId()!= null)? entity.getCk_12().getId() : -1l);
		Long ck13 = ((entity.getCk_13() != null && entity.getCk_13().getId()!= null)? entity.getCk_13().getId() : -1l);
		Long ck14 = ((entity.getCk_14() != null && entity.getCk_14().getId()!= null)? entity.getCk_14().getId() : -1l);
		Long ck15 = ((entity.getCk_15() != null && entity.getCk_15().getId()!= null)? entity.getCk_15().getId() : -1l);

		out.writeLong(ck1);
		out.writeLong(ck2);
		out.writeLong(ck3);
		out.writeLong(ck4);
		out.writeLong(ck5);
		out.writeLong(ck6);
		out.writeLong(ck7);
		out.writeLong(ck8);
		out.writeLong(ck9);
		out.writeLong(ck10);
		out.writeLong(ck11);
		out.writeLong(ck12);
		out.writeLong(ck13);
		out.writeLong(ck14);
		out.writeLong(ck15);

		out.writeUTF(entity.getDesc_01());
		out.writeUTF(entity.getDesc_02());
		out.writeUTF(entity.getDesc_03());
		out.writeUTF(entity.getDesc_04());
		out.writeUTF(entity.getDesc_05());
		out.writeUTF(entity.getDesc_06());
		out.writeUTF(entity.getDesc_07());
		out.writeUTF(entity.getDesc_08());
		out.writeUTF(entity.getDesc_09());
		out.writeUTF(entity.getDesc_10());

		out.writeUTF(entity.getValue_01());
		out.writeUTF(entity.getValue_02());
		out.writeUTF(entity.getValue_03());
		out.writeUTF(entity.getValue_04());
		out.writeUTF(entity.getValue_05());
		out.writeUTF(entity.getValue_06());
		out.writeUTF(entity.getValue_07());
		out.writeUTF(entity.getValue_08());
		out.writeUTF(entity.getValue_09());
		out.writeUTF(entity.getValue_10());

	}

	@Override
	public void destroy() {

	}
}
