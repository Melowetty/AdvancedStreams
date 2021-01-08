package com.melowetty.advancedstreams.Command;

import com.melowetty.advancedstreams.*;
import com.melowetty.advancedstreams.Managers.StreamsManager;
import com.melowetty.advancedstreams.Utils.Helper;
import com.melowetty.advancedstreams.Utils.ItemHelper;
import com.melowetty.advancedstreams.Utils.URLHelper;
import org.bukkit.Material;
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
                    ResponseStatus status = streamsManager.addStream(player, URLHelper.getYouTubeVideoID(args[1]), StreamPlatform.YOUTUBE);
                    switch(status) {
                        case SUCCESS:
                            player.sendMessage(Helper.colored("&6Вы успешно добавили &c&lстрим &6в табло."));
                            break;
                        case NULL:
                            player.sendMessage(Helper.colored("&6[SCY] &Видео не найдено."));
                            break;
                        case OVERFLOW:
                            player.sendMessage(Helper.colored("&cВ табло максимальное количество прямых трансляций"));
                            break;
                        default:
                            player.sendMessage("&6[SCY] &4Произошла неопределнная ошибка.");
                    }
                }
                if(args[0].equalsIgnoreCase("delete")) {
                    streamsManager.deleteStream(args[1]);
                }
                if(args[0].equalsIgnoreCase("list")) {
                    player.openInventory(AdvancedStreams.getInstance().getMenu());
                }
            }
        }
        return false;
    }
}
