package forum.commands.admin;

import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.time.OffsetDateTime;

public class AMarket implements ICommand {
    @Override
    public String getName() {
        return "Market";
    }

    @Override
    public String getDescription() {
        return "Jupiter Marketi";
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
        e.getMessage().delete().queue();
        String[] args = messageWithNoCommand.trim().split(" ");
        if(args.length>=2){
            String bought = messageWithNoCommand.replace(args[0],"").trim();
            EmbedBuilder newE = new EmbedBuilder().setTitle("<:jp_market:831839912088043561> Market").setDescription("**"+args[0].trim()+"** adlı oyuncu **"+bought+"** aldı!").setColor(new Color(0xFF7E00)).setTimestamp(OffsetDateTime.now());
            switch (args[1].trim()){
                case "JVIP++":
                    newE.setImage("https://media.discordapp.net/attachments/786215058035179540/829629846623485972/20210406_014836_0000.png?width=1200&height=676");
                    break;
                case "JVIP+":
                    newE.setImage("https://media.discordapp.net/attachments/786215058035179540/829629847197843476/20210406_020928_0000.png?width=1200&height=676");
                    break;
                case "JVIP":
                    newE.setImage("https://media.discordapp.net/attachments/786215058035179540/829629847454220288/20210406_020103_0000.png?width=1200&height=676");
                    break;
                case "VIP":
                    newE.setImage("https://media.discordapp.net/attachments/786215058035179540/829629847835508736/20210406_015717_0000.png?width=1200&height=676");
                    break;
            }
            e.getChannel().sendMessage(newE.build()).queue();
        }
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context, String messageWithNoCommand) {

    }
}
