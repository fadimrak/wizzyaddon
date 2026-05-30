/*     */ package com.example.addon.modules;
/*     */ import com.example.addon.AddonTemplate;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2818;
/*     */ import net.minecraft.class_370;
/*     */ 
/*     */ public class WizzyChunkV2 extends Module {
/*  26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  27 */   private final SettingGroup sgRender = this.settings.createGroup("Rendering");
/*     */   
/*  29 */   private final Map<class_1923, Integer> flaggedChunks = new HashMap<>();
/*  30 */   private final Set<class_1923> scannedChunks = new HashSet<>();
/*  31 */   private final ArrayDeque<class_1923> scanQueue = new ArrayDeque<>();
/*     */ 
/*     */   
/*  34 */   private final Setting<Integer> targetLength = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("target-length")).defaultValue(Integer.valueOf(20))).min(1).build());
/*  35 */   private final Setting<Integer> sensitivity = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("sensitivity")).defaultValue(Integer.valueOf(1))).min(1).build());
/*  36 */   private final Setting<Boolean> chatFeedback = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("chat-feedback")).defaultValue(Boolean.valueOf(true))).build());
/*  37 */   private final Setting<Boolean> toastNotifications = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("toast-notifications")).defaultValue(Boolean.valueOf(true))).build());
/*  38 */   private final Setting<Integer> chunksPerTick = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("chunks-per-tick")).defaultValue(Integer.valueOf(5))).min(1).build());
/*     */   
/*  40 */   private final Setting<Boolean> drawTracers = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("draw-tracers")).defaultValue(Boolean.valueOf(true))).build());
/*  41 */   private final Setting<SettingColor> defaultColor = this.sgRender.add((Setting)((ColorSetting.Builder)(new ColorSetting.Builder()).name("base-color")).defaultValue(new SettingColor(255, 0, 255, 150)).build());
/*     */   
/*  43 */   private final Setting<Boolean> forceRescan = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("FORCE RESCAN")).defaultValue(Boolean.valueOf(false))).onChanged(val -> {
/*     */           this.flaggedChunks.clear(); this.scannedChunks.clear(); this.scanQueue.clear(); if (this.mc.field_1724 != null)
/*     */             info("Wizzy: Cache cleared.", new Object[0]); 
/*  46 */         })).build());
/*     */   
/*     */   public WizzyChunkV2() {
/*  49 */     super(AddonTemplate.BASE_HUNTING, "WizzyChunkV2", "Advanced vine detection.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  54 */     if (!AddonTemplate.isLicensed) {
/*  55 */       error("Access Denied!", new Object[0]);
/*  56 */       toggle();
/*     */       return;
/*     */     } 
/*  59 */     this.flaggedChunks.clear();
/*  60 */     this.scannedChunks.clear();
/*  61 */     this.scanQueue.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  66 */     if (!AddonTemplate.isLicensed || this.mc.field_1687 == null || this.mc.field_1724 == null)
/*     */       return; 
/*  68 */     int pX = (this.mc.field_1724.method_31476()).field_9181;
/*  69 */     int pZ = (this.mc.field_1724.method_31476()).field_9180;
/*     */     
/*  71 */     for (int x = pX - 12; x <= pX + 12; x++) {
/*  72 */       for (int z = pZ - 12; z <= pZ + 12; z++) {
/*  73 */         class_1923 cp = new class_1923(x, z);
/*  74 */         if (!this.scannedChunks.contains(cp) && !this.scanQueue.contains(cp) && this.mc.field_1687.method_2935().method_12123(x, z)) {
/*  75 */           this.scanQueue.addLast(cp);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     for (int i = 0; i < ((Integer)this.chunksPerTick.get()).intValue(); i++) {
/*  81 */       class_1923 cp = this.scanQueue.pollFirst();
/*  82 */       if (cp == null)
/*  83 */         break;  this.scannedChunks.add(cp);
/*  84 */       processChunk(cp);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isBaseBlock(class_2680 state) {
/*  89 */     return (state.method_27852(class_2246.field_10340) || state.method_27852(class_2246.field_10508) || state
/*  90 */       .method_27852(class_2246.field_10474) || state.method_27852(class_2246.field_10115) || state
/*  91 */       .method_27852(class_2246.field_10255) || state.method_27852(class_2246.field_10418) || state
/*  92 */       .method_27852(class_2246.field_10212) || state.method_27852(class_2246.field_10571) || state
/*  93 */       .method_27852(class_2246.field_10442) || state.method_27852(class_2246.field_28888) || state
/*  94 */       .method_27852(class_2246.field_10445) || state.method_27852(class_2246.field_9989));
/*     */   }
/*     */   
/*     */   private void processChunk(class_1923 cp) {
/*  98 */     class_2818 chunk = this.mc.field_1687.method_8497(cp.field_9181, cp.field_9180);
/*  99 */     Set<class_2338> visited = new HashSet<>();
/* 100 */     int longVineCount = 0;
/*     */     
/* 102 */     for (int x = 0; x < 16; x++) {
/* 103 */       for (int z = 0; z < 16; z++) {
/* 104 */         for (int y = -64; y < 319; y++) {
/* 105 */           class_2338 pos = new class_2338(cp.method_8326() + x, y, cp.method_8328() + z);
/* 106 */           if (!visited.contains(pos))
/*     */           {
/* 108 */             if (chunk.method_8320(pos).method_27852(class_2246.field_10597)) {
/* 109 */               int length = 0;
/* 110 */               class_2338 current = pos;
/* 111 */               boolean touchingBaseBlock = false;
/*     */               
/* 113 */               for (; chunk.method_8320(current.method_10084()).method_27852(class_2246.field_10597); current = current.method_10084());
/* 114 */               while (chunk.method_8320(current).method_27852(class_2246.field_10597)) {
/* 115 */                 visited.add(current);
/* 116 */                 length++;
/* 117 */                 class_2338[] neighbors = { current.method_10095(), current.method_10072(), current.method_10078(), current.method_10067(), current.method_10084() };
/* 118 */                 for (class_2338 adj : neighbors) {
/* 119 */                   if (isBaseBlock(this.mc.field_1687.method_8320(adj))) { touchingBaseBlock = true; break; }
/*     */                 
/* 121 */                 }  current = current.method_10074();
/* 122 */                 if (length > 200)
/*     */                   break; 
/*     */               } 
/* 125 */               if (length >= ((Integer)this.targetLength.get()).intValue() && touchingBaseBlock) longVineCount++; 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     if (longVineCount >= ((Integer)this.sensitivity.get()).intValue() && 
/* 132 */       !this.flaggedChunks.containsKey(cp)) {
/* 133 */       this.flaggedChunks.put(cp, Integer.valueOf(longVineCount));
/* 134 */       boolean isGold = (longVineCount >= 12);
/*     */       
/* 136 */       if (((Boolean)this.chatFeedback.get()).booleanValue()) {
/* 137 */         info((isGold ? "§6GOLDEN CHUNK" : "§fCluster") + " found at " + (isGold ? "§6GOLDEN CHUNK" : "§fCluster") + ", " + cp.field_9181, new Object[0]);
/*     */       }
/*     */       
/* 140 */       if (((Boolean)this.toastNotifications.get()).booleanValue()) {
/* 141 */         this.mc.method_1566().method_1999((class_368)new class_370(class_370.class_9037.field_47586, 
/*     */               
/* 143 */               class_2561.method_30163("§6§lWizzyV2"), 
/* 144 */               class_2561.method_30163(isGold ? "§e§lGOLDEN CHUNK FOUND!" : "§fBase Cluster Detected")));
/*     */       }
/*     */       
/* 147 */       this.mc.field_1687.method_8396((class_1297)this.mc.field_1724, this.mc.field_1724.method_24515(), (class_3414)class_3417.field_14622.comp_349(), class_3419.field_15256, 1.0F, 2.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 154 */     if (this.mc.field_1724 == null) {
/*     */       return;
/*     */     }
/* 157 */     class_243 camPos = this.mc.field_1773.method_19418().method_71156();
/*     */     
/* 159 */     SettingColor goldColor = new SettingColor(255, 215, 0, 200);
/*     */     
/* 161 */     for (Map.Entry<class_1923, Integer> entry : this.flaggedChunks.entrySet()) {
/* 162 */       class_1923 cp = entry.getKey();
/* 163 */       SettingColor renderColor = (((Integer)entry.getValue()).intValue() >= 12) ? goldColor : (SettingColor)this.defaultColor.get();
/*     */       
/* 165 */       class_238 box = new class_238((cp.field_9181 * 16), 60.0D, (cp.field_9180 * 16), (cp.field_9181 * 16 + 16), 60.2D, (cp.field_9180 * 16 + 16));
/* 166 */       event.renderer.box(box, (Color)renderColor, (Color)renderColor, ShapeMode.Both, 0);
/*     */       
/* 168 */       if (((Boolean)this.drawTracers.get()).booleanValue())
/* 169 */         event.renderer.line(camPos.field_1352, camPos.field_1351, camPos.field_1350, (cp.field_9181 * 16 + 8), 60.1D, (cp.field_9180 * 16 + 8), (Color)renderColor); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\modules\WizzyChunkV2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */