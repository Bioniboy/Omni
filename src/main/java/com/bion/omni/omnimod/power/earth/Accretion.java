package com.bion.omni.omnimod.power.earth;

import com.bion.omni.omnimod.OmniMod;
import com.bion.omni.omnimod.entity.ModEntities;
import com.bion.omni.omnimod.entity.custom.AccRockEntity;
import com.bion.omni.omnimod.power.ImpulsePower;
import net.minecraft.block.Blocks;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Accretion extends ImpulsePower {
    public Accretion(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Accretion";
    }

    @Override
    public String getId() {
        return "accretion";
    }
    @Override
    public String getAdvancementId() {
        return "accretion";
    }

    @Override
    public String getPreRequisiteId() {
        return null;
    }
    @Override
    public double getManaCost() {
        return 2;
    }
    @Override
    public Boolean hasConfig() {
        return true; }
    @Override
    public boolean activate(ServerPlayerEntity user) {
        super.activate(user);
        tickCounter = 25;
        World world = user.getWorld();
        world.playSound(null, BlockPos.ofFloored(user.getPos()), SoundEvent.of(Identifier.of(OmniMod.MOD_ID, "rockgrow")), SoundCategory.PLAYERS, .7F, world.random.nextFloat() * 0.1F + 0.9F);
        return true;
    }
    public void tick(ServerPlayerEntity user) {
        super.tick(user);
        for (double x = -1; x <= 1; x += 0.2) {
            for (double y = -1; y <= 1; y += 0.2) {
                for (double z = -1; z <= 1; z += 0.2) {
                    double distance = new Vec3d(user.getX() + x, user.getY() + y, user.getZ() + z).distanceTo(user.getPos());
                    if (user.getRandom().nextDouble() > 0.8 && distance > .9 && distance <= 1.0) {
                        user.getServerWorld().spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COBBLESTONE.getDefaultState()), user.getX() + x, user.getY() + y + 1, user.getZ() + z, 1, 0, 0, 0, 2);
                    }
                }
            }
        }
        if(tickCounter == 1){
            World world = user.getWorld();
            if (!world.isClient) {
                AccRockEntity accrock = ModEntities.ACC_ROCK.create(user.getServerWorld());
                assert accrock != null;
                accrock.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                accrock.setPosition(user.getEyePos());
                world.spawnEntity(accrock);
                world.playSound(null, BlockPos.ofFloored(user.getPos()), SoundEvent.of(Identifier.of(OmniMod.MOD_ID, "rocksend")), SoundCategory.PLAYERS, .7F, world.random.nextFloat() * 0.1F + 0.9F);
//                if (!accrock.getWorld().isClient) {
//                    for (int yeetparticles = 0; yeetparticles < 100; yeetparticles++) {
//                        accrock.getEntityWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COBBLESTONE.getDefaultState()), accrock.getX(), accrock.getY(), accrock.getZ(), .5, .5, .5);
//                    }
//                    OmniMod.LOGGER.info("yeet particles spawned");
//                }
            }
        }
    }
}
