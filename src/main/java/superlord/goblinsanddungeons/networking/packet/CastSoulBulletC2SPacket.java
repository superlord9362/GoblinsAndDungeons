package superlord.goblinsanddungeons.networking.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import superlord.goblinsanddungeons.magic.PlayerMana;

public class CastSoulBulletC2SPacket {
	
	public CastSoulBulletC2SPacket() {
		
	}
	
	public CastSoulBulletC2SPacket(FriendlyByteBuf buf) {
		
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			PlayerMana mana = new PlayerMana();
			mana.subMana(1);
		});
		return true;
	}

}

