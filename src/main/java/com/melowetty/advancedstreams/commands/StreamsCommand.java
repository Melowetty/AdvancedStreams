package com.melowetty.advancedstreams.commands;

import com.melowetty.advancedstreams.AdvancedStreams;
import com.melowetty.advancedstreams.ResponseStatus;
import com.melowetty.advancedstreams.StreamPlatform;
import com.melowetty.advancedstreams.managers.StreamsManager;
import com.melowetty.advancedstreams.utils.ChatHelper;
import com.melowetty.advancedstreams.utils.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StreamsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("stream")) {
            if(args.length > 0) {
                StreamsManager streamsManager = AdvancedStreams.getInstance().getStreamsManager();
                if(args[0].equalsIgnoreCase("youtube")) {
                    ResponseStatus status = streamsManager.addStream(player, Helper.getYouTubeVideoID(args[1]), StreamPlatform.YOUTUBE);
                    switch(status) {
                        case SUCCESS:
                            ChatHelper.sendMessage(player, "&aAdvancedStreams &8| &f Стрим был успешно добавлен!");
                            break;
                        case NULL:
                            player.sendMessage(Helper.colored("&6[SCY] &Видео не найдено."));
                            break;
                        case OVERFLOW:
                            player.sendMessage(Helper.colored("&cВ табло максимальное количество прямых трансляций"));
                            break;
                        default:
                            player.sendMessage(Helper.colored("&6[SCY] &4Произошла неопределнная ошибка."));
                    }
                }
                if(args[0].equalsIgnoreCase("vk")) {
                    String[] ids = Helper.getVKIds(args[1]);
                    ResponseStatus status = streamsManager.addStream(player, ids[0], ids[1], StreamPlatform.VK);
                    switch(status) {
                        case SUCCESS:
                            ChatHelper.sendMessage(player, "&aAdvancedStreams &8| &f Стрим был успешно добавлен!");
                            break;
                        case NULL:
                            player.sendMessage(Helper.colored("&6[SCY] &Видео не найдено."));
                            break;
                        case OVERFLOW:
                            player.sendMessage(Helper.colored("&cВ табло максимальное количество прямых трансляций"));
                            break;
                        default:
                            ChatHelper.sendMessage(player, "&6[SCY] &4Произошла неопределнная ошибка.");
                    }
                }
                if(args[0].equalsIgnoreCase("twitch")) {
                    ResponseStatus status = streamsManager.addStream(player, args[1], StreamPlatform.TWITCH);
                    switch(status) {
                        case SUCCESS:
                            ChatHelper.sendMessage(player, "&aAdvancedStreams &8| &f Стрим был успешно добавлен!");
                            break;
                        case NULL:
                            player.sendMessage(Helper.colored("&6[SCY] &Видео не найдено."));
                            break;
                        case OVERFLOW:
                            player.sendMessage(Helper.colored("&cВ табло максимальное количество прямых трансляций"));
                            break;
                        default:
                            player.sendMessage(Helper.colored("&6[SCY] &4Произошла неопределнная ошибка."));
                    }
                }
                if(args[0].equalsIgnoreCase("delete")) {
                    streamsManager.deleteStream(args[1]);
                }
                if(args[0].equalsIgnoreCase("list")) {
                    player.openInventory(AdvancedStreams.getInstance().getMenu());
                }
                else {
                    Helper.sendAvailableCommand(player);
                }
            }
            else {
                Helper.sendAvailableCommand(player);
            }
        }
        return false;
    }
}
