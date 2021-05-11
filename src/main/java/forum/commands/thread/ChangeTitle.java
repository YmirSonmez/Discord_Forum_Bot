package forum.commands.thread;

import forum.commands.CommandManager;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.utils.ThreadContext;
import forum.embed.EmbedCreator;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ChangeTitle implements ICommand {
    @Override
    public String getName() {
        return "Başlık";
    }

    @Override
    public String getDescription() {
        return "Başlık değiştirmek için kullanılır!";
    }

    @Override
    public String getUsage() {
        return (CommandManager.getPrefix+getName()+" <Başlık>");
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
        if(context.getWrite()){
            String tryDoThis = messageWithNoCommand.trim();
            if(tryDoThis.length() > 15 || tryDoThis.length() < 4){
                e.getChannel().sendMessage(":warning: Başlık 15 karakterden büyük, 4 karakterden küçük olamaz! Başlık uzunluğunuz: " + tryDoThis.length()).queue();
            }else{
                ThreadUtils.changeTitle(tryDoThis, context.getUser());
                EmbedBuilder content = EmbedCreator.createForumEmbed(context.getUser());
                e.getChannel().sendMessage(content.build()).queue();
                content.clear();
            }
        }else {
            e.getChannel().sendMessage(":warning: Gönderilen konular üzerinde değişiklik yapamazsın!").queue();
        }
    }
}
