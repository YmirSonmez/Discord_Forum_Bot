package forum.commands.reaction;
import forum.commands.IReactionCommand;
import forum.commands.ReactionCommandType;
import forum.embed.EmbedCreator;
import forum.utils.Emoji;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Arrays;
import java.util.List;

public class RThreadPreview implements IReactionCommand {
    @Override
    public List<String> getName() {
        return (Arrays.asList(Emoji.NEXT, Emoji.PREVIOUS));
    }

    @Override
    public ReactionCommandType getType() {
        return ReactionCommandType.THREAD_PREVIEW_PAGE;
    }

    @Override
    public void onCommand(MessageReactionAddEvent e) {
        EmbedBuilder newPage;
        if(e.getReactionEmote().getName().equalsIgnoreCase(Emoji.NEXT)){
            newPage= EmbedCreator.changeEmbed(EmbedCreator.getEmbedPages(e.getMessageId()),true);
        }else if(e.getReactionEmote().getName().equalsIgnoreCase(Emoji.PREVIOUS)){
            newPage= EmbedCreator.changeEmbed(EmbedCreator.getEmbedPages(e.getMessageId()),false);
        }else{
            return;
        }
        if(newPage==null){
            return;
        }
        e.retrieveMessage().queue(message -> message.editMessage(newPage.build()).queue());
        if(e.getChannelType().equals(ChannelType.TEXT)){
            e.getReaction().removeReaction(e.getUser()).queue();
        }
    }
}
