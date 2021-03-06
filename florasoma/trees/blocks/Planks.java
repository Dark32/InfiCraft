package florasoma.trees.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/*
 * This class is for a single tree with a 2x2 base and inside textures 
 */

public class Planks extends Block
{
	public Planks(int id, int texPos, String tex) 
	{
		super(id, Material.wood);
		blockIndexInTexture = texPos;
		texture = tex;
		setBurnProperties(this.blockID, 5, 20);
		this.setHardness(2.0f);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
    {
		return blockIndexInTexture + meta;
    }
	
	@Override
	public String getTextureFile()
    {
        return texture;
    }
	
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
		if (metadata == 1 || metadata == 4)
			return 0;
        return blockFlammability[blockID];
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
    	if (metadata == 1 || metadata == 4)
			return 0;
        return blockFireSpreadSpeed[blockID];
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

	private String texture;
}
