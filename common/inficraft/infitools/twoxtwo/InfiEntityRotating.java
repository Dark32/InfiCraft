package inficraft.infitools.twoxtwo;
import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class InfiEntityRotating extends Entity
{

    public InfiEntityRotating(World world)
    {
        super(world);
        System.out.println("single constructor");
        texID = 0;
        tex2ID = 16*3;
        returnStackSlot = -1;
        hasHitGround = false;
        hasHitMob = false;
        damageDealt = 4;
        maxGroundTicks = 2400;
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        ticksInAir = 0;
        setSize(0.5F, 0.5F);
    }

    public InfiEntityRotating(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        yOffset = 0.0F;
    }

    public InfiEntityRotating(World world, EntityLiving entityliving, float f, float f1)
    {
        this(world);
        owner = entityliving;
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
        setArrowHeading(motionX, motionY, motionZ, f, f1);
    }

    protected void entityInit()
    {
    }
    
	public void damageDagger(int i, boolean flag)
    {
        /*if(returnStack.itemID == mod_2by2.daggerWood.shiftedIndex && fire > 0)
        {
            i *= 6;
        }
        returnStack.damageItem(1, this);*/
        if(!flag)
        {
            return;
        }
        if(returnStack.stackSize < 1)
        {
            for(int j = 0; j < 8; j++)
            {
                worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }

            kill();
        }
    }

	/*
    public void returnToPlayer()
    {
        damageDagger(0, true);
        if(returnStack.stackSize < 1)
        {
            return;
        }
        if(returnStackSlot > 0)
        {
            mod_Infi2x2.forceAddToInv((EntityPlayer)returnsTo, returnStack, returnStackSlot, true);
        } else
        {
            mod_Infi2x2.addToInv((EntityPlayer)returnsTo, returnStack, true);
        }
    }
    */

    public void determineRotation()
    {
        rotationPitch = -57.29578F * (float)Math.atan2(motionY, motionX);
        double d = Math.sqrt((double)(rotationYaw * rotationYaw) + motionY * motionY);
        prevRotationYaw = -57.29578F * (float)Math.atan2(motionZ, d);
    }

    public void onHit(MovingObjectPosition movingobjectposition)
    {
    }

    public void setArrowHeading(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        ticksInGround = 0;
    }

    @Override
    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;
        if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI);
        }
        if(arrowShake > 0)
        {
            arrowShake--;
        }
        if(inGround)
        {
            int i = worldObj.getBlockId(xTile, yTile, zTile);
            if(i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
            } else
            {
                ticksInGround++;
                if(ticksInGround == 1200)
                {
                    setDead();
                }
                if(ticksInGround == maxGroundTicks)
                {
                    setDead();
                }
                if(!hasHitGround)
                {
                    hasHitGround = true;
                    damageDagger(1, true);
                }
                return;
            }
        } else
        {
            ticksInAir++;
        }
        Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.canBeCollidedWith() || entity1 == owner && ticksInAir < 5)
            {
                continue;
            }
            float f3 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f3, f3, f3);
            MovingObjectPosition movingobjectposition2 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
            if(movingobjectposition2 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition2.hitVec);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            MovingObjectPosition movingobjectposition1 = new MovingObjectPosition(entity);
            onHit(movingobjectposition1);
        }
        if(movingobjectposition != null)
        {
            onHit(movingobjectposition);
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f1) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f2 = 0.99F;
        float f4 = 0.03F;
        if(isInWater())
        {
            for(int k = 0; k < 4; k++)
            {
                float f5 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f5, posY - motionY * (double)f5, posZ - motionZ * (double)f5, motionX, motionY, motionZ);
            }

            f2 = 0.8F;
        }
        motionX *= f2;
        motionY *= f2;
        motionZ *= f2;
        motionY -= f4;
        setPosition(posX, posY, posZ);
        if(!inGround)
        {
            prevBoomerangRotation = boomerangRotation;
            for(boomerangRotation += 36F; boomerangRotation > 360F; boomerangRotation -= 360F) { }
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("shake", (byte)arrowShake);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        arrowShake = nbttagcompound.getByte("shake") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        onCollideWithPlayer(entityplayer, true);
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer, boolean flag)
    {
        if(returnStack == null || returnStack.stackSize == 0 || worldObj.isRemote)
        {
            return;
        }
        if(!flag || inGround && arrowShake <= 0)
        {
            if(!flag || returnsTo != null && !returnsTo.isDead && returnsTo != entityplayer)
            {
                return;
            }
            Infi2x2.addToInv(entityplayer, returnStack, true);
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((Infi2x2.rand.nextFloat() - Infi2x2.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.onItemPickup(this, 1);
            kill();
        }
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public int texID;
    public int tex2ID;
    public ItemStack returnStack;
    public int returnStackSlot;
    public boolean hasHitGround;
    public boolean hasHitMob;
    float prevBoomerangRotation;
    float boomerangRotation;
    public Entity returnsTo;
    public int damageDealt;
    public int maxGroundTicks;
    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected int inTile;
    public boolean inGround;
    public int arrowShake;
    public EntityLiving owner;
    protected int ticksInGround;
    protected int ticksInAir;
}
