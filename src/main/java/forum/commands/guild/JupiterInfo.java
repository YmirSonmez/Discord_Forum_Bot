package forum.commands.guild;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class JupiterInfo implements ICommand {
    @Override
    public String getName() {
        return "Bilgi";
    }

    @Override
    public String getDescription() {
        return "JupiterMCPE bilgilerine ulaşmanızı sağlar.";
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
        String[] randomMessages = {"Jupiter 8 Nisan 2021 tarihinde oyunculara açılmıştır!","Oy takas sistemine göz atmayı unutma!","Savaşmayın Jupiter oynayın!","Bugün harikasın!","Sanırım oy vermek için geldin.",
        "Sanırım bir savaşçı geçti buradan!","Güç Jupiter oynayanların yanındadır!","Oy vermek erdemdir!","Ben mi? Sadece basit bir botum :(","Hey! Bugün çok güçlü görünüyorsun!",
        "Yükleme yaptın mı?","Yükleme yapmaya ne gerek var! Jupiter'e oy vererek 1. olabilirsin!","Bir sorunun olursa bizimle iletişime geçmeyi unutma dostum!!"};


        Random random = new Random();
        String description= "**İletişim**\n"
                +"\n<:jp_youtube:828272896349569034>**»** [JupiterMCPE Network](https://www.youtube.com/channel/UC8rX4nQwjROwxOZXcIubDfg)"
                +"\n<:jp_instagram:828272838611697675>**»** [@JupiterMCPE](https://www.instagram.com/jupitermcpe/)"
                +"\n<:jp_telegram:831826100689961000>**»** [JupiterMCPE](https://t.me/JPMCPE)"
                +"\n<:jp_facebook:828272966684377138>**»** [JupiterMCPE Network](https://www.facebook.com/groups/859470361568460)"
                +"\n\n"
                +"**Sunucu**\n"
                +"\n**IP »** `oyna.jupitermcpe.xyz`"
                +"\n**Sürüm »** `1.16.20 & 1.16.40`";


        try {
            Document doc = Jsoup.connect("https://minecraftpocket-servers.com/server/107211/").timeout(6000).get();
            Elements table = doc.select("table.table-bordered tr td");

            description = description
                    +"\n**Aktif Sayısı »** `"+table.get(7).text()+"`"
                    +"\n\n"
                    +"**Oy**\n"
                    +"**Oy Sayısı »** `"+table.get(23).text()+"`"
                    +"\nHer ay en çok oy veren kişi vip kazanıyor! [Buraya tıklayarak](https://bit.ly/jupiteroy) hemen oy verebilirsiniz!";

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        int randomInt = random.nextInt(randomMessages.length);
        EmbedBuilder info = new EmbedBuilder()
                .setTitle(Setting.GUILD().getName())
                .setDescription(description)
                .setColor(new Color(random.nextFloat(),random.nextFloat(),random.nextFloat()))
                .setFooter(randomMessages[randomInt]);
        e.getChannel().sendMessage(info.build()).queue();




    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context, String messageWithNoCommand) {

    }
}
