package inficraft.toolconstruct.crafting;

import inficraft.toolconstruct.items.Pattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOnlyTake extends Slot
{

    public SlotOnlyTake(IInventory builder, int par3, int par4, int par5)
    {
        super(builder, par3, par4, par5);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }
}
