package forum;

import forum.embed.EmbedCreator;
import forum.modules.image.ForumImage;
import forum.modules.image.ThreadImage;
import forum.setting.Setting;
import forum.utils.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class ForumManager {


    public static void confirmThread(TextChannel confirmChannel, ThreadContext context , String messageID,User admin){
        User user = context.getUser();
        Category category = Setting.CATEGORY();

        String title = context.getTitle().trim().replace(" ","・");
        String finalMessage = ThreadUtils.getFinalMessage(user);
        List<String> messageList = getMessageList(finalMessage);


        category.createTextChannel(title).queue(textChannel -> {
            textChannel.sendFile(new ThreadImage(user,admin,context.getTitle().trim(),context.getCategory()).getFile(),"konu.png").content(user.getAsMention()  ).queue(message -> {
                message.addReaction(Emoji.POSITIVE).queue();
                message.addReaction(Emoji.NEGATIVE).queue();
                message.addReaction(Emoji.LOCK).queue();
                message.pin().queue();
            });
            for(String sendThis:messageList){
                textChannel.sendMessage(sendThis).queue();
            }
            if(!ThreadUtils.threads.get(user).getAttachments().isEmpty()){
                int i=0;
                for (String url : ThreadUtils.threads.get(user).getAttachments()){
                    try {
                        File file = new File(Main.path+"oldfiles/lastThread"+i+".png");
                        ImageIO.write(ForumImage.fromURL(url), "png", file);
                        textChannel.sendFile(file,"lastThread.png").queue();
                    } catch (IOException e) {
                        textChannel.sendMessage("(Geçersiz Resim)").queue();
                    }
                    ++i;
                }
            }
            confirmChannel.deleteMessageById(messageID).queue();
            user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(":white_check_mark:Konunuz onayladı!").queue());
            ThreadUtils.threads.keySet().remove(user);
            ThreadUtils.sentThreads.remove(messageID);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    textChannel.upsertPermissionOverride(Setting.FORUM_MEMBER_ROLE()).setAllow(Permission.VIEW_CHANNEL).queue();
                    textChannel.sendMessage(Setting.FORUM_MEMBER_ROLE().getAsMention()).queue();
                }
            },60000);
        });

    }
    public static void refuseThread(TextChannel channel, String messageID, User user){
        channel.deleteMessageById(messageID).queue();
        user
                .openPrivateChannel()
                .queue(privateChannel -> privateChannel.sendMessage(":x: Konunuz reddedildi!").queue());
        ThreadUtils.threads.keySet().remove(user);
        ThreadUtils.sentThreads.remove(messageID);

    }

    public static void banThread(Guild guild, TextChannel channel, String messageID, User user, Role bannedRole){
        Member member = guild.getMember(user);
        if(!member.getRoles().contains(bannedRole)){
            guild.addRoleToMember(member,bannedRole).queue();
            channel.deleteMessageById(messageID).queue();
            user
                    .openPrivateChannel()
                    .queue(privateChannel -> privateChannel.sendMessage(":x: Forumdan banlandınız!").queue());

        }
        ThreadUtils.threads.keySet().remove(user);
        ThreadUtils.sentThreads.remove(messageID);
    }

    private static List<String> getMessageList(String finalMessage){
        List<String> messageList = new ArrayList<>();
        if(finalMessage.length() > 2000){
            String check;
            List<String> punctuation = Arrays.asList(" ", ".", "?", ",", "!", ":", ";");
            for(int a=2000;a<finalMessage.length();){
                for(int i =2000;;i--){
                    check= String.valueOf(finalMessage.charAt(i));
                    if(punctuation.contains(check)){
                        messageList.add(finalMessage.substring(0,i));
                        finalMessage = finalMessage.substring(i);
                    }else if(i==500){
                        messageList.add(finalMessage.substring(0,1900));
                        finalMessage = finalMessage.substring(1900);
                    }
                    if(finalMessage.length()<1900){
                        messageList.add(finalMessage);
                        break;
                    }
                }
            }
        }else{
            messageList.add(finalMessage);
        }
        return messageList;
    }


    public static void create(User user,String forumCategory){
        user.openPrivateChannel().queue(privateChannel ->{
            if (ThreadUtils.checkContext(user)) {
                if (ThreadUtils.checkWrite(user)) {
                    privateChannel.sendMessage(":warning: Zaten bir konu yazıyorsunuz!`").queue();

                    return;
                }
                privateChannel.sendMessage(":warning: Önceki konunun onaylanmasını beklemelisin!").queue();
                return;
            }
            ThreadContext context = new ThreadContext(user, "Konu", new ArrayList<>(), new ArrayList<>(), true, forumCategory);
            ThreadUtils.addForum(user, context);
            EmbedBuilder send = EmbedCreator.createForumEmbed(user);
            send.addField("Hatırlatma!",Setting.PREFIX()+"Yardım menüsünü okumayı unutmayın!\n"+
                    "Başlık değiştirmek için: "+Setting.PREFIX()+"başlık <yeni-başlık>",false);
            privateChannel.sendMessage(send.build()).queue(message -> {
            });
        });
    }


}
