package net.mine_diver.aethermp;

import java.io.File;
import java.net.URISyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPluginLoader;

import com.earth2me.essentials.Mob;
import com.earth2me.essentials.Mob.Enemies;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.crafting.WorkbenchManager;
import net.mine_diver.aethermp.dimension.DimensionManager;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.player.PlayerBaseAether;
import net.mine_diver.aethermp.proxy.ModLoaderLoggerProxy;
import net.mine_diver.aethermp.util.Achievements;
import net.mine_diver.aethermp.util.BlockPlacementHandler;
import net.minecraft.server.BaseMod;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ModLoader;
import net.minecraft.server.PlayerAPI;
import net.minecraft.server.SAPI;
import net.minecraft.server.mod_AetherMp;

public class Core {
	
	public void preInit(BaseMod mod) {
		ModLoader.SetInGameHook(mod, true, false);
		SAPI.interceptAdd(new BlockPlacementHandler());
		PlayerAPI.RegisterPlayerBase(PlayerBaseAether.class);
		new ModLoaderLoggerProxy();
	}
	
	public void init() {
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		WorkbenchManager.registerRecipes();
		EntityManager.registerEntities();
		DimensionManager.registerDimensions();
	}
	
	public void handleSendKey(EntityPlayer entityplayer, int key) {
		if (key == 0) {
			Environment env = entityplayer.getBukkitEntity().getWorld().getEnvironment();
			if (env.equals(Environment.valueOf(mod_AetherMp.nameDimensionAether.toUpperCase())))
                entityplayer.inventory.pickup(new ItemStack(ItemManager.LoreBook, 1, 2));
            else if(env.equals(Environment.NORMAL))
                entityplayer.inventory.pickup(new ItemStack(ItemManager.LoreBook, 1, 0));
            else if(env.equals(Environment.NETHER))
                entityplayer.inventory.pickup(new ItemStack(ItemManager.LoreBook, 1, 1));
		}
	}
	
	public void takenFromCrafting(EntityHuman entityhuman, ItemStack itemstack) {
		if (entityhuman instanceof EntityPlayer) {
			if(itemstack.id == BlockManager.Enchanter.id)
				Achievements.giveAchievement(Achievements.enchanter, (EntityPlayer) entityhuman);
	        if(itemstack.id == ItemManager.PickGravitite.id || itemstack.id == ItemManager.ShovelGravitite.id || itemstack.id == ItemManager.AxeGravitite.id || itemstack.id == ItemManager.SwordGravitite.id)
	        	Achievements.giveAchievement(Achievements.gravTools, (EntityPlayer) entityhuman);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void postInit(BaseMod mod, MinecraftServer game) {
		try {
			Bukkit.getServer().getPluginManager().loadPlugin(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()));
		} catch (InvalidPluginException | InvalidDescriptionException | UnknownDependencyException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
		if (game.server.getPluginManager().isPluginEnabled("Essentials")) {
			JavaPluginLoader jpl = (JavaPluginLoader)game.server.getPluginManager().getPlugin("Essentials").getPluginLoader();
			EntityManager.registerEssentialsEntities((Class<Mob>) jpl.getClassByName("com.earth2me.essentials.Mob"), (Class<Enemies>) jpl.getClassByName("com.earth2me.essentials.Mob$Enemies"));
		}
	}
}
