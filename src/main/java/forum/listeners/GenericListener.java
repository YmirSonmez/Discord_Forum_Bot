package forum.listeners;

import forum.database.DataBase;
import forum.database.ForumMember;
import forum.commands.admin.*;
import forum.commands.CommandManager;
import forum.commands.guild.Info;
import forum.commands.guild.JupiterInfo;
import forum.commands.guild.Rank;
import forum.commands.reaction.*;
import forum.commands.thread.*;
import forum.setting.Setting;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class GenericListener extends ListenerAdapter {
    public static JDA jda;
    public void onReady(ReadyEvent e){
        jda=e.getJDA();

        //Member Commands
        CommandManager.addCommand(new JupiterInfo());
        CommandManager.addCommand(new Help());
        CommandManager.addCommand(new Info());
        CommandManager.addCommand(new Rank());
        CommandManager.addCommand(new ChangeTitle());
        CommandManager.addCommand(new DeleteContent());
        CommandManager.addCommand(new DeleteThread());
        CommandManager.addCommand(new ShowThread());
        CommandManager.addCommand(new SendThread());

        //Admin Commands
        CommandManager.addCommand(new AChangeStatus());
        CommandManager.addCommand(new AThreadControl());
        CommandManager.addCommand(new AArchive());
        CommandManager.addCommand(new Atest());
        CommandManager.addCommand(new AThreadCategory());
        CommandManager.addCommand(new ALevel());
        CommandManager.addCommand(new AMarket());
        //Reaction Commands
        CommandManager.addCommand(new RCreateThread());
        CommandManager.addCommand(new ARModeratorCheck());
        CommandManager.addCommand(new ARThreadControl());
        CommandManager.addCommand(new RSignUp());
        CommandManager.addCommand(new RPoint());
        CommandManager.addCommand(new RThreadPreview());

        //Set Status
        e.getJDA().getPresence().setActivity(Activity.playing("İyi forumlar!"));


        //Set memberList
        for(ForumMember member: DataBase.getMemberList()){
            DataBase.memberList.put(member.getUserID(),member);
            DataBase.totalThread += member.getThreadCount();
        }
    }
    public void onGuildMemberRemove(GuildMemberRemoveEvent e){
        if(DataBase.checkMember(e.getUser().getId())){
            Setting.CONFIRM_CHANNEL().sendMessage(new EmbedBuilder().setTitle("Silindi!").setDescription(DataBase.getMember(e.getUser().getId()).toString()).build()).queue();
            DataBase.delete(e.getUser().getId());
        }
    }



}
