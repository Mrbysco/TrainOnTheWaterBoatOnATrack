package com.mrbysco.trainonthewaterboatonatrack.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrbysco.trainonthewaterboatonatrack.TrainOnTheWaterBoatOnATrack;
import com.mrbysco.trainonthewaterboatonatrack.client.model.MineBoatModel;
import com.mrbysco.trainonthewaterboatonatrack.client.model.MineChestBoatModel;
import com.mrbysco.trainonthewaterboatonatrack.client.model.MineRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MineBoatRenderer<T extends AbstractMinecart> extends EntityRenderer<T> {
	private final ResourceLocation boatLocation;
	private final ListModel<T> boatModel;
	private final BlockRenderDispatcher blockRenderer;

	public MineBoatRenderer(EntityRendererProvider.Context context, AbstractMinecart.Type type) {
		super(context);
		this.shadowRadius = 0.8F;
		this.boatLocation = getTextureLocation(type);
		this.boatModel = this.createBoatModel(context, type);
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	private ListModel<T> createBoatModel(EntityRendererProvider.Context context, AbstractMinecart.Type type) {
		Boat.Type boatType = getBoatType(type);
		ModelLayerLocation layerLocation = ModelLayers.createBoatModelName(boatType);
		if (type == AbstractMinecart.Type.SPAWNER) {
			TrainOnTheWaterBoatOnATrack.LOGGER.info("Creating MineRaft for {}", type);
			return (ListModel<T>) new MineRaftModel(context.bakeLayer(layerLocation));
		} else if (type == AbstractMinecart.Type.CHEST) {
			TrainOnTheWaterBoatOnATrack.LOGGER.info("Creating Chest MineBoat for {}", type);
			layerLocation = ModelLayers.createChestBoatModelName(boatType);
			return (ListModel<T>) new MineChestBoatModel(context.bakeLayer(layerLocation));
		} else {
			TrainOnTheWaterBoatOnATrack.LOGGER.info("Creating MineBoat for {}", type);
			return (ListModel<T>) new MineBoatModel(context.bakeLayer(layerLocation));
		}
	}

	private static Boat.Type getBoatType(AbstractMinecart.Type type) {
		if (type == AbstractMinecart.Type.SPAWNER) {
			return Boat.Type.BAMBOO;
		} else if (type == AbstractMinecart.Type.COMMAND_BLOCK) {
			return Boat.Type.MANGROVE;
		}
		//Boat.Type by id of the Type ordinal but check if not out of bounds
		return type.ordinal() < Boat.Type.values().length ? Boat.Type.byId(type.ordinal()) : Boat.Type.OAK;
	}

	private static ResourceLocation getTextureLocation(AbstractMinecart.Type type) {
		Boat.Type boatType = getBoatType(type);
		return type == AbstractMinecart.Type.CHEST
				? ResourceLocation.parse(boatType.getName()).withPrefix("textures/entity/chest_boat/").withSuffix(".png")
				: ResourceLocation.parse(boatType.getName()).withPrefix("textures/entity/boat/").withSuffix(".png");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		poseStack.pushPose();
		long i = (long) entity.getId() * 493286711L;
		i = i * i * 4392167121L + i * 98761L;
		float f = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f1 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f2 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		poseStack.translate(f, f1, f2);
		double d0 = Mth.lerp((double) partialTicks, entity.xOld, entity.getX());
		double d1 = Mth.lerp((double) partialTicks, entity.yOld, entity.getY());
		double d2 = Mth.lerp((double) partialTicks, entity.zOld, entity.getZ());
		double d3 = 0.3F;
		Vec3 vec3 = entity.getPos(d0, d1, d2);
		float f3 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
		if (vec3 != null) {
			Vec3 vec31 = entity.getPosOffs(d0, d1, d2, 0.3F);
			Vec3 vec32 = entity.getPosOffs(d0, d1, d2, -0.3F);
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
				entityYaw = (float) (Math.atan2(vec33.z, vec33.x) * 180.0 / Math.PI);
				f3 = (float) (Math.atan(vec33.y) * 73.0);
			}
		}

		poseStack.translate(0.0F, 0.375F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entityYaw));
		poseStack.mulPose(Axis.ZP.rotationDegrees(-f3));
		float f5 = (float) entity.getHurtTime() - partialTicks;
		float f6 = entity.getDamage() - partialTicks;
		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f5 > 0.0F) {
			poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f5) * f5 * f6 / 10.0F * (float) entity.getHurtDir()));
		}

		int j = entity.getDisplayOffset();
		BlockState blockstate = entity.getDisplayBlockState();
		if (blockstate.getRenderShape() != RenderShape.INVISIBLE && entity.getMinecartType() != AbstractMinecart.Type.CHEST) {
			poseStack.pushPose();
			poseStack.scale(0.75F, 0.75F, 0.75F);
			poseStack.translate(-0.5F, (float) (j - 8) / 16.0F, -0.5F);
			this.blockRenderer.renderSingleBlock(blockstate, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

			poseStack.popPose();
		}

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
		boatModel.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer vertexconsumer = buffer.getBuffer(boatModel.renderType(boatLocation));
		boatModel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
		if (!entity.isUnderWater()) {
			VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.waterMask());
			if (boatModel instanceof WaterPatchModel waterpatchmodel) {
				waterpatchmodel.waterPatch().render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
			}
		}

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return boatLocation;
	}
}
