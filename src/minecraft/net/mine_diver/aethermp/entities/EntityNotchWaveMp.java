package net.mine_diver.aethermp.entities;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityNotchWave;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;

public class EntityNotchWaveMp extends EntityNotchWave implements ISpawnable {

	public EntityNotchWaveMp(World world) {
		super(world);
	}

	@Override
	public void spawn(Packet230ModLoader packet) {
		entityId = packet.dataInt[0];
		Entity entity = EntityManager.getEntityByID(packet.dataInt[1]);
		EntityLiving thrower = null;
		try {
			if (entity instanceof EntityLiving) {
				ModLoader.setPrivateValue(EntityNotchWave.class, this, "thrower", entity);
				thrower = (EntityLiving) entity;
			}
			ModLoader.setPrivateValue(EntityNotchWave.class, this, "xTileSnowball", -1);
			ModLoader.setPrivateValue(EntityNotchWave.class, this, "yTileSnowball", -1);
			ModLoader.setPrivateValue(EntityNotchWave.class, this, "zTileSnowball", -1);
			ModLoader.setPrivateValue(EntityNotchWave.class, this, "inTileSnowball", 0);
			ModLoader.setPrivateValue(EntityNotchWave.class, this, "inGroundSnowball", false);
	        shakeSnowball = 0;
			ModLoader.setPrivateValue(EntityNotchWave.class, this, "ticksInAirSnowball", 0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        setSize(0.25F, 0.25F);
        setLocationAndAngles(thrower.posX, thrower.posY + (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
        serverPosX = (int) (posX * 32);
        serverPosY = (int) (posY * 32);
        serverPosZ = (int) (posZ * 32);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        setSnowballHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
	}

}
