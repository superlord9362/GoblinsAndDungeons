package superlord.goblinsanddungeons.common.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.GDArmorMaterial;

public class HealthRingItem extends ArmorItem {

	private final Multimap<Attribute, AttributeModifier> attributeModifiers;

	protected static final UUID HEALTH_MODIFIER = UUID.fromString("775e8866-ba4a-4a18-ae81-9f7476d2cb82");

	public static final ArmorMaterial MATERIAL = new GDArmorMaterial(GoblinsAndDungeons.MOD_ID + ":health_ring", 7, new int[] {1, 1, 1, 1}, 0, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, null);

	public HealthRingItem(Properties builderIn) {
		super(MATERIAL, EquipmentSlot.OFFHAND, builderIn);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_MODIFIER, "Armor modifier", 2.0D, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.OFFHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)) {
        	p_77624_3_.add(new TranslatableComponent("health_ring").withStyle(ChatFormatting.GRAY));
		} else {
			p_77624_3_.add(new TranslatableComponent("shift").withStyle(ChatFormatting.GRAY));
		}
	}
	
}
