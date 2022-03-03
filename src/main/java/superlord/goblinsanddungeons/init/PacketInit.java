package superlord.goblinsanddungeons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.client.packets.ClientBoundManaStatsPacket;
import superlord.goblinsanddungeons.common.packets.ServerBoundManaMovementPacket;

public class PacketInit {
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "network"), () -> PacketInit.PROTOCOL_VERSION, PacketInit.PROTOCOL_VERSION::equals, PacketInit.PROTOCOL_VERSION::equals);
	private static final String PROTOCOL_VERSION = "1";
	private static int ID = 0;
	
	public static void registerPackets() {
		PacketInit.CHANNEL.messageBuilder(ClientBoundManaStatsPacket.class, PacketInit.ID++).decoder(ClientBoundManaStatsPacket::decode).encoder(ClientBoundManaStatsPacket::encode).consumer(ClientBoundManaStatsPacket::handle).add();
		PacketInit.CHANNEL.messageBuilder(ServerBoundManaMovementPacket.class, PacketInit.ID++).decoder(ServerBoundManaMovementPacket::decode).encoder(ServerBoundManaMovementPacket::encode).consumer(ServerBoundManaMovementPacket::handle).add();
	}

}
