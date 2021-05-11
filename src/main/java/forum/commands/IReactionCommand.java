package forum.commands;

import forum.commands.ReactionCommandType;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.List;

public interface IReactionCommand {
    List<String> getName();

    ReactionCommandType getType();

    void onCommand(MessageReactionAddEvent e);

}

