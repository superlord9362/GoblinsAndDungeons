package superlord.goblinsanddungeons.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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

}
