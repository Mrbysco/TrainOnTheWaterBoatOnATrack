package com.mrbysco.trainonthewaterboatonatrack.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class MineRaftModel extends ListModel<AbstractMinecart> {
	private final ModelPart leftPaddle;
	private final ModelPart rightPaddle;
	private final ImmutableList<ModelPart> parts;

	public MineRaftModel(ModelPart root) {
		this.leftPaddle = root.getChild("left_paddle");
		this.rightPaddle = root.getChild("right_paddle");
		this.parts = this.createPartsBuilder(root).build();
	}

	protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart root) {
		ImmutableList.Builder<ModelPart> builder = new ImmutableList.Builder<>();
		builder.add(root.getChild("bottom"), this.leftPaddle, this.rightPaddle);
		return builder;
	}

	private final float[] paddlePositions = new float[2];

	/**
	 * Sets this entity's model rotation angles
	 */
	public void setupAnim(AbstractMinecart entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean isMoving = entity.getDeltaMovement().lengthSqr() > 0.2;
		if (isMoving) {
			animatePaddle(entity, 0, this.leftPaddle, limbSwing);
			animatePaddle(entity, 1, this.rightPaddle, limbSwing);

			for (int i = 0; i <= 1; i++) {
				this.paddlePositions[i] = this.paddlePositions[i] + (float) (Math.PI / 12);
			}
		}
	}

	public ImmutableList<ModelPart> parts() {
		return this.parts;
	}

	private void animatePaddle(AbstractMinecart abstractMinecart, int side, ModelPart paddle, float limbSwing) {
		float f = getRowingTime(side, limbSwing);
		paddle.xRot = Mth.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (Mth.sin(-f) + 1.0F) / 2.0F);
		paddle.yRot = Mth.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
		if (side == 1) {
			paddle.yRot = (float) Math.PI - paddle.yRot;
		}
	}

	public float getRowingTime(int side, float limbSwing) {
		return Mth.clampedLerp(this.paddlePositions[side] - (float) (Math.PI / 8), this.paddlePositions[side], limbSwing);
	}
}