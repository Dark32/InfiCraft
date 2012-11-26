package mDiyo.inficraft.flora.clouds;

import java.util.Random;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class CloudGen extends WorldGenerator
{
    private int bID;
    private int meta;
    private int numberOfBlocks;
    private boolean isFlat;

    public CloudGen(int blockID, int metadata, int size, boolean flag)
    {
        bID = blockID;
        meta = metadata;
        numberOfBlocks = size;
        isFlat = flag;
    }

    public boolean generate(World world, Random random, int x, int y, int z)
    {
        int l = random.nextInt(3) - 1;
        int i1 = random.nextInt(3) - 1;
        for (int j1 = 0; j1 < numberOfBlocks; j1++)
        {
            x += (random.nextInt(3) - 1) + l;
            z += (random.nextInt(3) - 1) + i1;
            if (random.nextBoolean() && !isFlat || isFlat && random.nextInt(10) == 0)
            {
                y += random.nextInt(3) - 1;
            }
            for (int xIter = x; xIter < x + random.nextInt(4) + 3 * (isFlat ? 3 : 1); xIter++)
            {
                for (int yIter = y; yIter < y + random.nextInt(1) + 2; yIter++)
                {
                    for (int zIter = z; zIter < z + random.nextInt(4) + 3 * (isFlat ? 3 : 1); zIter++)
                    {
                        if (world.getBlockId(xIter, yIter, zIter) == 0 && Math.abs(xIter - x) + Math.abs(yIter - y) + Math.abs(zIter - z) < 4 * (isFlat ? 3 : 1) + random.nextInt(2))
                        {
                            setBlockAndMetadata(world, xIter, yIter, zIter, bID, meta);
                        }
                    }
                }
            }
        }

        return true;
    }
}
