package superlord.goblinsanddungeons.networking.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import superlord.goblinsanddungeons.magic.PlayerMana;

public class DrinkBottledSoulsC2SPacket {
	
	public DrinkBottledSoulsC2SPacket() {
		
	}
	
	public DrinkBottledSoulsC2SPacket(FriendlyByteBuf buf) {
		
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			PlayerMana mana = new PlayerMana();
			mana.addMana(4);
		});
		return true;
	}

}

