package forum.commands.thread;

import forum.embed.EmbedPages;
import forum.commands.CommandManager;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.utils.ThreadContext;
import forum.embed.EmbedCreator;
import forum.utils.Emoji;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShowThread implements ICommand {
    @Override
    public String getName() {
        return "Görüntüle";
    }

    @Override
    public String getDescription() {
        return "Konunuzu görüntülemek için kullanılır!";
    }

    @Override
    public String getUsage() {
        return (CommandManager.getPrefix+getName());
    }

    @Override
    public CommandType type() {
        return CommandType.THREAD_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand){}

    @Override
    public void onCommand(MessageReceivedEvent e,ThreadContext context,String messageWithNoCommand) {
        EmbedBuilder show = EmbedCreator.createForumEmbed(e.getAuthor());
        EmbedPages page = new EmbedPages(context,1);
        e.getChannel().sendMessage(show.build()).queue(message -> {
            message.addReaction(Emoji.PREVIOUS).queue();
            message.addReaction(Emoji.NEXT).queue();
            EmbedCreator.addMultiEmbed(message.getId(),page);
        });
        show.clear();
    }
}
