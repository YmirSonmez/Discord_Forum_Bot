package forum.commands.admin;


import forum.utils.ThreadContext;
import forum.commands.CommandType;
import forum.commands.ICommand;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class Atest implements ICommand {
    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String getDescription() {
        return "Test için kullanılır.";
    }

    @Override
    public String getUsage() {
        return "-null";
    }

    @Override
    public CommandType type() {
        return CommandType.ADMIN_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e,String messageWithNoCommand) {
        e.getJDA().getRestPing().queue(aLong -> e.getChannel().sendMessage(aLong.toString()).queue());

    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand) {

    }


}
