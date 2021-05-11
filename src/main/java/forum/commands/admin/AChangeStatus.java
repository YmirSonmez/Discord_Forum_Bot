package forum.commands.admin;

import forum.utils.ThreadContext;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

import static forum.utils.BotUtils.setStatus;

public class AChangeStatus implements ICommand {
    @Override
    public String getName() {
        return "Durum";
    }

    @Override
    public String getDescription() {
        return "Bot durumunu değiştirmek için kullanılır!";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName()+" [Durum-mesajı]");
    }

    @Override
    public CommandType type() {
        return CommandType.ADMIN_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        if(args.length==1){
            if(Objects.equals(e.getJDA().getPresence().getActivity(), Activity.playing("Kullanılamaz!"))){
                setStatus(e.getJDA(), OnlineStatus.ONLINE,"İyi forumlar!");
            }else{
                setStatus(e.getJDA(), OnlineStatus.DO_NOT_DISTURB,"Kullanılamaz!");
            }
        }else setStatus(e.getJDA(),
                OnlineStatus.IDLE,
                messageWithNoCommand.trim());
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand) {

    }
}
