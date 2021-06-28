package superlord.goblinsanddungeons.client;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class ClientConfig {
	
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;
	private static final String CONFIG_PREFIX = "gui." + GoblinsAndDungeons.MOD_ID + ".config.";
	
	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}
	
	public static class Client {
		public final BooleanValue renderGoblins;
		public final IntValue buttonXOffset;
		public final IntValue buttonYOffset;
		public final IntValue creativeButtonXOffset;
		public final IntValue creativeButtonYOffset;
		public final EnumValue<ButtonCorner> buttonCorner;
		
		Client(ForgeConfigSpec.Builder builder) {
			renderGoblins = builder.comment("Set to true to enable rendering the ring slots")
			          .translation(CONFIG_PREFIX + "renderGoblins").define("renderGoblins", true);
			      buttonXOffset = builder.comment("The X-Offset for the Goblins & Dungeons GUI button")
			          .translation(CONFIG_PREFIX + "buttonXOffset")
			          .defineInRange("buttonXOffset", 0, -100, 100);
			      buttonYOffset = builder.comment("The Y-Offset for the Goblins & Dungeons GUI button")
			          .translation(CONFIG_PREFIX + "buttonYOffset")
			          .defineInRange("buttonYOffset", 0, -100, 100);
			      creativeButtonXOffset = builder.comment("The X-Offset for the Creative Goblins & Dungeons GUI button")
			          .translation(CONFIG_PREFIX + "creativeButtonXOffset")
			          .defineInRange("creativeButtonXOffset", 0, -100, 100);
			      creativeButtonYOffset = builder.comment("The Y-Offset for the Creative Goblins & Dungeons GUI button")
			          .translation(CONFIG_PREFIX + "creativeButtonYOffset")
			          .defineInRange("creativeButtonYOffset", 0, -100, 100);
			      buttonCorner = builder.comment("The corner for the Goblins & Dungeons GUI button")
			          .translation(CONFIG_PREFIX + "buttonCorner")
			          .defineEnum("buttonCorner", ButtonCorner.TOP_LEFT);

			      builder.pop();
		}
		public enum ButtonCorner {
			TOP_LEFT(26, -75, 73, -62),
			TOP_RIGHT(61, -75, 95, -62),
			BOTTOM_LEFT(26, -20, 73, -29),
			BOTTOM_RIGHT(61, -20, 95, -29);
			final int xoffset;
			final int yoffset;
			final int creativeXoffset;
			final int creativeYoffset;
			
			ButtonCorner(int x, int y, int creativeX, int creativeY) {
				xoffset = x;
				yoffset = y;
				creativeXoffset = creativeX;
				creativeYoffset = creativeY;
			}
			
			public int getXoffset() {
				return xoffset;
			}
			
			public int getYoffset() {
				return yoffset;
			}
			
			public int getCreativeXoffset() {
				return creativeXoffset;
			}
			
			public int getCreativeYoffset() {
				return creativeYoffset;
			}
		}
	}

}
