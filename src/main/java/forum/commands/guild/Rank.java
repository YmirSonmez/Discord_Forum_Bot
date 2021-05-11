package forum.commands.guild;

import forum.database.DataBase;
import forum.database.ForumMember;
import forum.commands.CommandType;
import forum.commands.ICommand;
import forum.modules.ranking.RankManager;
import forum.setting.Setting;
import forum.utils.DateUtil;
import forum.utils.ThreadContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class Rank implements ICommand {
    @Override
    public String getName() {
        return "Sıralama";
    }

    @Override
    public String getDescription() {
        return "Sıralamayı görüntülemek için kullanılır.";
    }

    @Override
    public String getUsage() {
        return (Setting.PREFIX()+getName());
    }

    @Override
    public CommandType type() {
        return CommandType.FORUM_COMMAND;
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String messageWithNoCommand) {
        HashMap<Integer, ForumMember> rankList = RankManager.getRanking();
        String[] args = messageWithNoCommand.split(" ");
        int number =1;
        StringBuilder list = new StringBuilder();
        try{
            number= Integer.parseInt(args[0]);
        }catch (NumberFormatException exception){
            System.out.println("sayfa 1");
        }


        for(int i =(10*number)-9;i<=(10*number);i++){
            if(i<=rankList.size()){
                if(rankList.get(i)!=null){
                    list.append(i).append(" | ").append(rankList.get(i).toString()).append("\n");
                }
            }
        }
        int authors = RankManager.getMembersRank(DataBase.getMember(e.getAuthor().getId()));
        list.append("\n**Senin Sıran:** `#").append(authors).append("`\n");
        EmbedBuilder rank=new EmbedBuilder()
                .setTitle("<:jp_sralama:831827631095414785>"+" Sıralama ")
                .setDescription("**Son Güncellenme Zamanı: **"+ DateUtil.SDF.format(RankManager.last)+"\n"+list.toString())
                .setFooter(e.getAuthor().getName()+" tarafından istendi! NOT: Üye bilgileri değişmediği sürece sıralama yenilenmez!",e.getAuthor().getAvatarUrl());
        e.getChannel().sendMessage(rank.build()).queue();
    }

    @Override
    public void onCommand(MessageReceivedEvent e, ThreadContext context, String messageWithNoCommand) {

    }
}
