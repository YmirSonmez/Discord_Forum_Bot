package forum;

import forum.gui.ForumMenu;
import forum.listeners.MessageListener;
import forum.listeners.ReactionListener;
import forum.listeners.GenericListener;

import forum.setting.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;


public class Main {
    public static final String path = "Forum\\";

    public static void Bot(String token) throws LoginException{
        JDABuilder jda = JDABuilder.createDefault(token,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.DIRECT_MESSAGE_TYPING,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS
        ).setMemberCachePolicy(MemberCachePolicy.ALL);
        jda.addEventListeners(new MessageListener());
        jda.addEventListeners(new ReactionListener());
        jda.addEventListeners(new GenericListener());
        jda.build();
    }


    public static void main(String[] args){
        Config.checkFile();
        new ForumMenu();
    }
}
