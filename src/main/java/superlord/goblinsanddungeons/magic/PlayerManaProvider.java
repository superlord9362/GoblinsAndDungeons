package superlord.goblinsanddungeons.magic;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMana> PLAYER_MANA = CapabilityManager.get(new CapabilityToken<PlayerMana>() { });

    private PlayerMana mana = null;
    private final LazyOptional<PlayerMana> optional = LazyOptional.of(this::createPlayerMana);

    private PlayerMana createPlayerMana() {
        if(this.mana == null) {
            this.mana = new PlayerMana();
        }
        return this.mana;
    }

    @Nonnull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == PLAYER_MANA) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    	return getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerMana().write(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    	createPlayerMana().read(nbt);
    }
}
