package forum.commands.admin;

import forum.database.DataBase;
import forum.database.ForumMember;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.modules.debug.Error;
import forum.modules.debug.ErrorCodes;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ALevel implements ICommand {
    @Override
    public String getName() {
        return "Seviye";
    }

    @Override
    public String getDescription() {
        return "Seviye sisteminin kontrolünü sağlar.";
    }

    @Override
    public String getUsage() {
        return Setting.PREFIX()+getName()+" <işlem türü> <id> [değer]";
    }

    @Override
    public CommandType type() {
        return CommandType.ADMIN_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String messageWithNoCommand) {
        String[] args = messageWithNoCommand.split(" ");
        if(args.length>1){
            if(DataBase.checkMember(args[1])){
                if(args.length==2){
                    switch (args[0]){
                        case "sil":
                            DataBase.delete(args[1]);
                            break;
                        case "görüntüle":
                            e.getChannel().sendMessage(DataBase.getMember(args[1]).toString()).queue();
                            break;
                    }
                }else{
                    ForumMember member = DataBase.getMember(args[1]);
                    int xp = parseInt(args[2], e.getChannel());
                    if (xp == 0) {
                        return;
                    }
                    switch (args[0]) {
                        case "ayarla":
                            member.setXp(xp).complete();
                            break;
                        case "ekle":
                            member.addXp(xp).complete();
                            break;
                        case "çıkar":
                            member.addXp(-xp).complete();
                            break;
                    }
                    e.getChannel().sendMessage(member.toString()).queue();
                }
            }else{
                new Error(e.getChannel(),ErrorCodes.CHECK_ERROR).set("Girilen ID değeri ile eşleşen bir üye bulunamadı!").send();
            }
        }else{
            new Error(e.getChannel(),ErrorCodes.USAGE_ERROR).set(getUsage()).send();
        }
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context, String messageWithNoCommand) {

    }

    private int parseInt(String parse, MessageChannel channel){
        int parsed;
        try {
            parsed = Integer.parseInt(parse.trim());
        }catch (NumberFormatException exception){
            new Error(channel, ErrorCodes.NUMBER_ERROR).defaultEmbed().send();
            parsed= 0;
        }
        return parsed;
    }
}
