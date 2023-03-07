package com.example.examplemod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

@Mod(modid = "examplemod", version = "1.0")
public class Modstest {

    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    public static ItemCompassWithChests itemCompassWithChests;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        itemCompassWithChests = new ItemCompassWithChests();
        itemCompassWithChests.setUnlocalizedName("item_Compas").setTextureName("examplemod:icwc").setCreativeTab(CreativeTabs.tabTools);
        GameRegistry.registerItem(itemCompassWithChests,"item_Compas");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        // Check if placed block is a chest
        Block block = event.block;
        if (block instanceof BlockChest) {
            // Get chunk coordinates
            int chunkX = event.x >> 4;
            int chunkZ = event.z >> 4;

            // Count chests in chunk
            int chestCount = 0;
            for (int x = chunkX << 4; x < (chunkX << 4) + 16; x++) {
                for (int z = chunkZ << 4; z < (chunkZ << 4) + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block b = event.world.getBlock(x, y, z);
                        if (b instanceof BlockChest) {
                            chestCount++;
                        }
                    }
                }
            }

            // Compute percentage
            int chunkSize = 16 * 16 * 256;
            int percent = (int) ((float) chestCount / (float) chunkSize * 100.0f);

            // Send message to player
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Chests in chunk: " + chestCount + " (" + percent + "%)"));
        }
    }

    public static class ItemCompassWithChests extends Item {

        public static final String ICON_NAME = "compass_with_chests";

        @Override
        public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
            return super.onLeftClickEntity(stack, player, entity);
        }

        private IIcon icon;

        public ItemCompassWithChests() {
        }}}