package mDiyo.inficraft.flora.berries;

import net.minecraft.src.*;

public class BerryBushItem extends ItemBlock
{

    public BerryBushItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(int md)
    {
        return md;
    }

    /* Place bushes on dirt, grass, or other bushes only */
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (!player.canPlayerEdit(x, y, z, side, stack) || !player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            return false;
        }
        int i1 = world.getBlockId(x, y, z);
        if ((i1 == Block.dirt.blockID || i1 == Block.grass.blockID || i1 == FloraBerries.berryBush.blockID) && world.isAirBlock(x, y + 1, z))
        {
            world.setBlockAndMetadataWithNotify(x, y + 1, z, FloraBerries.berryBush.blockID, stack.getItemDamage());
            if (!player.capabilities.isCreativeMode)
            	stack.stackSize--;
            world.playAuxSFX(2001, x, y, z, Block.grass.blockID);
            return true;
        }
        else
        {
            return false;
        }
    }

    /* Block name in inventory */
    @Override
    public String getItemNameIS(ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.").append(blockType[itemstack.getItemDamage()]).append("berryBush").toString();
    }
    public static final String blockType[] =
    {
        "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo",
        "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo"
    };
    
    /* Render fix */
    @Override
    public int getIconFromDamage(int meta)
    {
        return FloraBerries.instance.berryBush.getBlockTextureFromSideAndMetadata(2, BlockCloth.getBlockFromDye(meta));
    }
}