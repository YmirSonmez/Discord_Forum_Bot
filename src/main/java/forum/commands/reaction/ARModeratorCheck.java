package forum.commands.reaction;

import forum.ForumManager;
import forum.commands.IReactionCommand;
import forum.commands.ReactionCommandType;
import forum.utils.ThreadContext;
import forum.setting.Setting;
import forum.embed.EmbedCreator;
import forum.utils.Emoji;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ARModeratorCheck implements IReactionCommand {
    @Override
    public List<String> getName() {
        return Arrays.asList(Emoji.CONFIRM,Emoji.REJECT,Emoji.BAN);
    }

    @Override
    public ReactionCommandType getType() {
        return ReactionCommandType.THREAD_CONFIRM;
    }

    @Override
    public void onCommand(MessageReactionAddEvent e) {
        if(!ThreadUtils.sentThreads.containsKey(e.getMessageId())){
            e.getChannel().sendMessage("Bu konu bot sıfırladığı için silinmiştir! Konu sahibine ulaşarak yeniden oluşturmasını isteyebilirsiniz!").queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            e.retrieveMessage().queue(message -> message.delete().queue());
            return;
        }
        User author = ThreadUtils.sentThreads.get(e.getMessageId());
        ThreadContext context = ThreadUtils.threads.get(author);
        switch (e.getReactionEmote().getName()){
            case Emoji.CONFIRM:
                ForumManager.confirmThread(Setting.CONFIRM_CHANNEL(),context,e.getMessageId(),e.getUser());
                break;
            case Emoji.REJECT:
                ForumManager.refuseThread(e.getTextChannel(),e.getMessageId(),author);
                break;
            case Emoji.BAN:
                ForumManager.banThread(e.getGuild(),e.getTextChannel(),e.getMessageId(),author,Setting.FORUM_BANNED_ROLE());
                break;
        }
        EmbedCreator.removeMultiEmbed(e.getMessageId());
    }
}
