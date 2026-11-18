
package net.createindustrialteam.createindustrial.blocks;

import static net.createindustrialteam.createindustrial.CreateIndustrial.MODID;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockDataGen extends BlockStateProvider {

    public ModBlockDataGen(
            PackOutput output,
            ExistingFileHelper exFileHelper
    ) {
        super(output, MODID, exFileHelper);
    }
    private void addSimpleBlock(Block block, String blockName) {
        simpleBlockWithItem( block,models().cubeAll(blockName, modLoc("block/" + blockName)));
    }

    private void addSimpleBlockSet(
            SlabBlock slab,
            StairBlock stair,
            WallBlock wall,
            String blockName
    ) {
        var texture = modLoc("block/" + blockName);


        slabBlock(slab, texture, texture);
        stairsBlock(stair, texture);
        wallBlock(wall, texture);

        itemModels().slab(blockName + "_slab", texture, texture, texture);
        itemModels().stairs(blockName + "_stairs", texture, texture, texture);

        itemModels().wallInventory(blockName + "_wall", texture);
    }

    private void addSimpleBlockSetWithBlock(
            Block block,
            SlabBlock slab,
            StairBlock stair,
            WallBlock wall,
            String blockName
    ) {
        simpleBlockWithItem(block, models().cubeAll(
                blockName,
                modLoc("block/" + blockName)
        ));

        slabBlock(slab,
                modLoc("block/" + blockName),
                modLoc("block/" + blockName));

        stairsBlock(stair,
                blockName,
                modLoc("block/" + blockName));

        wallBlock(wall,
                blockName,
                modLoc("block/" + blockName));
    }

    @Override
    protected void registerStatesAndModels() {

        // normal blocks
        addSimpleBlock(ModBlocks.KIRK_BLOCK.get(), "kirk_block");
        addSimpleBlock(ModBlocks.BLOCK_OF_ALUMINIUM.get(), "block_of_aluminium");
        addSimpleBlock(ModBlocks.BLOCK_OF_STEEL.get(), "block_of_steel");
        addSimpleBlock(ModBlocks.BAUXITE_ORE.get(), "bauxite_ore");
        addSimpleBlock(ModBlocks.BLOCK_OF_URANIUM.get(), "block_of_uranium");
        addSimpleBlock(ModBlocks.BLOCK_OF_PLUTONIUM.get(), "block_of_plutonium");
        addSimpleBlock(ModBlocks.BLOCK_OF_CALIFORNIUM.get(), "block_of_californium");
        addSimpleBlock(ModBlocks.CONDENSING_MESH.get(), "condensing_mesh");
        addSimpleBlock(ModBlocks.REFRACTORY_BRICKS.get(), "refractory_bricks");




        // Custom sided
        horizontalBlock(
                ModBlocks.BLAST_FURNACE_HATCH.get(),
                modLoc("block/refractory_bricks"),       // side
                modLoc("block/blast_furnace_hatch"),     // front
                modLoc("block/refractory_bricks") // top (and bottom)
        );

        simpleBlockItem(
                ModBlocks.BLAST_FURNACE_HATCH.get(),
                models().getExistingFile(modLoc("block/blast_furnace_hatch"))
        );

        simpleBlockWithItem( ModBlocks.REINFORCED_CONCRETE.get(),models().cubeBottomTop(
                                "reinforced_concrete",
                        modLoc("block/reinforced_concrete"),
                        modLoc("block/reinforced_concrete_top"),
                        modLoc("block/reinforced_concrete_top")));


        // BLOCKSETS
        addSimpleBlockSet(
                ModBlocks.REINFORCED_CONCRETE_SLAB.get(),
                ModBlocks.REINFORCED_CONCRETE_STAIRS.get(),
                ModBlocks.REINFORCED_CONCRETE_WALL.get(),
                "reinforced_concrete"
        );
    }
}
