package quarri6343.missilemanmod.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class MissileEntity extends EntityTNTPrimed {
    
    private final EntityLivingBase target;
    
    public MissileEntity(World worldIn, double x, double y, double z, EntityLivingBase igniter, EntityLivingBase target) {
        super(worldIn, x, y, z, igniter);
        this.target = target;
    }
    
    public MissileEntity(World worldIn){
        super(worldIn);
        this.target = null;
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        Vector3d distance = new Vector3d(0,0,0);
        if(!this.world.isRemote && target != null){
            distance = new Vector3d(target.posX - posX,target.posY - posY,target.posZ - posZ);
            Vector3f normalizedDist = new Vector3f((float) distance.x, (float) distance.y, (float) distance.z);
            normalizedDist.normalize();
            this.motionX = normalizedDist.getX();
            this.motionY = normalizedDist.getY();
            this.motionZ = normalizedDist.getZ();
            this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        }
        
        if(Math.abs(distance.x) < 1 && Math.abs(distance.y) < 1 && Math.abs(distance.z) < 1)
        {
            if(!this.world.isRemote){
                this.setDead();
                this.explode();
            }
        }
        else
        {
            this.handleWaterMovement();
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode()
    {
        float f = 4.0F;
        this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, 4.0F, false);
    }
}
