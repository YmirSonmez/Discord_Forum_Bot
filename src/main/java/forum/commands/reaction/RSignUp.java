package forum.commands.reaction;

import forum.commands.IReactionCommand;
import forum.commands.ReactionCommandType;
import forum.setting.Setting;
import forum.utils.Emoji;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Collections;
import java.util.List;

public class RSignUp implements IReactionCommand {
    @Override
    public List<String> getName() {
        return Collections.singletonList(Emoji.MEMBER);
    }

    @Override
    public ReactionCommandType getType() {
        return ReactionCommandType.THREAD_MAIN;
    }

    @Override
    public void onCommand(MessageReactionAddEvent e) {
        e.getReaction().removeReaction(e.getUser()).queue();
        Role memberRole = Setting.FORUM_MEMBER_ROLE();
        if(!e.getMember().getRoles().contains(memberRole)){
            e.getGuild().addRoleToMember(e.getMember(),memberRole).queue();
        }
    }
}
