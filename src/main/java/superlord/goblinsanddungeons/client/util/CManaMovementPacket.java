package superlord.goblinsanddungeons.client.util;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import superlord.goblinsanddungeons.common.util.ManaStats;
import superlord.goblinsanddungeons.common.util.ManaSystem;

public class CManaMovementPacket {
	private float moveF;
	private float moveS;
	private boolean jump;
	private UUID uuid;
	
	public CManaMovementPacket(final float moveF, final float moveS, final boolean jump, final UUID uuid) {
		this.uuid = uuid;
		this.moveF = moveF;
		this.moveS = moveS;
		this.jump = jump;
	}
	
	public static void encode(final CManaMovementPacket msg, final PacketBuffer packetBuffer) {
		packetBuffer.writeFloat(msg.moveF);
		packetBuffer.writeFloat(msg.moveS);
		packetBuffer.writeBoolean(msg.jump);
		packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
		packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
	}
	
	public static CManaMovementPacket decode(final PacketBuffer packetBuffer) {
		return new CManaMovementPacket(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readBoolean(), new UUID(packetBuffer.readLong(), packetBuffer.readLong()));
	}
	
	@SuppressWarnings("unused")
	public static void handle(final CManaMovementPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
		final NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			final ServerPlayerEntity sender = context.getSender();
			if (sender == null) {
				return;
			}
			final float moveF = msg.moveF;
			final float moveS = msg.moveS;
			final boolean jump = msg.jump;
			final UUID uuid = msg.uuid;
			if (uuid.equals(PlayerEntity.getUUID(sender.getGameProfile()))) {
				ManaSystem stats = ManaStats.getManaStats(sender);
				int movM = (int) ((moveS+moveF)*10);
				float moveMul;
				if (movM > 0) {
					moveMul = 1.0F;
				} else {
					moveMul = 0.5F;
				}
				if (sender.isSprinting()) {
					moveMul += 0.5F;
				}
				if (jump) {
					moveMul += 1.0F;
				}
				stats.save(sender);
			}
		});
		context.setPacketHandled(true);
	}

}
