/*    */ package com.example.addon.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ 
/*    */ public class CommandExample
/*    */   extends Command
/*    */ {
/*    */   public CommandExample() {
/* 16 */     super("example", "Sends a message.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 21 */     builder.executes(context -> {
/*    */           info("hi", new Object[0]);
/*    */           
/*    */           return 1;
/*    */         });
/* 26 */     builder.then(literal("name").then(argument("nameArgument", (ArgumentType)StringArgumentType.word()).executes(context -> {
/*    */               String argument = StringArgumentType.getString(context, "nameArgument");
/*    */               info("hi, " + argument, new Object[0]);
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\fadim\Downloads\wizzyaddon-0.1.0.jar!\com\example\addon\commands\CommandExample.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */