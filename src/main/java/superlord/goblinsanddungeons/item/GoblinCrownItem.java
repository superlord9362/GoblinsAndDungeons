package superlord.goblinsanddungeons.item;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.client.model.armor.GoblinCrownModel;
import superlord.goblinsanddungeons.entity.GoblinEntity;

public class GoblinCrownItem extends ArmorItem {

	public GoblinCrownItem(IArmorMaterial material, EquipmentSlotType type, Item.Properties properties) {
		super(material, type, properties);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType slot, A _default) {
		return (A) GoblinCrownModel.INSTANCE;
	}

	@Override
	@Nullable
	@OnlyIn(Dist.CLIENT) 
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "goblinsanddungeons:textures/models/armor/goblin_crown.png";
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		MonsterEntity monsters = world.func_225318_b(MonsterEntity.class, EntityPredicate.DEFAULT, (LivingEntity)player, player.getPosX(), player.getPosY(), player.getPosZ(), player.getBoundingBox().grow(8.0D, 3.0D, 8.0D));
		if (monsters != null) {
			if (monsters instanceof GoblinEntity) {
				monsters.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 1, false, false));
			} else {
				monsters.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 0, false, false));
			}
		}
	}


}
