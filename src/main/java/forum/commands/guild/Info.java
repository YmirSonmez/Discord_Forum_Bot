package forum.commands.guild;

import forum.database.DataBase;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.DateUtil;
import forum.utils.ThreadContext;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.Clock;
import java.time.OffsetDateTime;

public class Info implements ICommand {
    @Override
    public String getName() {
        return "ForumBilgi";
    }

    @Override
    public String getDescription() {
        return "Forum bilgilerini görüntülemek için kullanılır.";
    }

    @Override
    public String getUsage() {
        return Setting.PREFIX()+getName();
    }

    @Override
    public CommandType type() {
        return CommandType.FORUM_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String messageWithNoCommand) {
        int confirmedThreads=Setting.CATEGORY().getTextChannels().size();
        int waitThreads =ThreadUtils.threads.size();
        int totalThreads = waitThreads+confirmedThreads;
        String date =Setting.CATEGORY().getTimeCreated().format(DateUtil.DTF);


        EmbedBuilder info = new EmbedBuilder()
                .setTitle(Setting.GUILD().getName())
                .setDescription("**Kurulma Tarihi: **```" + date
                        +"```\n**Üye Sayısı: **```" + e.getGuild().getMembersWithRoles(Setting.FORUM_MEMBER_ROLE()).size()
                        +"```\n**Banlanmış Üye Sayısı: **```"+Setting.GUILD().getMembersWithRoles(Setting.FORUM_BANNED_ROLE()).size()
                        +"```\n**Kapanmış Konu Sayısı: **```" + DataBase.getTotalThread()
                        +"```\n**Aktif Konu Sayısı: **```" + totalThreads
                        +"```\n**Onaylanmış Konu Sayısı: **```"+confirmedThreads
                        +"```\n**Bekleyen Konu Sayısı: **```"+waitThreads+"```")
                .setThumbnail(e.getGuild().getIconUrl())
                .setFooter(e.getAuthor().getName()+" tarafından istendi.",e.getAuthor().getAvatarUrl())
                .setTimestamp(OffsetDateTime.now(Clock.systemUTC()))
                .setColor(0xFFBD00);

        e.getChannel().sendMessage(info.build()).queue();
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context, String messageWithNoCommand) {

    }
}
