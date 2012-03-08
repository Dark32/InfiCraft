package net.minecraft.src.blocks;

import net.minecraft.src.*;
import net.minecraft.src.forge.MinecraftForgeClient;

public class InfiGlassItem extends CustomItemBlockInfi
{
	public static final String blockType[] =
	    {
	        "clear", "soul", "clearSoul"
	    };

    public InfiGlassItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
        MinecraftForgeClient.registerCustomItemRenderer(mod_InfiBlocks.infiGlass.blockID, this);
    }

    public int getIconFromDamage(int i)
    {
        return mod_InfiBlocks.infiGlass.getBlockTextureFromSideAndMetadata(0, i);
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("Glass").toString();
    }
}
