package com.mrbysco.trainonthewaterboatonatrack.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BoatCartRenderer<T extends Boat> extends EntityRenderer<T> {
	private static final ResourceLocation MINECART_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/minecart.png");
	protected final EntityModel<T> model;
	private final BlockRenderDispatcher blockRenderer;

	public BoatCartRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context);
		this.shadowRadius = 0.7F;
		this.model = new MinecartModel<>(context.bakeLayer(layer));
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(-45F - entityYaw));
		long i = (long)entity.getId() * 493286711L;
		i = i * i * 4392167121L + i * 98761L;
		float f = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f1 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f2 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		poseStack.translate(f, f1 + 0.18F, f2);
		double d0 = Mth.lerp((double)partialTicks, entity.xOld, entity.getX());
		double d1 = Mth.lerp((double)partialTicks, entity.yOld, entity.getY());
		double d2 = Mth.lerp((double)partialTicks, entity.zOld, entity.getZ());
		Vec3 vec3 = entity.getPosition(partialTicks);
		float f3 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
		if (vec3 != null) {
			Vec3 vec31 = vec3.add(0.3F, 0, 0.3F);
			Vec3 vec32 = vec3.add(-0.3F, 0, -0.3F);
			if (vec31 == null) {
				vec31 = vec3;
			}

			if (vec32 == null) {
				vec32 = vec3;
			}

			poseStack.translate(vec3.x - d0, (vec31.y + vec32.y) / 2.0 - d1, vec3.z - d2);
			Vec3 vec33 = vec32.add(-vec31.x, -vec31.y, -vec31.z);
			if (vec33.length() != 0.0) {
				vec33 = vec33.normalize();
				entityYaw = (float)(Math.atan2(vec33.z, vec33.x) * 180.0 / Math.PI);
				f3 = (float)(Math.atan(vec33.y) * 73.0);
			}
		}

		poseStack.translate(0.0F, 0.375F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
		poseStack.mulPose(Axis.ZP.rotationDegrees(-f3));
		float f5 = (float)entity.getHurtTime() - partialTicks;
		float f6 = entity.getDamage() - partialTicks;
		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f5 > 0.0F) {
			poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f5) * f5 * f6 / 10.0F * (float)entity.getHurtDir()));
		}

		if(entity instanceof ChestBoat) {
			int j = 6;
			BlockState blockstate = Blocks.CHEST.defaultBlockState();
			if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
				poseStack.pushPose();
				poseStack.scale(0.75F, 0.75F, 0.75F);
				poseStack.translate(-0.5F, (float)(j - 8) / 16.0F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
				this.renderBoatContents(entity, partialTicks, blockstate, poseStack, buffer, packedLight);
				poseStack.popPose();
			}
		}

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		this.model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
		this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(T entity) {
		return MINECART_LOCATION;
	}

	@SuppressWarnings("deprecation")
	protected void renderBoatContents(T entity, float partialTicks, BlockState state, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		this.blockRenderer.renderSingleBlock(state, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
	}
}