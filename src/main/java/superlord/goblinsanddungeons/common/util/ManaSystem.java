package superlord.goblinsanddungeons.common.util;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import superlord.goblinsanddungeons.init.ItemInit;

@EventBusSubscriber
public class ManaSystem {

	public int manaLevel = 20;

	public ManaSystem() {
	}

	public void addStats(int manaLevel) {
		this.manaLevel = Math.min(manaLevel + this.manaLevel, 1);
	}

	public void read(CompoundNBT compound) {
		if (compound.contains("manaLevel", 99)) {
			this.manaLevel = compound.getInt("manaLevel");
		}
	}

	public void write(CompoundNBT compound) {
		compound.putInt("manaLevel", this.manaLevel);
	}

	public int getManaLevel() {
		return this.manaLevel;
	}

	public boolean needMana() {
		return this.manaLevel < 20;
	}
	
	public void setManaLevel(int manaLevelIn) {
		this.manaLevel = manaLevelIn;
	}

	public void save(LivingEntity player) {
		ManaStats.setManaStats(player, this);
	}

	public void baseTick(PlayerEntity player) {
		save(player);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void baseClientTick(AbstractClientPlayerEntity player) {
	}


	@SubscribeEvent
	public static void addSouls(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntityLiving() != null && event.getEntityLiving() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
			ManaSystem stats = ManaStats.getManaStats(player);

			if (event.getItem().getItem() == ItemInit.BOTTLED_SOULS.get()) {
				System.out.println("Drank!");
				stats.setManaLevel(3);
			}
			stats.save(player);
		}
	}
}