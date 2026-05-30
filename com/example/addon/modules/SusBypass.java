/*    */ package com.example.addon.modules;
/*    */ 
/*    */ import com.example.addon.AddonTemplate;
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ public class SusBypass
/*    */   extends Module
/*    */ {
/*    */   public SusBypass() {
/* 13 */     super(AddonTemplate.BASE_HUNTING, "sus-bypass", "bypasses /sus");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPlayerMove(PlayerMoveEvent event) {
/* 18 */     if (this.mc.field_1724 == null || this.mc.field_1687 == null) {
/*    */       return;
/*    */     }
/* 21 */     double nextY = this.mc.field_1724.method_23318();
/*    */ 
/*    */     
/* 24 */     if (nextY <= 16.0D) {
/*    */ 
/*    */       
/* 27 */       if (this.mc.method_1562() != null && this.mc.method_1562().method_48296() != null) {
/* 28 */         this.mc.method_1562().method_48296().method_10747((class_2561)class_2561.method_43470("SusBypass: Triggered at Y <= 16!"));
/*    */       }
/*    */ 
/*    */       
/* 32 */       toggle();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\modules\SusBypass.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */