package forum.commands;


import forum.utils.ThreadContext;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {
    String getName();

    String getDescription();

    String getUsage();

    CommandType type();

    void onCommand(MessageReceivedEvent e,String messageWithNoCommand);

    void onCommand(MessageReceivedEvent e, ThreadContext context,String messageWithNoCommand);

}
