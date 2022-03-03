package superlord.goblinsanddungeons.client.packets;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import superlord.goblinsanddungeons.common.util.ManaEntityStats;

public class ClientBoundManaStatsPacket {
	
	private CompoundTag stats;
	private UUID uuid;
	
	public ClientBoundManaStatsPacket(final CompoundTag stats, final UUID uuid) {
		this.stats = stats;
		this.uuid = uuid;
	}
	
	public ClientBoundManaStatsPacket(final ServerPlayer player) {
		this(ManaEntityStats.getModNBT(player), player.getUUID());
	}
	
	public static void encode(final ClientBoundManaStatsPacket msg, final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(msg.stats);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}

	public static ClientBoundManaStatsPacket decode(final FriendlyByteBuf packetBuffer) {
		return new ClientBoundManaStatsPacket(packetBuffer.readNbt(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final ClientBoundManaStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final CompoundTag stats = msg.stats;
			final UUID uuid = msg.uuid;
			update(stats, uuid);
		}));
		context.setPacketHandled(true);
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void update(final CompoundTag stats, final UUID uuid) {
		if (uuid.equals(Player.createPlayerUUID(Minecraft.getInstance().player.getGameProfile()))) {
			ManaEntityStats.setModNBT(stats, Minecraft.getInstance().player);
		}
	}

}
