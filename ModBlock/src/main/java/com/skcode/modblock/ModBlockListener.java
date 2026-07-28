package com.skcode.modblock;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

import java.util.List;
import java.util.Locale;

/**
 * Ловит клиентские моды по "плагинным каналам", которые они регистрируют
 * при заходе на сервер (так делает большинство миникарт/x-ray/фрикам/макро модов).
 * НЕ ловит моды, которые вообще не используют сетевые каналы - это не
 * стопроцентная защита, а дополнительный слой поверх обычного античита.
 */
public class ModBlockListener implements Listener {

    private final ModBlock plugin;

    public ModBlockListener(ModBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChannelRegister(PlayerRegisterChannelEvent event) {
        Player player = event.getPlayer();
        String channel = event.getChannel().toLowerCase(Locale.ROOT);

        if (plugin.getConfig().getBoolean("log-all-channels", false)) {
            plugin.getLogger().info("[канал] " + player.getName() + " -> " + channel);
        }

        if (!plugin.getConfig().getBoolean("enabled", true)) return;
        if (player.hasPermission("skcode.bypass")) return;

        List<String> blocked = plugin.getConfig().getStringList("blocked-channels");

        for (String bad : blocked) {
            if (bad == null || bad.isBlank()) continue;
            if (channel.contains(bad.toLowerCase(Locale.ROOT))) {
                handleDetection(player, bad, channel);
                return;
            }
        }
    }

    private void handleDetection(Player player, String matchedKeyword, String channel) {
        String action = plugin.getConfig().getString("action", "kick");

        if (action.equalsIgnoreCase("kick")) {
            player.kick(Component.text("Обнаружен запрещённый мод (" + matchedKeyword + ")")
                    .color(NamedTextColor.RED));
        }

        Component alert = Component.text("⚠ ").color(NamedTextColor.GOLD)
                .append(Component.text(player.getName()).color(NamedTextColor.RED))
                .append(Component.text(" использует запрещённый мод: ").color(NamedTextColor.GRAY))
                .append(Component.text(matchedKeyword).color(NamedTextColor.YELLOW))
                .append(Component.text(" (канал: " + channel + ")").color(NamedTextColor.DARK_GRAY));

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("skcode.alerts")) {
                staff.sendMessage(alert);
            }
        }
        Bukkit.getConsoleSender().sendMessage(alert);
    }
}
