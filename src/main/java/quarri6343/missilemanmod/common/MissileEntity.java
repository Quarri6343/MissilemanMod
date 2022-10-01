package quarri6343.missilemanmod.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.Random;

public class MissileEntity extends EntityTNTPrimed {

    private final EntityLivingBase target;
    private final Vector3d launchPos;
    private MissilePhase missilePhase = MissilePhase.RISE;
    private int currentAimDuration = 0;

    private static final double riseAltitude = 8 + new Random().nextInt(10);
    private static final double horizontalSpeedMultiplierOnRise = 0.25;
    private static final double horizontalSpeedMultiplierOnFly = 2;
    private static final double speedMultiplierOnFall = 2;
    private static final int aimDuration = 10;

    public MissileEntity(World worldIn, double x, double y, double z, EntityLivingBase igniter, EntityLivingBase target) {
        super(worldIn, x, y, z, igniter);
        this.target = target;
        launchPos = new Vector3d(igniter.posX, igniter.posY, igniter.posZ);
        this.setNoGravity(true);
        this.noClip = true;
    }

    public MissileEntity(World worldIn) {
        super(worldIn);
        this.target = null;
        launchPos = null;
        this.setNoGravity(true);
        this.noClip = true;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        Vector3d distance = new Vector3d(0, 0, 0);
        if (!this.world.isRemote) {
            distance = new Vector3d(target.posX - posX, target.posY - posY, target.posZ - posZ);
            Vector3f normalizedDist = new Vector3f((float) distance.x, (float) distance.y, (float) distance.z);
            normalizedDist.normalize();

            if (missilePhase == MissilePhase.RISE) {
                this.motionX = normalizedDist.getX() * horizontalSpeedMultiplierOnRise;
                this.motionY = 1;
                this.motionZ = normalizedDist.getZ() * horizontalSpeedMultiplierOnRise;
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                if (this.posY - launchPos.y > riseAltitude) {
                    missilePhase = MissilePhase.Fly;
                }
            } else if (missilePhase == MissilePhase.Fly) {
                this.motionX = normalizedDist.getX() * horizontalSpeedMultiplierOnFly;
                this.motionY = 0;
                this.motionZ = normalizedDist.getZ() * horizontalSpeedMultiplierOnFly;
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                if (distance.x < 1 && distance.z < 1) {
                    missilePhase = MissilePhase.Aim;
                }
            } else if (missilePhase == MissilePhase.Aim) {
                if (++currentAimDuration > aimDuration) {
                    this.motionX = 0;
                    this.motionY = 0;
                    this.motionZ = 0;
                    missilePhase = MissilePhase.Fall;
                }
            } else if (missilePhase == MissilePhase.Fall) {
                this.motionX = normalizedDist.getX() * speedMultiplierOnFall;
                this.motionY = normalizedDist.getY() * speedMultiplierOnFall;
                this.motionZ = normalizedDist.getZ() * speedMultiplierOnFall;
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            }
        } else {
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }

        if (Math.abs(distance.x) < 1 && Math.abs(distance.y) < 1 && Math.abs(distance.z) < 1) {
            if (!this.world.isRemote) {
                this.setDead();
                this.explode();
            }
        } else {
            this.handleWaterMovement();
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode() {
        float f = 4.0F;
        this.world.createExplosion(this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 4.0F, false);
    }

    private static enum MissilePhase {
        RISE,
        Fly,
        Aim,
        Fall
    }
}
