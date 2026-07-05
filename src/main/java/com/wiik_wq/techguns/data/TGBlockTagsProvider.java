package com.wiik_wq.techguns.data;

import com.wiik_wq.techguns.TechgunsReborn;
import com.wiik_wq.techguns.common.content.TGBlockCatalog;
import com.wiik_wq.techguns.common.registration.TGBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TGBlockTagsProvider extends BlockTagsProvider {

    public TGBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TechgunsReborn.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addBlocks(BlockTags.STAIRS, TGBlockCatalog.STAIRS);
        addBlocks(BlockTags.SLABS, TGBlockCatalog.SLABS);
        addBlocks(BlockTags.WALLS, TGBlockCatalog.WALLS);
    }

    private void addBlocks(TagKey<Block> tagKey, Map<String, String> blocks) {
        var appender = tag(tagKey);
        blocks.keySet().forEach(id -> appender.add(TGBlocks.ENTRIES.get(id).block().get()));
    }
}
