package forum.commands.reaction;

import forum.commands.IReactionCommand;
import forum.commands.ReactionCommandType;
import forum.utils.Emoji;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import java.util.Arrays;
import java.util.List;


public class RPoint implements IReactionCommand {
    @Override
    public List<String> getName() {
        return Arrays.asList(Emoji.POSITIVE,Emoji.NEGATIVE);
    }

    @Override
    public ReactionCommandType getType() {
        return ReactionCommandType.POINT;
    }

    @Override
    public void onCommand(MessageReactionAddEvent e) {
        e.retrieveMessage().queue(message -> {
            for(MessageReaction reaction:message.getReactions()){
                if(!reaction.getReactionEmote().equals(e.getReaction().getReactionEmote())&&getName().contains(e.getReactionEmote().getName())){
                    reaction.retrieveUsers().queue(users -> {
                        if(users.contains(e.getUser())){
                            e.getReaction().removeReaction(e.getUser()).queue();
                        }
                    });
                }
            }
        });
    }
}
