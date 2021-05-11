package forum.commands.thread;

import forum.commands.CommandManager;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.setting.Setting;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Clock;
import java.time.OffsetDateTime;


public class Help implements ICommand {
    @Override
    public String getName() {
        return "Yardım";
    }

    @Override
    public String getDescription() {
        return "Konu oluşturma rehberi.";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName());
    }

    @Override
    public CommandType type() {
        return CommandType.FREE_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand) {
        Guild guild = Setting.GUILD();
        Member member = guild.getMember(e.getAuthor());
        Role adminRole = Setting.ADMIN_ROLE();
        EmbedBuilder helpMenu = new EmbedBuilder()
                .setTitle("Yardım Menüsü")
                .setDescription("Herhangi bir sorunun olduğunda bize ulaşabilirsin!")
                .setAuthor(guild.getName(),Setting.INVITE_LINK(),guild.getIconUrl())
                .setFooter(e.getAuthor().getName()+" tarafından istendi.",e.getAuthor().getAvatarUrl())
                .setTimestamp(OffsetDateTime.now(Clock.systemUTC()))
                .setColor(new Color(0xFF5A5A));
        boolean isAdmin = member.getRoles().contains(adminRole) || member.hasPermission(Permission.MANAGE_SERVER);
        for(ICommand command: CommandManager.commands){
            if(command.type().isManagerRequired() && !isAdmin){
                continue;
            }
            helpMenu.addField(command.getName(),command.getDescription() +"\n" + command.getUsage() +"\n" + command.type().getName(),false);
        }
        e.getChannel()
                .sendMessage(helpMenu.build())
                .queue();
        helpMenu.clear();
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand) {

    }
}
