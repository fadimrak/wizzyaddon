/*     */ package com.example.addon.modules;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MapDataManager
/*     */ {
/* 115 */   private final Map<Integer, RegionMap.RegionInfo> regionMap = new HashMap<>();
/* 116 */   private final String[] regionTypeNames = new String[] { "EU Central", "EU West", "NA East", "NA West", "Asia", "Oceania" };
/* 117 */   private final Color[] regionTypeColors = new Color[] { new Color(159, 206, 99), new Color(0, 166, 99), new Color(79, 173, 234), new Color(47, 110, 186), new Color(245, 194, 66), new Color(252, 136, 3) };
/*     */   public MapDataManager() {
/* 119 */     int[][] layout = { { 82, 5 }, { 100, 3 }, { 101, 3 }, { 102, 3 }, { 103, 2 }, { 104, 2 }, { 105, 2 }, { 106, 2 }, { 91, 2 }, { 83, 5 }, { 44, 3 }, { 75, 3 }, { 42, 3 }, { 41, 2 }, { 40, 2 }, { 39, 2 }, { 38, 2 }, { 92, 2 }, { 84, 5 }, { 45, 3 }, { 14, 3 }, { 13, 3 }, { 12, 2 }, { 11, 2 }, { 10, 2 }, { 37, 2 }, { 93, 2 }, { 85, 5 }, { 46, 5 }, { 74, 5 }, { 3, 3 }, { 2, 2 }, { 1, 2 }, { 25, 2 }, { 36, 2 }, { 94, 2 }, { 86, 4 }, { 47, 4 }, { 72, 4 }, { 71, 4 }, { 5, 2 }, { 4, 2 }, { 24, 2 }, { 35, 2 }, { 95, 2 }, { 87, 4 }, { 51, 1 }, { 17, 1 }, { 9, 0 }, { 8, 0 }, { 7, 0 }, { 23, 0 }, { 34, 0 }, { 96, 2 }, { 88, 4 }, { 54, 1 }, { 18, 1 }, { 61, 0 }, { 62, 0 }, { 21, 0 }, { 22, 0 }, { 33, 0 }, { 97, 0 }, { 89, 0 }, { 26, 1 }, { 27, 0 }, { 28, 0 }, { 29, 0 }, { 30, 0 }, { 59, 0 }, { 32, 0 }, { 98, 0 }, { 90, 0 }, { 107, 1 }, { 108, 1 }, { 109, 1 }, { 110, 1 }, { 111, 1 }, { 112, 1 }, { 113, 1 }, { 99, 0 } };
/* 120 */     for (int i = 0; i < layout.length; ) { this.regionMap.put(Integer.valueOf(i), new RegionMap.RegionInfo(layout[i][0], layout[i][1], i / 9, i % 9)); i++; }
/*     */   
/*     */   } public int getRegionAt(double x, double z) {
/* 123 */     int gx = (int)((x + 225000.0D) / 50000.0D), gz = (int)((z + 225000.0D) / 50000.0D);
/* 124 */     if (gx >= 0 && gx < 9 && gz >= 0 && gz < 9) {
/* 125 */       RegionMap.RegionInfo info = this.regionMap.get(Integer.valueOf(gz * 9 + gx));
/* 126 */       return (info != null) ? info.regionId : -1;
/*     */     } 
/* 128 */     return -1;
/*     */   }
/* 130 */   public Color getRegionColor(int t) { return (t >= 0 && t < this.regionTypeColors.length) ? this.regionTypeColors[t] : Color.WHITE; }
/* 131 */   public String[] getRegionTypeNames() { return this.regionTypeNames; }
/* 132 */   public Color[] getRegionTypeColors() { return this.regionTypeColors; } public RegionMap.RegionInfo getRegionInfo(int i) {
/* 133 */     return this.regionMap.get(Integer.valueOf(i));
/*     */   }
/*     */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\modules\RegionMap$MapDataManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */