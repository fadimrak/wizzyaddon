/*     */ package com.example.addon.modules;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class SafeAnchorMacro extends Module {
/*  19 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  21 */   private final Setting<Keybind> activateKey = this.sgGeneral.add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder())
/*  22 */       .name("key"))
/*  23 */       .description("Taste halten für legitime Sequenz."))
/*  24 */       .defaultValue(Keybind.none()))
/*  25 */       .build());
/*     */ 
/*     */   
/*  28 */   private final Setting<Integer> totemSlotSetting = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  29 */       .name("totem-slot"))
/*  30 */       .description("Slot mit dem Totem (1-9)."))
/*  31 */       .defaultValue(Integer.valueOf(1)))
/*  32 */       .min(1)
/*  33 */       .max(9)
/*  34 */       .build());
/*     */ 
/*     */   
/*  37 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  38 */       .name("action-delay"))
/*  39 */       .description("Ticks zwischen Schritten (Empfohlen: 4+ für Anticheats)."))
/*  40 */       .defaultValue(Integer.valueOf(4)))
/*  41 */       .min(1)
/*  42 */       .build());
/*     */   
/*     */   private int timer;
/*     */   private int step;
/*     */   private class_2338 shieldPos;
/*     */   private class_2338 anchorPos;
/*     */   private boolean running;
/*     */   
/*     */   public SafeAnchorMacro() {
/*  51 */     super(AddonTemplate.PVP, "safe-anchor-legit", "Legit-Version mit Rotationen gegen Anticheat-Bans.");
/*     */   }
/*     */   
/*     */   private void reset() {
/*  55 */     this.running = false;
/*  56 */     this.step = 0;
/*  57 */     this.timer = 0;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  62 */     if (!((Keybind)this.activateKey.get()).isPressed()) {
/*  63 */       reset();
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     if (!this.running) {
/*  68 */       calculatePositions();
/*  69 */       this.running = true;
/*     */     } 
/*     */     
/*  72 */     if (this.timer > 0) {
/*  73 */       this.timer--;
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     switch (this.step) { case 0:
/*  78 */         executeStep(class_1802.field_8801, this.shieldPos, true); break;
/*  79 */       case 1: executeStep(class_1802.field_23141, this.anchorPos, true); break;
/*  80 */       case 2: executeStep(class_1802.field_8801, this.anchorPos, false); break;
/*     */       case 3:
/*  82 */         InvUtils.swap(((Integer)this.totemSlotSetting.get()).intValue() - 1, false);
/*  83 */         executeStep((class_1792)null, this.anchorPos, false);
/*  84 */         reset();
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   private void executeStep(class_1792 item, class_2338 pos, boolean isPlace) {
/*  90 */     if (item != null && !swapTo(item)) {
/*  91 */       reset();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  96 */     class_243 hitVec = new class_243(pos.method_10263() + 0.4D + Math.random() * 0.2D, pos.method_10264() + 0.5D, pos.method_10260() + 0.4D + Math.random() * 0.2D);
/*     */     
/*  98 */     Rotations.rotate(Rotations.getYaw(hitVec), Rotations.getPitch(hitVec), () -> {
/*     */           if (isPlace) {
/*     */             class_3965 bhr = new class_3965(hitVec, class_2350.field_11036, pos.method_10074(), false);
/*     */             this.mc.field_1761.method_2896(this.mc.field_1724, class_1268.field_5808, bhr);
/*     */           } else {
/*     */             class_3965 bhr = new class_3965(hitVec, class_2350.field_11036, pos, false);
/*     */             this.mc.field_1761.method_2896(this.mc.field_1724, class_1268.field_5808, bhr);
/*     */           } 
/*     */           this.step++;
/*     */           this.timer = ((Integer)this.delay.get()).intValue();
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void calculatePositions() {
/* 113 */     double yawRad = Math.toRadians(this.mc.field_1724.method_36454());
/* 114 */     double lookX = -Math.sin(yawRad);
/* 115 */     double lookZ = Math.cos(yawRad);
/*     */     
/* 117 */     this.shieldPos = this.mc.field_1724.method_24515().method_10069((int)Math.round(lookX), 0, (int)Math.round(lookZ));
/* 118 */     this.anchorPos = this.shieldPos.method_10069((int)Math.round(lookX), 0, (int)Math.round(lookZ));
/*     */   }
/*     */   
/*     */   private boolean swapTo(class_1792 item) {
/* 122 */     int slot = InvUtils.findInHotbar(new class_1792[] { item }).slot();
/* 123 */     if (slot != -1) {
/* 124 */       InvUtils.swap(slot, false);
/* 125 */       return true;
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\modules\SafeAnchorMacro.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */