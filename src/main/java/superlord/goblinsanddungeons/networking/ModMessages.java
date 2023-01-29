package superlord.goblinsanddungeons.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.networking.packet.CastSoulBulletC2SPacket;
import superlord.goblinsanddungeons.networking.packet.CastSoulJumpC2SPacket;
import superlord.goblinsanddungeons.networking.packet.DrinkBottledSoulsC2SPacket;
import superlord.goblinsanddungeons.networking.packet.LearnSoulBulletC2SPacket;
import superlord.goblinsanddungeons.networking.packet.LearnSoulJumpC2SPacket;
import superlord.goblinsanddungeons.networking.packet.ManaDataSyncS2CPacket;

public class ModMessages {

	private static SimpleChannel INSTANCE;

	private static int packetId = 0;
	private static int id() {
		return packetId++;
	}

	public static void register() {
		SimpleChannel net = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(GoblinsAndDungeons.MOD_ID, "messages"))
				.networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();

		INSTANCE = net;

		net.messageBuilder(LearnSoulBulletC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(LearnSoulBulletC2SPacket::new)
		.encoder(LearnSoulBulletC2SPacket::toBytes)
		.consumer(LearnSoulBulletC2SPacket::handle)
		.add();

		net.messageBuilder(LearnSoulJumpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(LearnSoulJumpC2SPacket::new)
		.encoder(LearnSoulJumpC2SPacket::toBytes)
		.consumer(LearnSoulJumpC2SPacket::handle)
		.add();

		net.messageBuilder(DrinkBottledSoulsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(DrinkBottledSoulsC2SPacket::new)
		.encoder(DrinkBottledSoulsC2SPacket::toBytes)
		.consumer(DrinkBottledSoulsC2SPacket::handle)
		.add();

		net.messageBuilder(CastSoulBulletC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(CastSoulBulletC2SPacket::new)
		.encoder(CastSoulBulletC2SPacket::toBytes)
		.consumer(CastSoulBulletC2SPacket::handle)
		.add();

		net.messageBuilder(CastSoulJumpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(CastSoulJumpC2SPacket::new)
		.encoder(CastSoulJumpC2SPacket::toBytes)
		.consumer(CastSoulJumpC2SPacket::handle)
		.add();

		net.messageBuilder(ManaDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
		.decoder(ManaDataSyncS2CPacket::new)
		.encoder(ManaDataSyncS2CPacket::toBytes)
		.consumer(ManaDataSyncS2CPacket::handle)
		.add();

	}

	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

}
