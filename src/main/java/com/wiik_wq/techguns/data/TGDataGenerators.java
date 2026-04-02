package com.wiik_wq.techguns.data;

import com.wiik_wq.techguns.TechgunsReborn;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechgunsReborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class TGDataGenerators {

    private TGDataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new TGBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new TGItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new TGLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeClient(), new TGLanguageProvider(output, "ru_ru"));
        generator.addProvider(event.includeClient(), new TGLanguageProvider(output, "de_de"));
    }
}
