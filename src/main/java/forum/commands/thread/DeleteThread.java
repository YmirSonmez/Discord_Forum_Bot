package forum.commands.thread;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DeleteThread implements ICommand {
    @Override
    public String getName() {
        return "Sil";
    }

    @Override
    public String getDescription() {
        return "Yazmakta olduğunuz konuyu silmek için kullanılır.";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName());
    }

    @Override
    public CommandType type() {
        return CommandType.THREAD_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand) {
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand) {
        if(ThreadUtils.checkWrite(context.getUser())){
            ThreadUtils.threads.keySet().remove(context.getUser());
            e.getChannel().sendMessage("Konunuz başarıyla silin!").queue();
        }else{
            e.getChannel().sendMessage("Gönderilen konu üzerinde değişiklik yapamazsın!").queue();
        }
    }
}
