package forum.commands.reaction;

import forum.database.DataBase;
import forum.database.ForumMember;
import forum.commands.IReactionCommand;
import forum.commands.ReactionCommandType;
import forum.setting.Setting;
import forum.utils.Emoji;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ARThreadControl implements IReactionCommand {
    @Override
    public List<String> getName() {
        return Collections.singletonList(Emoji.LOCK);
    }

    @Override
    public ReactionCommandType getType() {
        return ReactionCommandType.THREAD_CONTROL;
    }

    @Override
    public void onCommand(MessageReactionAddEvent e) {
        e.retrieveMessage().queue(message ->{
            int xp;
            int positive = 0;
            int negative = 0;
            for(MessageReaction reaction:message.getReactions()) {
                switch (reaction.getReactionEmote().getName()) {
                    case Emoji.POSITIVE:
                        positive = reaction.getCount();
                        break;
                    case Emoji.NEGATIVE:
                        negative = reaction.getCount();
                        break;
                }
            }
            xp= (((positive-1)*3)-(negative - 1));
            try{
                User user = message.getMentionedUsers().get(0);
                Objects.requireNonNull(e.getGuild().getMember(user));
                ForumMember member = DataBase.getMember(user.getId());
                member.addXp(xp).addThread(1).complete();
            }catch (NullPointerException | IndexOutOfBoundsException exception){
                Setting.CONFIRM_CHANNEL().sendMessage(e.getTextChannel().getName()+" kanalı silinirken bir sorun oluştu! **"+ message.getContentDisplay()
                        +"** sunucuda bulunmadığı için puan eklenemedi!"
                        +"\nAlması gereken puan: "+xp
                        +"\nPozitif oy sayısı: "+positive
                        +"\nNegatif oy sayısı: "+negative).queue();
            }
            e.getTextChannel().delete().queue();
        });

    }
}
