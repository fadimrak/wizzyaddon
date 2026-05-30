/*    */ package com.example.addon.mixin;
/*    */ 
/*    */ import com.example.addon.AddonTemplate;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_542;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_310.class})
/*    */ public abstract class ExampleMixin
/*    */ {
/*    */   @Inject(method = {"<init>"}, at = {@At("TAIL")})
/*    */   private void onGameLoaded(class_542 args, CallbackInfo ci) {
/* 29 */     AddonTemplate.LOG.info("Hello from ExampleMixin!");
/*    */   }
/*    */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\mixin\ExampleMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */