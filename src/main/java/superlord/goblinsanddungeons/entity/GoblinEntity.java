package superlord.goblinsanddungeons.entity;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;

public class GoblinEntity extends Monster {

	protected GoblinEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
	}

	public MobType getCreatureAttribute() {
		return CreatureAttributeInit.GOBLIN;
	}

	public double getVisibilityMultiplier(@Nullable Entity lookingEntity) {
		double d0 = 1.0D;

		if (lookingEntity != null) {
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
			Item item = itemstack.getItem();

			ItemStack itemstack2 = this.getItemBySlot(EquipmentSlot.OFFHAND);
			Item item2 = itemstack2.getItem();


			if (lookingEntity instanceof GoblinEntity && item == ItemInit.GOBLIN_CROWN.get()) {
				d0 *= 0.5D;
			}
			if (lookingEntity instanceof Monster && lookingEntity.isCrouching() && item2 == ItemInit.RING_OF_STEALTH.get()) {
				d0 *= 0.8D;
			}
		}

		return d0;
	}

	private static boolean isAdmiringItem(GoblinEntity p_234451_0_) {
		return p_234451_0_.getBrain().hasMemoryValue(MemoryModuleType.ADMIRING_ITEM);
	}


	protected static void cancelAdmiring(GoblinEntity p_234496_0_) {
		if (isAdmiringItem(p_234496_0_) && !p_234496_0_.getOffhandItem().isEmpty()) {
			p_234496_0_.spawnAtLocation(p_234496_0_.getOffhandItem());
			p_234496_0_.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
		}

	}

	 protected void finishConversion(ServerLevel p_234416_1_) {
	      ZombifiedPiglin zombifiedpiglinentity = this.convertTo(EntityType.ZOMBIFIED_PIGLIN, true);
	      if (zombifiedpiglinentity != null) {
	         zombifiedpiglinentity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
	         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zombifiedpiglinentity);
	      }

	   }
	
}
