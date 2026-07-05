package com.wiik_wq.techguns.data;

import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TGItemTagsProvider extends ItemTagsProvider {

    public TGItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TechgunsReborn.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
    }
}
