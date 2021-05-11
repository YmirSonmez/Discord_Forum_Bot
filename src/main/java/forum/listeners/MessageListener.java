package forum.listeners;

import forum.commands.CommandManager;
import forum.setting.Setting;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getMessage().getType().equals(MessageType.CHANNEL_PINNED_ADD)){
            e.getMessage().delete().queue();
            return;
        }
        if(e.getAuthor().isBot()){
            return;
        }
        if(e.getMessage().getContentDisplay().startsWith(Setting.PREFIX())){
            CommandManager.runCommand(e);
            System.out.println(e.getMessage());
        }else{
            if(e.getChannelType().equals(ChannelType.PRIVATE) && ThreadUtils.checkContext(e.getAuthor())){
                if(ThreadUtils.checkWrite(e.getAuthor())){
                    ThreadUtils.addMessage(e.getAuthor(),e.getPrivateChannel(),e.getMessage());
                }
            }
        }
    }
}
