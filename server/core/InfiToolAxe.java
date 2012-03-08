package net.minecraft.src.core;

import net.minecraft.src.forge.ForgeHooks;
import net.minecraft.src.forge.ITextureProvider;
import java.util.Random;
import net.minecraft.src.*;

public class InfiToolAxe extends InfiToolBase
    implements ITextureProvider
{
    private static Material materialEffectiveAgainst[];
    private static Random random = new Random();

    public InfiToolAxe(int i, int j, int k, float f, int l, int i1, int j1)
    {
        super(i, 3, j, k, f, l, true, materialEffectiveAgainst, i1, j1);
    }

    public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer entityplayer)
    {
        World world = entityplayer.worldObj;
        if (world.isRemote)
        {
            return false;
        }
        int l = world.getBlockId(i, j, k);
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = true;
        boolean flag1 = true;
        if (type1 == type2)
        {
            flag = powers(itemstack, l, i, j, k, world, entityplayer, i1, type1);
        }
        else
        {
            if (random.nextInt(100) + 1 <= 80)
            {
                flag = powers(itemstack, l, i, j, k, world, entityplayer, i1, type1);
            }
            if (random.nextInt(100) + 1 <= 20)
            {
                flag1 = powers(itemstack, l, i, j, k, world, entityplayer, i1, type2);
            }
        }
        if (!ForgeHooks.canHarvestBlock(Block.blocksList[l], entityplayer, i1))
        {
            flag = false;
        }
        if (!flag || !flag1)
        {
            world.playAuxSFX(2001, i, j, k, l + i1 * 256);
            world.setBlockWithNotify(i, j, k, 0);
            onBlockDestroyed(itemstack, l, i, j, k, entityplayer);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving)
    {
        int i1 = itemstack.getItemDamage();
        if (i1 >= dur)
        {
            itemstack.stackSize = 0;
        }
        if (type1 == 3 || type2 == 3 || type1 == 4 || type2 == 4 || type1 == 8 || type2 == 8)
        {
            if (random.nextInt(100) + 1 <= 90)
            {
                itemstack.damageItem(1, entityliving);
            }
        }
        else
        {
            itemstack.damageItem(1, entityliving);
        }
        return true;
    }

    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
        World world = entityliving1.worldObj;
        if (type1 == type2)
        {
            attacks(itemstack, world, entityliving1, entityliving, type1);
        }
        else
        {
            if (random.nextInt(100) + 1 <= 80)
            {
                attacks(itemstack, world, entityliving1, entityliving, type1);
            }
            if (random.nextInt(100) + 1 <= 20)
            {
                attacks(itemstack, world, entityliving1, entityliving, type2);
            }
        }
        int i = itemstack.getItemDamage();
        if (i >= dur)
        {
            itemstack.stackSize = 0;
        }
        if (type1 == 3 || type2 == 3 || type1 == 4 || type2 == 4 || type1 == 8 || type2 == 8)
        {
            if (random.nextInt(100) + 1 <= 90)
            {
                itemstack.damageItem(1, entityliving1);
            }
        }
        else
        {
            itemstack.damageItem(1, entityliving1);
        }
        return true;
    }

    public boolean powers(ItemStack itemstack, int i, int j, int k, int l, World world, EntityLiving entityliving,
            int i1, int j1)
    {
        switch (j1)
        {
            case 1:
                InfiToolPowers.splintering(j, k, l, mod_InfiTools.woodSplinters, world);
                break;

            case 2:
                InfiToolPowers.splintering(j, k, l, mod_InfiTools.stoneShard, world);
                break;

            case 7:
                InfiToolPowers.splintering(j, k, l, mod_InfiTools.obsidianShard, world);
                break;

            case 8:
                InfiToolPowers.splintering(j, k, l, mod_InfiTools.sandstoneShard, world);
                break;

            case 12:
                InfiToolPowers.splintering(j, k, l, mod_InfiTools.netherrackShard, world);
                break;

            case 13:
                InfiToolPowers.splintering(j, k, l, Item.lightStoneDust, world);
                break;

            case 14:
                InfiToolPowers.freezing(j, k, l, i, i1, world, entityliving);
                break;

            case 15:
                InfiToolPowers.burning(j, k, l, i, i1, world, entityliving);
                break;

            case 16:
                InfiToolPowers.slimePower(j, k, l, world);
                break;
        }
        return j1 != 14 && j1 != 15;
    }

    public void attacks(ItemStack itemstack, World world, EntityLiving entityliving, EntityLiving entityliving1, int i)
    {
        switch (i)
        {
            case 1:
                InfiToolPowers.splinterAttack(entityliving, mod_InfiTools.woodSplinters, world);
                break;

            case 2:
                InfiToolPowers.splinterAttack(entityliving, mod_InfiTools.stoneShard, world);
                break;

            case 7:
                InfiToolPowers.splinterAttack(entityliving, mod_InfiTools.obsidianShard, world);
                break;

            case 8:
                InfiToolPowers.splinterAttack(entityliving, mod_InfiTools.sandstoneShard, world);
                break;

            case 12:
                InfiToolPowers.splinterAttack(entityliving, mod_InfiTools.netherrackShard, world);
                break;

            case 13:
                InfiToolPowers.splinterAttack(entityliving, Item.lightStoneDust, world);
                break;

            case 14:
                entityliving1.freeze();
                break;

            case 15:
                entityliving1.setFire(100);
                break;

            case 20:
                entityliving1.setFire(100);
                break;
        }
    }

    public float getStrVsBlock(ItemStack itemstack, Block block, int md)
    {
        for (int i = 0; i < materialEffectiveAgainst.length; i++)
        {
            if (materialEffectiveAgainst[i] == block.blockMaterial)
            {
                if (type1 == 2 || type1 == 8 || type1 == 10 || type1 == 12 || type1 == 14 || type1 == 15 || type1 == 17 || type1 == 20)
                {
                    return efficiencyOnProperMaterial + (float)itemstack.getItemDamage() / 100F;
                }
                else
                {
                    return efficiencyOnProperMaterial;
                }
            }
        }

        return 1.0F;
    }

    public String getTextureFile()
    {
        return "/infitools/infaxes.png";
    }

    public int getItemEnchantability()
    {
        return mod_InfiTools.enchantBase(type1);
    }

    static
    {
        materialEffectiveAgainst = (new Material[]
                {
                    Material.wood, Material.circuits, Material.cactus
                });
    }
}
