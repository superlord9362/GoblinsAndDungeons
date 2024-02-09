package superlord.goblinsanddungeons.networking.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import superlord.goblinsanddungeons.magic.PlayerManaProvider;
import superlord.goblinsanddungeons.networking.ModMessages;

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
			ServerPlayer player = context.getSender();
			player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana -> {
				playerMana.addMana(4);
				System.out.println(playerMana.getMana());
				ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(playerMana.getMana()), player);
			});
		});
		return true;
	}

}
