package com.mrbysco.trainonthewaterboatonatrack;

import com.mojang.logging.LogUtils;
import com.mrbysco.trainonthewaterboatonatrack.client.ClientHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TrainOnTheWaterBoatOnATrack.MOD_ID)
public class TrainOnTheWaterBoatOnATrack {
	public static final String MOD_ID = "trainonthewaterboatonatrack";
	public static final Logger LOGGER = LogUtils.getLogger();

	public TrainOnTheWaterBoatOnATrack(IEventBus eventBus, Dist dist, ModContainer container) {

		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::onRegisterRenderer);
		}
	}
}
