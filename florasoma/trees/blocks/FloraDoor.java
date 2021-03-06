package florasoma.trees.blocks;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import florasoma.trees.FloraTrees;

public class FloraDoor extends BlockDoor
{
    private int itemID;
	
	public FloraDoor(int id, int tex, Material material, int item)
    {
        super(id, material); //146
        blockIndexInTexture = tex;
        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        itemID = item;
        this.setHardness(3F);
        this.setStepSound(Block.soundWoodFootstep);
        this.setBlockName("redwoodDoor");
        this.disableStats();
        this.setRequiresSelfNotify();
    }

    public int idDropped(int i, Random random, int j)
    {
    	return itemID;
    }

    
    public String getTextureFile()
    {
        return FloraTrees.texture;
    }
}
