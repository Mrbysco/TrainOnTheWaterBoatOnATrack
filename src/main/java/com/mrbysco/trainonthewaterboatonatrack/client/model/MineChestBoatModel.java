package com.mrbysco.trainonthewaterboatonatrack.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;

public class MineChestBoatModel extends MineBoatModel {

	public MineChestBoatModel(ModelPart root) {
		super(root);
	}

	@Override
	protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart p_250198_) {
		ImmutableList.Builder<ModelPart> builder = super.createPartsBuilder(p_250198_);
		builder.add(p_250198_.getChild("chest_bottom"));
		builder.add(p_250198_.getChild("chest_lid"));
		builder.add(p_250198_.getChild("chest_lock"));
		return builder;
	}
}
