package forum.listeners;

import forum.commands.CommandManager;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {
    public void onMessageReactionAdd(MessageReactionAddEvent e){
        if(e.getUser().isBot()){
            return;
        }
        CommandManager.runReactionCommand(e);
        System.out.println(e.getUser().getAsTag()+" "+e.getReactionEmote().getName());

    }
}
