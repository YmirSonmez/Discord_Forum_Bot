package forum.commands.admin;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.modules.archive.Archive;
import forum.modules.debug.Error;
import forum.modules.debug.ErrorCodes;
import forum.setting.Setting;
import forum.utils.DateUtil;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Objects;



public class AArchive implements ICommand {
    @Override
    public String getName() {
        return "Arşivle";
    }

    @Override
    public String getDescription() {
        return "Konuyu arşivlemek için kullanılır.";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName()+" <kanal-id>");
    }

    @Override
    public CommandType type() {
        return CommandType.ADMIN_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand) {
        String[] args = messageWithNoCommand.trim().split(" ");
        if(args[0].length()<2){
            if(e.getChannel().getType().equals(ChannelType.TEXT)){
                new Archive(e.getTextChannel()).toArchiveChannel();
            }else{
                new Error(e.getChannel(),ErrorCodes.CHECK_ERROR).set("Yalnızca sunucu sohbet kanallarında kullanılabilir!").send();
            }
        }else{
            Guild guild = Setting.GUILD();
            try{
                TextChannel channel = Objects.requireNonNull(guild.getTextChannelById(args[0]));
                if(args.length==1){
                    new Archive(channel).toArchiveChannel();
                }else{
                    new Archive(channel).setTimer(DateUtil.getDate(args[1]));
                }
            }catch (NumberFormatException error){
                new Error(e.getChannel(), ErrorCodes.NUMBER_ERROR).set("ID yalnızca rakamlardan oluşur!").send();
            }
        }
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand) {

    }
}
