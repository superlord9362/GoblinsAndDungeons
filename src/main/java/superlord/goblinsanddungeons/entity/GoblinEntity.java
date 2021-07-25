package superlord.goblinsanddungeons.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import superlord.goblinsanddungeons.init.CreatureAttributeInit;
import superlord.goblinsanddungeons.init.ItemInit;

public class GoblinEntity extends MonsterEntity {

	protected GoblinEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttributeInit.GOBLIN;
	}

	public double getVisibilityMultiplier(@Nullable Entity lookingEntity) {
		double d0 = 1.0D;

		if (lookingEntity != null) {
			ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
			Item item = itemstack.getItem();

			ItemStack itemstack2 = this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
			Item item2 = itemstack2.getItem();


			if (lookingEntity instanceof GoblinEntity && item == ItemInit.GOBLIN_CROWN.get()) {
				d0 *= 0.5D;
			}
			if (lookingEntity instanceof MonsterEntity && lookingEntity.isSneaking() && item2 == ItemInit.RING_OF_STEALTH.get()) {
				d0 *= 0.8D;
			}
		}

		return d0;
	}

	private static boolean func_234451_A_(GoblinEntity p_234451_0_) {
		return p_234451_0_.getBrain().hasMemory(MemoryModuleType.ADMIRING_ITEM);
	}


	protected static void func_234496_c_(GoblinEntity p_234496_0_) {
		if (func_234451_A_(p_234496_0_) && !p_234496_0_.getHeldItemOffhand().isEmpty()) {
			p_234496_0_.entityDropItem(p_234496_0_.getHeldItemOffhand());
			p_234496_0_.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
		}

	}

	 protected void func_234416_a_(ServerWorld p_234416_1_) {
	      ZombifiedPiglinEntity zombifiedpiglinentity = this.func_233656_b_(EntityType.ZOMBIFIED_PIGLIN, true);
	      if (zombifiedpiglinentity != null) {
	         zombifiedpiglinentity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
	         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zombifiedpiglinentity);
	      }

	   }
	
}
