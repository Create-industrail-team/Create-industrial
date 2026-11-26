
package net.createindustrialteam.createindustrial.data;

import static net.createindustrialteam.createindustrial.CreateIndustrial.MODID;

import net.createindustrialteam.createindustrial.CIBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CIBlockDataGen extends BlockStateProvider {

    public CIBlockDataGen(
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

        // Normal blocks
        addSimpleBlock(CIBlocks.KIRK_BLOCK.get(), "kirk_block");
        addSimpleBlock(CIBlocks.BLOCK_OF_ALUMINIUM.get(), "block_of_aluminium");
        addSimpleBlock(CIBlocks.BLOCK_OF_STEEL.get(), "block_of_steel");
        addSimpleBlock(CIBlocks.BAUXITE_ORE.get(), "bauxite_ore");
        addSimpleBlock(CIBlocks.BLOCK_OF_URANIUM.get(), "block_of_uranium");
        addSimpleBlock(CIBlocks.BLOCK_OF_PLUTONIUM.get(), "block_of_plutonium");
        addSimpleBlock(CIBlocks.BLOCK_OF_CALIFORNIUM.get(), "block_of_californium");
        addSimpleBlock(CIBlocks.CONDENSING_MESH.get(), "condensing_mesh");
        addSimpleBlock(CIBlocks.REFRACTORY_BRICKS.get(), "refractory_bricks");

        // Machine blocks (custom models)
        simpleBlockItem(
                CIBlocks.LASER_ENGRAVER.get(),
                models().getExistingFile(modLoc("block/laser_engraver"))
        );




        // Custom sided
        horizontalBlock(
                CIBlocks.BLAST_FURNACE_HATCH.get(),
                modLoc("block/refractory_bricks"),       // side
                modLoc("block/blast_furnace_hatch"),     // front
                modLoc("block/refractory_bricks") // top (and bottom)
        );

        simpleBlockItem(
                CIBlocks.BLAST_FURNACE_HATCH.get(),
                models().getExistingFile(modLoc("block/blast_furnace_hatch"))
        );

        simpleBlockWithItem( CIBlocks.REINFORCED_CONCRETE.get(),models().cubeBottomTop(
                                "reinforced_concrete",
                        modLoc("block/reinforced_concrete"),
                        modLoc("block/reinforced_concrete_top"),
                        modLoc("block/reinforced_concrete_top")));


        // BLOCKSETS
        addSimpleBlockSet(
                CIBlocks.REINFORCED_CONCRETE_SLAB.get(),
                CIBlocks.REINFORCED_CONCRETE_STAIRS.get(),
                CIBlocks.REINFORCED_CONCRETE_WALL.get(),
                "reinforced_concrete"
        );
    }
}
