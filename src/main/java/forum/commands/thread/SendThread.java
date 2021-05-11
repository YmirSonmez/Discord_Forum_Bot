package forum.commands.thread;

import forum.embed.EmbedPages;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import forum.embed.EmbedCreator;
import forum.utils.Emoji;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SendThread implements ICommand {
    @Override
    public String getName() {
        return "Gönder";
    }

    @Override
    public String getDescription() {
        return "Konuyu bitirmek için kullanılır!";
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
        PrivateChannel channel = e.getPrivateChannel();
        if(context.getWrite()){
            if(context.getTitle().equals("Konu")){
                channel.sendMessage("Konu başlığı belirlemediniz!").queue();
            }else{
                if(context.getMessages().isEmpty()){
                    channel.sendMessage("Boş mesaj gönderemezsiniz!").queue();
                }else{
                    TextChannel guildChannel = Setting.CONFIRM_CHANNEL();

                    EmbedBuilder send = EmbedCreator.createForumEmbed(context.getUser());

                    guildChannel.sendMessage(send.build()).queue(message1 ->{

                        message1.addReaction(Emoji.CONFIRM).queue();
                        message1.addReaction(Emoji.REJECT).queue();
                        message1.addReaction(Emoji.BAN).queue();
                        message1.addReaction(Emoji.PREVIOUS).queue();
                        message1.addReaction(Emoji.NEXT).queue();
                        ThreadUtils.addWait(message1.getId(), context.getUser());
                        EmbedCreator.addMultiEmbed(message1.getId(),new EmbedPages(context,1));
                    });
                    channel
                            .sendMessage(":white_check_mark: Konunuz başarıyla İletildi!")
                            .queue();
                    send.clear();
                    EmbedBuilder content = EmbedCreator.createForumEmbed(context.getUser());
                    channel.sendMessage(content.build()).queue();
                    content.clear();
                }
            }

        }else{
            channel.sendMessage("Gönderilmiş konuyu tekrar gönderemezsinz!").queue();
        }
    }
}
