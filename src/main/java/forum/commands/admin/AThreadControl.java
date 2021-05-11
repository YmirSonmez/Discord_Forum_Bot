package forum.commands.admin;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.modules.debug.Error;
import forum.modules.debug.ErrorCodes;
import forum.utils.ThreadContext;
import forum.setting.Setting;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class AThreadControl implements ICommand {
    @Override
    public String getName() {
        return "Değiştir";
    }

    @Override
    public String getDescription() {
        return "Konu başlığı ve kategorisi değiştirmek için kullanılır.";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName()+" <tür> <id> <yeni-içerik>");
    }

    @Override
    public CommandType type() {
        return CommandType.ADMIN_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand){
        String[] args = messageWithNoCommand.split(" ");
        if(args.length>=3){
            User writer;
            try{
                writer = e.getJDA().getUserById(args[1]);
                if(writer==null || !ThreadUtils.checkContext(writer)){
                    new Error(e.getChannel(), ErrorCodes.CHECK_ERROR).set("Bu ID ile eşleşen herhangi bir konu bulunamadı!").send();
                }else{
                    String change = messageWithNoCommand.substring((args[0].length()+args[1].length())+1).trim();
                    switch (args[0]){
                        case "kategori":
                            ThreadUtils.changeCategory(change,writer);
                            break;
                        case "başlık":
                            if(change.length()<4|| change.length()>15){
                                e.getChannel().sendMessage(":warning: Başlık 15 karakterden büyük, 4 karakterden küçük olamaz! Başlık uzunluğunuz: " + change.length()).queue();
                                return;
                            }
                            ThreadUtils.changeTitle(change,writer);
                            break;
                    }
                }
            }catch (NumberFormatException error){
                new Error(e.getChannel(), ErrorCodes.NUMBER_ERROR).set("ID yalnızca rakamlardan oluşur!").send();
            }
        }else{
            new Error(e.getChannel(), ErrorCodes.USAGE_ERROR).set(getUsage()).send();
        }

    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand) {

    }
}
