package com.mrbysco.trainonthewaterboatonatrack.client;

import com.mrbysco.trainonthewaterboatonatrack.client.renderer.BoatCartRenderer;
import com.mrbysco.trainonthewaterboatonatrack.client.renderer.MineBoatRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityType.MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.RIDEABLE));
		event.registerEntityRenderer(EntityType.COMMAND_BLOCK_MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.COMMAND_BLOCK));
		event.registerEntityRenderer(EntityType.FURNACE_MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.FURNACE));
		event.registerEntityRenderer(EntityType.HOPPER_MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.HOPPER));
		event.registerEntityRenderer(EntityType.TNT_MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.TNT));
		event.registerEntityRenderer(EntityType.SPAWNER_MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.SPAWNER));
		event.registerEntityRenderer(EntityType.CHEST_MINECART, context -> new MineBoatRenderer<>(context, AbstractMinecart.Type.CHEST));
		event.registerEntityRenderer(EntityType.BOAT, context -> new BoatCartRenderer<>(context, ModelLayers.MINECART));
		event.registerEntityRenderer(EntityType.CHEST_BOAT, context -> new BoatCartRenderer<>(context, ModelLayers.CHEST_MINECART));
	}

	public static ModelLayerLocation createMineBoatModelName(Boat.Type type) {
		ResourceLocation location = ResourceLocation.parse(type.getName());
		return new ModelLayerLocation(location.withPrefix("boat/"), "main");
	}
	public static ModelLayerLocation createMineChestBoatModelName(Boat.Type type) {
		ResourceLocation location = ResourceLocation.parse(type.getName());
		return new ModelLayerLocation(location.withPrefix("chest_boat/"), "main");
	}
}
