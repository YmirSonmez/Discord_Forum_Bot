package forum.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class BotUtils {
    public static void setStatus(JDA jda, OnlineStatus onlineStatus, String playing){
        jda.getPresence().setPresence(onlineStatus,Activity.playing(playing));
    }
}
