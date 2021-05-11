package forum.commands;

import forum.setting.Setting;
import forum.embed.EmbedCreator;
import forum.utils.Emoji;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CommandManager {
    public static final List<ICommand> commands =new ArrayList<>();
    public static final List<IReactionCommand> reactionCommands =new ArrayList<>();
    public static final String getPrefix = Setting.PREFIX();

    public static void addCommand(ICommand command){
        if(!commands.contains(command)){
            commands.add(command);
            return;
        }
        System.out.println("Bu komut zaten eklenmiş!");

    }
    public static void addCommand(IReactionCommand command){
        if(!reactionCommands.contains(command)){
            reactionCommands.add(command);
            return;
        }
        System.out.println("Bu komut zaten eklenmiş!");
    }


    public static void runCommand(MessageReceivedEvent e){
        Guild guild = Setting.GUILD();
        ICommand commandToRun = null;
        String[] args = e.getMessage().getContentRaw().split(" ");
        String checkThis = args[0].replace(getPrefix,"");
        for(ICommand command: commands){
            if(command.getName().equalsIgnoreCase(checkThis)){
                commandToRun = command;
            }
        }
        if (commandToRun != null) {
            CommandType commandType = commandToRun.type();
            Member member = guild.getMember(e.getAuthor());
            Role adminRole = Setting.ADMIN_ROLE();
            if(member==null || member.getRoles().contains(Setting.FORUM_BANNED_ROLE())){
                return;
            }
            if(!(member.hasPermission(Permission.MANAGE_SERVER) || member.getRoles().contains(adminRole))){
                if(!Objects.equals(e.getJDA().getPresence().getActivity(), Activity.playing("Kullanılamaz!"))){
                    if(!member.getRoles().contains(Setting.FORUM_MEMBER_ROLE())){
                        e.getChannel().sendMessage("Forum kullanıcısı değilsiniz!").queue(message -> message.delete().queueAfter(4,TimeUnit.SECONDS));
                        return;
                    }
                    if(commandType.isManagerRequired()){
                        e.getChannel().sendMessage("Yetkiniz bulunmuyor!").queue(message -> message.delete().queueAfter(4,TimeUnit.SECONDS));
                        return;
                    }
                    if(commandType.getChannelType()==null||commandType.getChannelType().equals(e.getChannelType())){
                        if(commandType.isBotChannelRequired()&&!e.getChannel().equals(Setting.BOT_CHANNEL())){
                            e.getChannel()
                                    .sendMessage(new EmbedBuilder().setDescription("**Yalnızca** "+Setting.BOT_CHANNEL().getAsMention()+" **kanalında kullanabilirsin!**").build())
                                    .queue(message -> message.delete().queueAfter(2,TimeUnit.SECONDS));
                            return;
                        }
                    }else{
                        EmbedBuilder error = new EmbedBuilder()
                                .setTitle("Hata")
                                .setDescription("**Bu komudu burada kullanamazsın!**\n"+"Komut Türü: ```"+commandType.getName()+"```");
                        e.getChannel().sendMessage(error.build()).queue(message -> message.delete().queueAfter(4,TimeUnit.SECONDS));
                        return;
                    }
                }
            }
            String messageWithNoCommand = e.getMessage().getContentDisplay().substring(Setting.PREFIX().length()+commandToRun.getName().length()).trim();
            if(commandType.equals(CommandType.THREAD_COMMAND)) {
                if (ThreadUtils.threads.containsKey(e.getAuthor())){
                    commandToRun.onCommand(e,ThreadUtils.threads.get(e.getAuthor()), messageWithNoCommand);
                }else{
                    e.getChannel().sendMessage("Bu komudu kullanmak için önce bir konu açmalısınız!").queue();
                }
            }else{
                commandToRun.onCommand(e,messageWithNoCommand);
            }
        }
    }

    public static void runReactionCommand(MessageReactionAddEvent e){
        IReactionCommand commandToRun = null;
        for(IReactionCommand command: reactionCommands){
            if(command.getName().contains(e.getReactionEmote().getName())){
                commandToRun=command;
            }
        }
        if(commandToRun!=null){
            Member member = Setting.GUILD().getMember(e.getUser());
            if(member.getRoles().contains(Setting.FORUM_BANNED_ROLE())){
                return;
            }
            if(commandToRun.getType().isSpecialCategoryRequired()&&!commandToRun.getType().getSpecialCategory().equals(e.getTextChannel().getParent())){
                return;
            }
            if(commandToRun.getType().isSpecialChannelRequired()&&!commandToRun.getType().getSpecialChannel().equals(e.getTextChannel())){
                return;
            }
            if(commandToRun.getType().isThreadPageRequired()&&!EmbedCreator.checkMultiEmbed(e.getMessageId())){
                return;
            }
            Role adminRole = Setting.ADMIN_ROLE();
            if(!member.getRoles().contains(Setting.FORUM_MEMBER_ROLE())){
                if(!commandToRun.getName().contains(Emoji.MEMBER)) {
                    return;
                }
            }
            if(Objects.equals(e.getJDA().getPresence().getActivity(), Activity.playing("Kullanılamaz!")) &&!member.getRoles().contains(adminRole)){
                return;
            }
            if(commandToRun.getType().isManagerRequired()&&!member.getRoles().contains(adminRole)){
                if(!member.hasPermission(Permission.MANAGE_SERVER)){
                    return;
                }
            }
            commandToRun.onCommand(e);
        }
    }
}
