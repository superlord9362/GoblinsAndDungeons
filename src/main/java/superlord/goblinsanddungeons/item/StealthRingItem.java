package superlord.goblinsanddungeons.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.SoundEvents;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.common.util.GDArmorMaterial;

public class StealthRingItem extends ArmorItem {

	public static final IArmorMaterial MATERIAL = new GDArmorMaterial(GoblinsAndDungeons.MOD_ID + ":stealth_ring", 7, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, null);

	public StealthRingItem(Properties builderIn) {
		super(MATERIAL, EquipmentSlotType.OFFHAND, builderIn);
	}
	
}
