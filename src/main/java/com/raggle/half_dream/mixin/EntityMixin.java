package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.DreamEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

@Mixin(Entity.class)
public abstract class EntityMixin implements DreamEntity{
	
	private NbtCompound dreamNbt;
	
	@Override
	public boolean isDream() {
		return getPersistantData().getBoolean("half_dream");
	}

	@Override
	public void setDream(boolean b) {
		getPersistantData().putBoolean("half_dream", b);
	}
	@Override
	public NbtCompound getPersistantData() {
		if (dreamNbt == null) {
			dreamNbt = new NbtCompound();
		}
		
		return dreamNbt;
	}
	
	@Shadow public abstract World getWorld();
	
	@Inject(method = "writeNbt", at = @At("HEAD"))
	public void writeNbt(NbtCompound tag, CallbackInfoReturnable<NbtCompound> ci) {
		if(dreamNbt != null) {
			tag.put("half_dream", dreamNbt);
		}
	}
	@Inject(method = "readNbt", at = @At("HEAD"))
	public void readNbt(NbtCompound tag, CallbackInfo ci) {
		if(tag.contains("half_dream", 10)) {
			dreamNbt = tag.getCompound("half_dream");
		}
	}

	@Inject(method = "isInsideWall", at = @At("HEAD"), cancellable = true)
	private void isInsideWall(CallbackInfoReturnable<Boolean> cir) {
		if(this.isDream()) {
			cir.setReturnValue(false);
		}
	}
}
