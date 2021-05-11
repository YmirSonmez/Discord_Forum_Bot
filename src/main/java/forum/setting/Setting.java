package forum.setting;

import forum.listeners.GenericListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class Setting {
    public static final JDA jda = GenericListener.jda;
    public static Guild GUILD(){return GenericListener.jda.getGuildById(Config.get("GUILDID"));}
    public static Category CATEGORY(){return GUILD().getCategoryById(Config.get("CATEGORYID"));}
    public static TextChannel MAIN_CHANNEL(){return GUILD().getTextChannelById(Config.get("MAINCHANNELID"));}
    public static TextChannel CONFIRM_CHANNEL(){return GUILD().getTextChannelById(Config.get("CONFIRMCHANNELID"));}
    public static TextChannel ARCHIVE_CHANNEL(){return GUILD().getTextChannelById(Config.get("ARCHIVECHANNELID"));}
    public static TextChannel BOT_CHANNEL(){return GUILD().getTextChannelById(Config.get("BOTCHANNELID"));}
    public static Role ADMIN_ROLE(){return GUILD().getRoleById(Config.get("ADMINROLEID"));}
    public static Role FORUM_MEMBER_ROLE(){return GUILD().getRoleById(Config.get("MEMBERROLEID"));}
    public static Role WRITE_BANNED_ROLE(){return GUILD().getRoleById(Config.get("WRITEBANNEDROLEID"));}
    public static Role FORUM_BANNED_ROLE(){return GUILD().getRoleById(Config.get("FORUMBANNEDROLEID"));}
    public static String PREFIX(){return Config.get("PREFIX");}
    public static String INVITE_LINK(){return Config.get("INVITELINK");}
    public static String TOKEN(){return Config.get("TOKEN");}

}
