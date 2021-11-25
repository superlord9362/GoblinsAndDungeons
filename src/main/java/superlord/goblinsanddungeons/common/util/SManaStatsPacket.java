package superlord.goblinsanddungeons.common.util;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SManaStatsPacket {
	
	private CompoundNBT stats;
	private UUID uuid;
	
	public SManaStatsPacket(final CompoundNBT statsIn, final UUID uuidIn) {
		this.stats = statsIn;
		this.uuid = uuidIn;
	}
	
	public SManaStatsPacket(final ServerPlayerEntity player) {
		this(ManaStats.getModNBT(player), player.getUniqueID());
	}
	
	public static void encode(final SManaStatsPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeCompoundTag(msg.stats);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}
	
	public static SManaStatsPacket decode(final PacketBuffer packetBuffer) {
		return new SManaStatsPacket(packetBuffer.readCompoundTag(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}
	
	@SuppressWarnings("deprecation")
	public static void handle(final SManaStatsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			final CompoundNBT stats = msg.stats;
			final UUID uuid = msg.uuid;
			update(stats, uuid);
		}));
		context.setPacketHandled(true);
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void update(final CompoundNBT stats, final UUID uuid) {
		if (uuid.equals(PlayerEntity.getUUID(Minecraft.getInstance().player.getGameProfile()))) {
			ManaStats.setModNBT(stats, Minecraft.getInstance().player);
		}
	}

}
