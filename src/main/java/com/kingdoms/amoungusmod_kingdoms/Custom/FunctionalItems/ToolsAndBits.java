package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ToolsAndBits {



        private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);

        public static void broadcastTypewriterActionbar(MinecraftServer server, String fullText, Formatting color) {
            final AtomicInteger index = new AtomicInteger(1);

            SCHEDULER.scheduleAtFixedRate(() -> {
               int CL=index.getAndIncrement();
               if(CL>fullText.length()){
                   throw new RuntimeException("Typing Completed");
               }
                String partialText = fullText.substring(0, CL);
                Text formattedMessage = Text.literal(partialText).formatted(color, Formatting.BOLD);
                server.execute(() -> {
                    server.getPlayerManager().broadcast(formattedMessage, true);
                });
            },0,100,TimeUnit.MILLISECONDS);
        }

}
