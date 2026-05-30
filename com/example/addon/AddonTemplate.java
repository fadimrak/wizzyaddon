/*    */ package com.example.addon;
/*    */ 
/*    */ import com.example.addon.modules.AutoRelog;
/*    */ import com.example.addon.modules.RegionMap;
/*    */ import com.example.addon.modules.SafeAnchorMacro;
/*    */ import com.example.addon.modules.SpawnerNotifier;
/*    */ import com.example.addon.modules.SusBypass;
/*    */ import com.example.addon.modules.WizzyChunkV2;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import meteordevelopment.meteorclient.addons.MeteorAddon;
/*    */ import meteordevelopment.meteorclient.systems.modules.Category;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class AddonTemplate extends MeteorAddon {
/* 17 */   public static final Logger LOG = LogUtils.getLogger();
/* 18 */   public static final Category BASE_HUNTING = new Category("Base Hunting");
/* 19 */   public static final Category PVP = new Category("PvP");
/* 20 */   public static final Category MISC = new Category("Misc");
/*    */   public static boolean isLicensed = true;
/*    */   
/*    */   public void onInitialize() {
/* 24 */     LOG.info("================================================");
/* 25 */     LOG.info("  Wizzy Addon - cracked");
/* 26 */     LOG.info("  all modules activated");
/* 27 */     LOG.info("================================================");
/* 28 */     Modules.get().add((Module)new RegionMap());
/* 29 */     Modules.get().add((Module)new SpawnerNotifier());
/* 30 */     Modules.get().add((Module)new WizzyChunkV2());
/* 31 */     Modules.get().add((Module)new SafeAnchorMacro());
/* 32 */     Modules.get().add((Module)new AutoRelog());
/* 33 */     Modules.get().add((Module)new SusBypass());
/*    */   }
/*    */   
/*    */   public void onRegisterCategories() {
/* 37 */     Modules.registerCategory(BASE_HUNTING);
/* 38 */     Modules.registerCategory(PVP);
/* 39 */     Modules.registerCategory(MISC);
/*    */   }
/*    */   
/*    */   public String getPackage() {
/* 43 */     return "com.example.addon";
/*    */   }
/*    */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\AddonTemplate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */