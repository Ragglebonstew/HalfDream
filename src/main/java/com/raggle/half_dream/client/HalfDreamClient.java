package com.raggle.half_dream.client;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import com.raggle.half_dream.common.registry.HDBlockRegistry;
import com.raggle.half_dream.common.registry.HDEntityRegistry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.StrayEntityRenderer;

public class HalfDreamClient implements ClientModInitializer {
	
    
	@Override
	public void onInitializeClient(ModContainer mod) {
			
		BlockRenderLayerMap.put(RenderLayer.getCutout(), HDBlockRegistry.DREAM_LEAVES);
		BlockRenderLayerMap.put(RenderLayer.getCutout(), HDBlockRegistry.DREAM_SAPLING);
		BlockRenderLayerMap.put(RenderLayer.getCutout(), HDBlockRegistry.SHEEP_LAUREL_BUSH);
		
        EntityRendererRegistry.register(HDEntityRegistry.HDSKELETON, StrayEntityRenderer::new);
        
	}
	
}
