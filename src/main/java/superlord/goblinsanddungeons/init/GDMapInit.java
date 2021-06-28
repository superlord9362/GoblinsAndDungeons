package superlord.goblinsanddungeons.init;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GDMapInit extends MapDecoration {

	public GDMapInit(net.minecraft.world.storage.MapDecoration.Type type, byte x, byte y, byte rotation, ITextComponent customName) {
		super(type, x, y, rotation, customName);
	}
	
	public static void addTargetDecoration(ItemStack map, BlockPos target, String decorationName, Type type) {
	      ListNBT listnbt;
	      if (map.hasTag() && map.getTag().contains("Decorations", 9)) {
	         listnbt = map.getTag().getList("Decorations", 10);
	      } else {
	         listnbt = new ListNBT();
	         map.setTagInfo("Decorations", listnbt);
	      }

	      CompoundNBT compoundnbt = new CompoundNBT();
	      compoundnbt.putByte("type", type.getIcon());
	      compoundnbt.putString("id", decorationName);
	      compoundnbt.putDouble("x", (double)target.getX());
	      compoundnbt.putDouble("z", (double)target.getZ());
	      compoundnbt.putDouble("rot", 180.0D);
	      listnbt.add(compoundnbt);
	      if (type.hasMapColor()) {
	         CompoundNBT compoundnbt1 = map.getOrCreateChildTag("display");
	         compoundnbt1.putInt("MapColor", type.getMapColor());
	      }

	   }

	public static enum Type {
		RUINED_KEEP(true);

		private final byte icon = (byte)this.ordinal();
		private final boolean renderedOnFrame;
		private final int mapColor;

		private Type(boolean renderedOnFrame) {
			this(renderedOnFrame, -1);
		}

		private Type(boolean renderedOnFrame, int mapColor) {
			this.renderedOnFrame = renderedOnFrame;
			this.mapColor = mapColor;
		}

		public byte getIcon() {
			return this.icon;
		}

		@OnlyIn(Dist.CLIENT)
		public boolean isRenderedOnFrame() {
			return this.renderedOnFrame;
		}

		public boolean hasMapColor() {
			return this.mapColor >= 0;
		}

		public int getMapColor() {
			return this.mapColor;
		}

		public static Type byIcon(byte iconByte) {
			return values()[MathHelper.clamp(iconByte, 0, values().length - 1)];
		}
	}

}
