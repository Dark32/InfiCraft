package inficraft.infiblocks.magicslabs;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class StainedGlassMagicSlabItem extends ItemBlock
{
	public static final String blockType[] =
	{
	    "white", "orange", "magenta", "lightblue", "yellow", "lime", "pink", "gray", "lightgray", "cyan",
	    "purple", "blue", "brown", "green", "red", "black"
	};

    public StainedGlassMagicSlabItem(int i)
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

    public String getItemNameIS(ItemStack itemstack)
    {
        return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("GlassSlab").toString();
    }
}
