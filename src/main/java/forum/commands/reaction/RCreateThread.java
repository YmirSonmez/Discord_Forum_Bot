package forum.commands.reaction;

import forum.ForumManager;
import forum.commands.IReactionCommand;
import forum.commands.ReactionCommandType;
import forum.utils.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import java.util.Arrays;
import java.util.List;

public class RCreateThread implements IReactionCommand {
    @Override
    public List<String> getName() {
        return Arrays.asList(Emoji.GENERAL,Emoji.MINECRAFT,Emoji.SERVER);
    }

    @Override
    public ReactionCommandType getType() {
        return ReactionCommandType.THREAD_MAIN;
    }

    @Override
    public void onCommand(MessageReactionAddEvent e) {
        String threadCategory;
        switch (e.getReactionEmote().getName()){
            case Emoji.GENERAL:
                threadCategory="Konu Dışı";
                break;
            case Emoji.MINECRAFT:
                threadCategory="Minecraft";
                break;
            case Emoji.SERVER:
                threadCategory="Sunucu Hakkında";
                break;
            default:
                threadCategory="Null";
                break;
        }
        e.getReaction().removeReaction(e.getUser()).queue();
        ForumManager.create(e.getUser(),threadCategory);
    }
}
