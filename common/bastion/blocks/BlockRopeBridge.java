package bastion.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class BlockRopeBridge extends BlockTensile
{
	public BlockRopeBridge(int var1, int var2, Material var3, int var4)
	{
		super(var1, var2, var3, var4);
	}

	/*public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5)
	{
		if (var1.getBlockId(var2, var3 - 1, var4) == 0)
		{
			EntityPlayerSP var6 = ModLoader.getMinecraftInstance().thePlayer;
			double var7 = var6.posX - var2;
			double var9 = var6.posY - var3;
			double var11 = var6.posZ - var4;

			if ((Math.sqrt(var7 * var7 + var11 * var11) <= 1.0D) && (var9 > 0.0D) && (var9 < 3.0D))
			{
				var6.triggerAchievement(mod_Zipline.achievementBreakRopeBridge);
			}
		}
	}*/
}