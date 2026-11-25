package net.createindustrialteam.createindustrial.blocks.laser_engraver;

import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class EngravingBehaviour extends BeltProcessingBehaviour {

    public static final int CYCLE = 240;
    public static final int ENTITY_SCAN = 10;

    EngraverBehaviourSpecifics specifics;
    public int prevRunningTicks;
    public int runningTicks;
    public boolean running;
    public boolean finished;
    Mode mode;

    int entityScanCooldown;

    int amount = 0;

    public interface EngraverBehaviourSpecifics {

        public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate);

        public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate);

        public void onEngravingCompleted();

        public int getParticleAmount();

        public float getKineticSpeed();
    }

    public <T extends SmartBlockEntity & EngraverBehaviourSpecifics> EngravingBehaviour(T be) {
        super(be);
        this.specifics = be;
        mode = Mode.WORLD;
        entityScanCooldown = ENTITY_SCAN;
    }

    @Override
    public void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        running = compound.getBoolean("Running");
        mode = EngravingBehaviour.Mode.values()[compound.getInt("Mode")];
        finished = compound.getBoolean("Finished");
        prevRunningTicks = runningTicks = compound.getInt("Ticks");
        super.read(compound, registries, clientPacket);

        if (clientPacket) {
            //NBTHelper.iterateCompoundList(compound.getList("ParticleItems", Tag.TAG_COMPOUND),
            //        c -> particleItems.add(ItemStack.parseOptional(registries, c)));
            //spawnParticles();
        }
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putBoolean("Running", running);
        compound.putInt("Mode", mode.ordinal());
        compound.putBoolean("Finished", finished);
        compound.putInt("Ticks", runningTicks);
        super.write(compound, registries, clientPacket);

        if (clientPacket) {
            //compound.put("ParticleItems", NBTHelper.writeCompoundList(particleItems, s -> (CompoundTag) s.saveOptional(registries)));
            //particleItems.clear();
        }
    }

    public int getRenderedLaserAlpha(float partialTicks) {
        return amount;
    }

    public void start(Mode mode) {
        this.mode = mode;
        running = true;
        prevRunningTicks = 0;
        runningTicks = 0;
        blockEntity.sendData();
    }

    public boolean inWorld() {
        return mode == Mode.WORLD;
    }

    @Override
    public void tick() {
        super.tick();

        Level level = getWorld();
        BlockPos worldPosition = getPos();

        amount += 3;
        while (amount >= 256) amount -= 256;
    }

    protected void applyInWorld() {
        Level level = getWorld();
        BlockPos worldPosition = getPos();
        AABB bb = new AABB(worldPosition.below(1));

        if (level.isClientSide)
            return;

        for (Entity entity : level.getEntities(null, bb)) {
            if (!(entity instanceof  ItemEntity itemEntity))
                continue;
            if (!entity.isAlive() || !entity.onGround())
                continue;

            entityScanCooldown = 0;
            if (specifics.tryProcessInWorld(itemEntity, false))
                blockEntity.sendData();
            break;
        }
    }

    public int getRunningTickSpeed() {
        float speed = specifics.getKineticSpeed();
        if (speed == 0)
            return 0;
        return (int) Mth.lerp(Mth.clamp(Math.abs(speed) / 512f, 0, 1), 1, 60);
    }

    public enum Mode {
        WORLD(18f / 16f), BELT(21f / 16f);

        public float laserOffset;

        Mode(float laserOffset) {
            this.laserOffset = laserOffset;
        }
    }
}
