package forum.commands.thread;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.modules.debug.Error;
import forum.modules.debug.ErrorCodes;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import forum.embed.EmbedCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DeleteContent implements ICommand {

    @Override
    public String getName() {
        return "Temizle";
    }

    @Override
    public String getDescription() {
        return "Konu içerisinde yazdığınız mesajları silmek için kullanılır.";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName()+" <miktar>");
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
        PrivateChannel channel = e.getPrivateChannel();
        if(context.getWrite()){
            String[] args = messageWithNoCommand.split(" ");
            if(args.length<2){
                context.clearMessages();
                channel.sendMessage(":white_check_mark: İçeriğiniz tamamen temizlendi!").queue();
            }else {
                try {
                    int number = Integer.parseInt(args[0]);
                    if(context.getMessages().size()<number){
                        context.clearMessages();
                        channel.sendMessage(":white_check_mark: İçeriğiniz tamamen temizlendi!").queue();
                    }else{
                        for (int i = 1; i <= number; i++) {
                            context.getMessages().remove((context.getMessages().size() - 1));
                        }
                    }
                }catch (NumberFormatException exception){
                    new Error(channel, ErrorCodes.NUMBER_ERROR).set("Miktar yalnızca rakamlardan oluşabilir.").send();
                }
            }
            EmbedBuilder content = EmbedCreator.createForumEmbed(context.getUser());
            channel.sendMessage(content.build()).queue();
            content.clear();
        }else{
            channel
                    .sendMessage(":warning: Gönderilen bir konu üzerinde değişiklik yapamazsınız!")
                    .queue();
        }
    }
}
