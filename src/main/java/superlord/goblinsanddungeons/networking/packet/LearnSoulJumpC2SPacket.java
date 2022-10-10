package superlord.goblinsanddungeons.networking.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import superlord.goblinsanddungeons.magic.PlayerSpells;

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
			PlayerSpells spells = new PlayerSpells();
			spells.setKnowsSoulJump(true);
		});
		return true;
	}

}
