package superlord.goblinsanddungeons.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.SoundEvents;
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

}
