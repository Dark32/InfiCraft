package mDiyo.inficraft.infitools.core;
import java.util.List;

import mDiyo.inficraft.infitools.base.mod_InfiBase;
import mDiyo.inficraft.infitools.core.buckets.BucketInteractHandler;
import mDiyo.inficraft.infitools.core.buckets.InfiBucketHandler;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ShapedRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;


@Mod(modid = "InfiTools", name = "InfiTools", version = "3.1")
public class mod_InfiTools
{
	@Instance("InfiTools")
	public static mod_InfiTools instance;

	@Init
	public void load(FMLInitializationEvent event) 
	{
		PropsHelperInfiTools.initProps();
		removeVanillaRecipes();
		Pickaxes.init();
		Swords.init();
		Shovels.init();
		Axes.init();
		Hoes.init();
		Items.init();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new InfiBucketHandler());
        MinecraftForge.EVENT_BUS.register(new BucketInteractHandler());
	}

	@ForgeSubscribe
	private void registerOre(OreRegisterEvent event)
	{
		String ore = event.Name;
		ItemStack itemstack = event.Ore;
	    if(PropsHelperInfiTools.enableCopperTools && ore.equals("ingotCopper"))
	    {
	    	Pickaxes.addCopperTools(itemstack);
	    	Swords.addCopperTools(itemstack);
	    	Shovels.addCopperTools(itemstack);
	    	Axes.addCopperTools(itemstack);
	    	Hoes.addCopperTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableBronzeTools && ore.equals("ingotBronze"))
	    {
	        Pickaxes.addBronzeTools(itemstack);
	        Swords.addBronzeTools(itemstack);
	        Shovels.addBronzeTools(itemstack);
	        Axes.addBronzeTools(itemstack);
	        Hoes.addBronzeTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableWorkedIronTools && ore.equals("ingotRefinedIron"))
	    {
	        Pickaxes.addWorkedIronTools(itemstack);
	        Swords.addWorkedIronTools(itemstack);
	        Shovels.addWorkedIronTools(itemstack);
	        Axes.addWorkedIronTools(itemstack);
	        Hoes.addWorkedIronTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableSteelTools && ore.equals("ingotSteel"))
	    {
	        Pickaxes.addSteelTools(itemstack);
	        Swords.addSteelTools(itemstack);
	        Shovels.addSteelTools(itemstack);
	        Axes.addSteelTools(itemstack);
	        Hoes.addSteelTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableCobaltTools && ore.equals("ingotCobalt"))
	    {
	        Pickaxes.addCobaltTools(itemstack);
	        Swords.addCobaltTools(itemstack);
	        Shovels.addCobaltTools(itemstack);
	        Axes.addCobaltTools(itemstack);
	        Hoes.addCobaltTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableArditeTools && ore.equals("ingotArdite"))
	    {
	        Pickaxes.addArditeTools(itemstack);
	        Swords.addArditeTools(itemstack);
	        Shovels.addArditeTools(itemstack);
	        Axes.addArditeTools(itemstack);
	        Hoes.addArditeTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableManyullynTools && ore.equals("ingotManyullyn"))
	    {
	    	Pickaxes.addManyullynTools(itemstack);
	    	Swords.addManyullynTools(itemstack);
	    	Shovels.addManyullynTools(itemstack);
	    	Axes.addManyullynTools(itemstack);
	    	Hoes.addManyullynTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableUraniumTools && ore.equals("ingotUranium"))
	    {
	        Pickaxes.addUraniumTools(itemstack);
	        Swords.addUraniumTools(itemstack);
	        Shovels.addUraniumTools(itemstack);
	        Axes.addUraniumTools(itemstack);
	        Hoes.addUraniumTools(itemstack);
	    }
	    if(PropsHelperInfiTools.enableStoneTools && (ore.equals("customCobblestone") || ore.equals("customStone")))
	    {
	        Pickaxes.addStoneTools(itemstack);
	        Swords.addStoneTools(itemstack);
	        Shovels.addStoneTools(itemstack);
	        Axes.addStoneTools(itemstack);
	        Hoes.addStoneTools(itemstack);
	    }
	    if(ore.equals("customCobblestone"))
	    {
	    	Items.addStoneBowl(itemstack);
	    }
	}
	
	private void removeVanillaRecipes()
	{
		System.out.println("Note: Some conflicts here are supposed to happen");
		System.out.println("      if *Overwrite Vanilla Tools* is enabled");
		ItemStack[] vanillaTools = {
			new ItemStack(Item.axeWood), new ItemStack(Item.pickaxeWood), new ItemStack(Item.shovelWood), 
			new ItemStack(Item.swordWood),new ItemStack(Item.hoeWood),
			
			new ItemStack(Item.axeStone), new ItemStack(Item.pickaxeStone), new ItemStack(Item.shovelStone), 
			new ItemStack(Item.swordStone),new ItemStack(Item.hoeStone),
			
			new ItemStack(Item.axeSteel), new ItemStack(Item.pickaxeSteel), new ItemStack(Item.shovelSteel), 
			new ItemStack(Item.swordSteel),new ItemStack(Item.hoeSteel),
			
			new ItemStack(Item.axeDiamond), new ItemStack(Item.pickaxeDiamond), new ItemStack(Item.shovelDiamond), 
			new ItemStack(Item.swordDiamond),new ItemStack(Item.hoeDiamond),
			
			new ItemStack(Item.axeGold), new ItemStack(Item.pickaxeGold), new ItemStack(Item.shovelGold), 
			new ItemStack(Item.swordGold),new ItemStack(Item.hoeGold),
			
			new ItemStack(Item.bucketEmpty)
		};
		
		for (int i = 0; i < vanillaTools.length; i++)
		{
			removeRecipe(vanillaTools[i]);
		}		
	}
	
	private void removeRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++) 
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes)tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();
			
				if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
					recipes.remove(i--);
				}
			}
		}
	}

}
