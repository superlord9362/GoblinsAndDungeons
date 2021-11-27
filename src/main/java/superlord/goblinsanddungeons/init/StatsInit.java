package superlord.goblinsanddungeons.init;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import superlord.goblinsanddungeons.common.util.ManaStats;

@EventBusSubscriber
public class StatsInit {
	
	@SubscribeEvent
	public static void registerStats(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			ManaStats.addStatsOnSpawn(player);
			if (!player.world.isRemote) {
				ManaStats.getManaStats(player).baseTick(player);
			}
		}
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void tickStatsOnClient(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof AbstractClientPlayerEntity) {
			AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)event.getEntityLiving();
			if (player.world.isRemote) {
				ManaStats.getManaStats(player).baseClientTick(player);
			}
		}
	}
	
	@SubscribeEvent
	public static void restoreStats(PlayerEvent.Clone event) {
		ManaStats.getOrCreateModNBT(event.getPlayer());
		if (!event.isWasDeath()) {
			ManaStats.setManaStats(event.getPlayer(), ManaStats.getManaStats(event.getOriginal()));
		}
	}

}
