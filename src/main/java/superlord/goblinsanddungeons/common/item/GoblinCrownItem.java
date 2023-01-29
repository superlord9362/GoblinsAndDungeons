package superlord.goblinsanddungeons.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.entity.Goblin;

public class GoblinCrownItem extends ArmorItem {

	public GoblinCrownItem(ArmorMaterial material, EquipmentSlot type, Item.Properties properties) {
		super(material, type, properties);
	}
	
	@Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer)
    {
        consumer.accept((net.minecraftforge.client.IItemRenderProperties) GoblinsAndDungeons.PROXY.getArmorRenderProperties());
    }

	@Override
	@Nullable
	@OnlyIn(Dist.CLIENT) 
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return "goblinsanddungeons:textures/models/armor/goblin_crown.png";
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		Monster monsters = world.getNearestEntity(Monster.class, TargetingConditions.DEFAULT, (LivingEntity)player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(8.0D, 3.0D, 8.0D));
		if (monsters != null) {
			if (monsters instanceof Goblin) {
				monsters.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1200, 1, false, false));
			} else {
				monsters.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1200, 0, false, false));
			}
		}
	}

	@Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)) {
            p_77624_3_.add(new TranslatableComponent("goblin_crown").withStyle(ChatFormatting.GRAY));
        } else {
            p_77624_3_.add(new TranslatableComponent("shift").withStyle(ChatFormatting.GRAY));
        }
	}


}
