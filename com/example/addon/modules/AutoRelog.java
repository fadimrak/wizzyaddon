/*    */ package com.example.addon.modules;
/*    */ import com.example.addon.AddonTemplate;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.IntSetting;
/*    */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ public class AutoRelog extends Module {
/* 12 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */ 
/*    */ 
/*    */   
/* 16 */   private final Setting<Keybind> relogKey = this.sgGeneral.add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder())
/* 17 */       .name("relog-button"))
/* 18 */       .description("Press this key to disconnect immediately."))
/* 19 */       .defaultValue(Keybind.none()))
/* 20 */       .build());
/*    */ 
/*    */   
/* 23 */   private final Setting<Integer> minHeight = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 24 */       .name("relog-height"))
/* 25 */       .description("Disconnects you automatically if your Y-level drops below this value."))
/* 26 */       .defaultValue(Integer.valueOf(-64)))
/* 27 */       .min(-128)
/* 28 */       .max(320)
/* 29 */       .sliderMin(-64)
/* 30 */       .sliderMax(0)
/* 31 */       .build());
/*    */   
/*    */   private boolean wasKeyPressed = false;
/*    */   
/* 35 */   private int loginTicksDelay = 0;
/*    */   private boolean wasInWorld = false;
/*    */   
/*    */   public AutoRelog() {
/* 39 */     super(AddonTemplate.MISC, "auto-relog", "Instantly disconnects you from the server via keybind or low Y-level.");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 45 */     this.loginTicksDelay = 200;
/* 46 */     this.wasInWorld = (this.mc.field_1687 != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 51 */     this.wasInWorld = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 57 */     if (this.mc.field_1724 == null || this.mc.field_1687 == null) {
/* 58 */       this.wasInWorld = false;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 63 */     if (!this.wasInWorld) {
/* 64 */       this.loginTicksDelay = 200;
/* 65 */       this.wasInWorld = true;
/*    */     } 
/*    */ 
/*    */     
/* 69 */     if (this.loginTicksDelay > 0) {
/* 70 */       this.loginTicksDelay--;
/*    */     }
/*    */ 
/*    */     
/* 74 */     boolean isPressed = ((Keybind)this.relogKey.get()).isPressed();
/* 75 */     if (isPressed && !this.wasKeyPressed) {
/* 76 */       this.wasKeyPressed = true;
/* 77 */       triggerDisconnect(); return;
/*    */     } 
/* 79 */     if (!isPressed) {
/* 80 */       this.wasKeyPressed = false;
/*    */     }
/*    */ 
/*    */     
/* 84 */     if (this.loginTicksDelay == 0 && this.mc.field_1724.method_23318() < ((Integer)this.minHeight.get()).intValue()) {
/* 85 */       triggerDisconnect();
/*    */     }
/*    */   }
/*    */   
/*    */   private void triggerDisconnect() {
/* 90 */     if (this.mc.method_1562() != null && this.mc.method_1562().method_48296() != null)
/* 91 */       this.mc.method_1562().method_48296().method_10747((class_2561)class_2561.method_43470("AutoRelog: Disconnected successfully!")); 
/*    */   }
/*    */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\modules\AutoRelog.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */