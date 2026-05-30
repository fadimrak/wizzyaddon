/*     */ package com.example.addon.modules;
/*     */ import com.example.addon.AddonTemplate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2636;
/*     */ import net.minecraft.class_2818;
/*     */ import net.minecraft.class_368;
/*     */ import net.minecraft.class_370;
/*     */ 
/*     */ public class SpawnerNotifier extends Module {
/*  26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  27 */   private final SettingGroup sgRender = this.settings.createGroup("Rendering");
/*     */   
/*  29 */   private final Setting<Boolean> chatAlert = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("chat-alert")).defaultValue(Boolean.valueOf(true))).build());
/*  30 */   private final Setting<Boolean> toastAlert = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("toast-alert")).defaultValue(Boolean.valueOf(true))).build());
/*  31 */   private final Setting<Boolean> soundAlert = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sound-alert")).defaultValue(Boolean.valueOf(true))).build());
/*     */   
/*  33 */   private final Setting<Boolean> pillarAlert = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pillar-alert")).defaultValue(Boolean.valueOf(true))).build());
/*  34 */   private final Setting<SettingColor> pillarColor = this.sgRender.add((Setting)((ColorSetting.Builder)(new ColorSetting.Builder()).name("pillar-color")).defaultValue(new SettingColor(255, 255, 0, 100)).build());
/*     */   
/*  36 */   private final Setting<Boolean> tracers = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("tracers")).defaultValue(Boolean.valueOf(true))).build());
/*  37 */   private final Setting<SettingColor> tracerColor = this.sgRender.add((Setting)((ColorSetting.Builder)(new ColorSetting.Builder()).name("tracer-color")).defaultValue(new SettingColor(0, 255, 200, 255)).build());
/*     */   
/*  39 */   private final Set<class_2338> notifiedSpawners = new HashSet<>();
/*  40 */   private final List<DetectedSpawner> activeSpawners = new ArrayList<>();
/*     */   
/*     */   public SpawnerNotifier() {
/*  43 */     super(AddonTemplate.BASE_HUNTING, "spawner-notifier", "Advanced spawner detection with stable native tracers.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  48 */     if (!AddonTemplate.isLicensed) {
/*  49 */       error("Nicht lizenziert!", new Object[0]);
/*  50 */       toggle();
/*     */       return;
/*     */     } 
/*  53 */     this.notifiedSpawners.clear();
/*  54 */     this.activeSpawners.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  59 */     if (!AddonTemplate.isLicensed || this.mc.field_1687 == null || this.mc.field_1724 == null)
/*     */       return; 
/*  61 */     this.activeSpawners.clear();
/*  62 */     int radius = 6;
/*  63 */     int pX = (this.mc.field_1724.method_31476()).field_9181;
/*  64 */     int pZ = (this.mc.field_1724.method_31476()).field_9180;
/*     */     
/*  66 */     for (int x = pX - radius; x <= pX + radius; x++) {
/*  67 */       for (int z = pZ - radius; z <= pZ + radius; z++) {
/*  68 */         class_2818 chunk = this.mc.field_1687.method_8497(x, z);
/*  69 */         if (chunk != null)
/*     */         {
/*  71 */           for (class_2586 be : chunk.method_12214().values()) {
/*  72 */             if (be instanceof class_2636) { class_2636 spawner = (class_2636)be;
/*  73 */               class_2338 pos = be.method_11016();
/*  74 */               if (pos == null)
/*     */                 continue; 
/*  76 */               String type = getSpawnerType(spawner, pos);
/*  77 */               this.activeSpawners.add(new DetectedSpawner(pos, type));
/*     */               
/*  79 */               if (!this.notifiedSpawners.contains(pos)) {
/*  80 */                 notifySpawner(type, pos);
/*  81 */                 this.notifiedSpawners.add(pos);
/*     */               }  }
/*     */           
/*     */           }  } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getSpawnerType(class_2636 spawner, class_2338 pos) {
/*     */     try {
/*  91 */       class_1297 entry = spawner.method_11390().method_8283((class_1937)this.mc.field_1687, pos);
/*  92 */       return (entry != null) ? entry.method_5864().method_5897().getString() : "Spawner";
/*  93 */     } catch (Exception e) {
/*  94 */       return "Spawner";
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifySpawner(String type, class_2338 pos) {
/*  99 */     if (((Boolean)this.chatAlert.get()).booleanValue()) info("§6" + type.toUpperCase() + "§f Spawner found!", new Object[0]);
/*     */     
/* 101 */     if (((Boolean)this.toastAlert.get()).booleanValue()) {
/* 102 */       this.mc.method_1566().method_1999((class_368)new class_370(class_370.class_9037.field_47586, 
/*     */             
/* 104 */             class_2561.method_30163("§6§lSpawner Found"), 
/* 105 */             class_2561.method_30163("§f" + type)));
/*     */     }
/*     */ 
/*     */     
/* 109 */     if (((Boolean)this.soundAlert.get()).booleanValue()) {
/* 110 */       this.mc.field_1724.method_5783((class_3414)class_3417.field_14622.comp_349(), 1.0F, 2.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 116 */     if (!AddonTemplate.isLicensed || this.activeSpawners.isEmpty() || this.mc.field_1724 == null)
/*     */       return; 
/* 118 */     for (DetectedSpawner ds : this.activeSpawners) {
/* 119 */       double tX = ds.pos.method_10263();
/* 120 */       double tY = ds.pos.method_10264();
/* 121 */       double tZ = ds.pos.method_10260();
/*     */ 
/*     */       
/* 124 */       if (((Boolean)this.tracers.get()).booleanValue()) {
/* 125 */         double camX = RenderUtils.center.field_1352;
/* 126 */         double camY = RenderUtils.center.field_1351;
/* 127 */         double camZ = RenderUtils.center.field_1350;
/*     */         
/* 129 */         event.renderer.line(camX, camY, camZ, tX + 0.5D, tY + 0.5D, tZ + 0.5D, (Color)this.tracerColor.get());
/*     */       } 
/*     */ 
/*     */       
/* 133 */       if (((Boolean)this.pillarAlert.get()).booleanValue()) {
/* 134 */         double heightToRender = 300.0D - tY;
/* 135 */         event.renderer.box(tX, tY, tZ, tX + 1.0D, tY + heightToRender, tZ + 1.0D, (Color)this.pillarColor.get(), (Color)this.pillarColor.get(), ShapeMode.Both, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DetectedSpawner { public class_2338 pos;
/*     */     public String entityName;
/*     */     
/*     */     public DetectedSpawner(class_2338 pos, String entityName) {
/* 144 */       this.pos = pos;
/* 145 */       this.entityName = entityName;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\modules\SpawnerNotifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */