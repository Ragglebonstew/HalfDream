package com.raggle.half_dream.common.entity.effect;

import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.common.block.DreamBed;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class HalfAsleepStatusEffect extends StatusEffect {

	public HalfAsleepStatusEffect() {
		super(
			      StatusEffectType.BENEFICIAL, // whether beneficial or harmful for entities
			      0x98D982); // color in RGB
	}
	
		@Override
	  public boolean canApplyUpdateEffect(int duration, int amplifier) {
	    // In our case, we just make it return true so that it applies the status effect every tick.
	    return true;
	  }
	 
	  @Override
	  public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		  
	  }
	  
	  @Override
	  public void onRemoved(LivingEntity entity, AttributeContainer ac, int i) {
		  
		  //update entity's dream status
		  if(entity instanceof DreamEntity de && de.isDream()) {
			  de.setDream(false);
		  }
		  if(entity.getWorld().getRegistryKey() == DreamBed.DEEP_DREAM && entity instanceof ServerPlayerEntity player) {
				QuiltDimensions.teleport(player, entity.getServer().getWorld(World.OVERWORLD), new TeleportTarget(entity.getPos(), new Vec3d(0,0,0), 0, 0));
		  }

	  }
	  
	  @Override
	  public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		
		  //update entity's dream status
		  if(entity instanceof DreamEntity de && !de.isDream()) {
			  de.setDream(true);
		  }

	  }
	  

}
