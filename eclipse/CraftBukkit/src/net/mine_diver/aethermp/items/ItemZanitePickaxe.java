package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemTool;
import net.minecraft.server.ToolBase;

public class ItemZanitePickaxe extends ItemTool {

    protected ItemZanitePickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, blocksEffectiveAgainst);
    }
    
    @Override
    public float a(ItemStack itemstack, Block block) {
        return super.a(itemstack, block) * ((2.0F * (float)itemstack.getData()) / (float)itemstack.getItem().e() + 0.5F);
    }
    
    @Override
    public boolean a(Block block) {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
            if(blocksEffectiveAgainst[i].id == block.id)
                return true;
        return false;
    }
    
    @Override
    public ToolBase getToolBase() {
        return ToolBase.Pickaxe;
    }

    private static Block[] blocksEffectiveAgainst = new Block[] {
            BlockManager.Holystone,
            BlockManager.Icestone,
            BlockManager.EnchantedGravitite,
            BlockManager.GravititeOre,
            BlockManager.ZaniteOre,
            BlockManager.AmbrosiumOre,
            BlockManager.LightDungeonStone,
            BlockManager.DungeonStone,
            BlockManager.Pillar,
            BlockManager.Enchanter,
            //BlockManager.Incubator,
            BlockManager.ZaniteBlock,
            //BlockManager.Freezer,
            BlockManager.QuicksoilGlass
            };
}
