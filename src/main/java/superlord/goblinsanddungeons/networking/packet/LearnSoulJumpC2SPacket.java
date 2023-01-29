package superlord.goblinsanddungeons.networking.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import superlord.goblinsanddungeons.magic.PlayerSpellsProvider;

public class LearnSoulJumpC2SPacket {
	
	public LearnSoulJumpC2SPacket() {
		
	}
	
	public LearnSoulJumpC2SPacket(FriendlyByteBuf buf) {
		
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			player.getCapability(PlayerSpellsProvider.PLAYER_SPELLS).ifPresent(playerSpells -> {
				playerSpells.setKnowsSoulJump(true);
			});
		});
		return true;
	}

}
