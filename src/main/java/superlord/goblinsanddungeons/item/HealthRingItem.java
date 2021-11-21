package superlord.goblinsanddungeons.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.GDArmorMaterial;

public class HealthRingItem extends ArmorItem {

	private final Multimap<Attribute, AttributeModifier> attributeModifiers;

	protected static final UUID HEALTH_MODIFIER = UUID.fromString("775e8866-ba4a-4a18-ae81-9f7476d2cb82");

	public static final IArmorMaterial MATERIAL = new GDArmorMaterial(GoblinsAndDungeons.MOD_ID + ":health_ring", 7, new int[] {1, 1, 1, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, null);

	public HealthRingItem(Properties builderIn) {
		super(MATERIAL, EquipmentSlotType.OFFHAND, builderIn);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_MODIFIER, "Armor modifier", 2.0D, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.OFFHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344)) {
			tooltip.add(new TranslationTextComponent("health_ring").mergeStyle(TextFormatting.GRAY));
		} else {
			tooltip.add(new TranslationTextComponent("shift").mergeStyle(TextFormatting.GRAY));
		}
	}

}
