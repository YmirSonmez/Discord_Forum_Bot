package forum.commands.admin;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.Emoji;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AThreadCategory implements ICommand {
    @Override
    public String getName() {
        return "Kategoriler";
    }

    @Override
    public String getDescription() {
        return "Kategori menüsü oluşturur.";
    }

    @Override
    public String getUsage() {
        return Setting.PREFIX()+getName();
    }

    @Override
    public CommandType type() {
        return CommandType.ADMIN_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String messageWithNoCommand) {
        TextChannel channel = Setting.MAIN_CHANNEL();
        EmbedBuilder createEmbed = new EmbedBuilder()
                .setColor(0x00FFF0)
                .setTitle("Kategori")
                .setDescription(Emoji.MINECRAFT + " : Minecraft \n" + Emoji.GENERAL + " : Genel\n" + Emoji.SERVER + " : Sunucu Hakkında" )
                .addField("Üyelik","・Üye olmak için: "+Emoji.MEMBER,false)
                .addField(":warning: Dikkat :warning:", "・Özelden mesaj almanız kapalı ise botu kullanamazsınız!\n・Emojiye tıkladıktan ve bot mesaj attıkan sonra yazacağınız ilk mesaj başlık olur, dilerseniz daha sonra değiştirebilirsiniz.\n・Üye olduktan sonra bütün kuralları okumuş ve kabul etmiş sayılırsınız!",false );

        channel.sendMessage(createEmbed.build()).queue(message -> {
            message.addReaction(Emoji.MINECRAFT).queue();
            message.addReaction(Emoji.GENERAL).queue();
            message.addReaction(Emoji.SERVER).queue();
            message.addReaction(Emoji.MEMBER).queue();
        });
        createEmbed.clear();
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context, String messageWithNoCommand) {

    }
}
