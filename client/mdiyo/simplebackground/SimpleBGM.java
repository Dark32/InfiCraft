package mDiyo.simplebackground;

import net.minecraft.src.GameSettings;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPoolEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import paulscode.sound.SoundSystem;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;

/* Constant background music
 * Flavorlicious!
 */

@Mod(modid = "SimpleBGM", name = "Simple Background Music", version = "1.4.5_2012.12.2")
public class SimpleBGM
{
	SoundSystem bgm;
	GameSettings options;
	String currentMusic;

	@Instance("SimpleBGM")
	public static SimpleBGM instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent evt)
	{
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent evt)
	{
		bgm = SoundManager.sndSystem;
		options = FMLClientHandler.instance().getClient().gameSettings;
		playMenuMusic("Windswept.ogg");
		TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
	}

	@SideOnly(Side.CLIENT)
	public void playBackgroundMusic(String sound)
	{
		if (options.musicVolume > 0f && sound != currentMusic)
		{
			bgm.stop(currentMusic);
			SoundPoolEntry song = (SoundPoolEntry) SoundHandler.music.get(sound);
			//bgm.fadeOutIn(currentMusic, song.soundUrl, song.soundName, 1500, 3000);

			bgm.backgroundMusic(sound, song.soundUrl, song.soundName, true);
			bgm.setVolume(sound, options.musicVolume);
			bgm.play(sound);
			
			currentMusic = sound;
			System.out.println("Playing background music: "+sound);
			
		}
	}

	public void playMenuMusic(String sound)
	{
		//bgm.stop(currentMusic);
		SoundPoolEntry song = (SoundPoolEntry) SoundHandler.music.get(sound);
		bgm.backgroundMusic(sound, song.soundUrl, song.soundName, true);
		bgm.setVolume(sound, options.musicVolume);
		bgm.play(sound);
		currentMusic = sound;
		System.out.println("Playing menu music: "+sound);
	}

	public void stopMusic()
	{
		bgm.stop(currentMusic);
	}

}