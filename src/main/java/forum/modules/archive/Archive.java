package forum.modules.archive;

import forum.Main;
import forum.modules.image.ForumImage;
import forum.setting.Setting;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static forum.utils.BotUtils.setStatus;

public class Archive {
    private final TextChannel archiveThis;
    private final List<Message> messageList;
    private final int messageListCount;
    private final List<String> messageCanSend;
    private List<Message.Attachment> imageList;

    public Archive(TextChannel archiveThis) {
        this.archiveThis = archiveThis;
        this.messageList= archiveThis.getIterableHistory().stream().collect(Collectors.toList());
        this.messageListCount=messageList.size();
        this.messageCanSend=new ArrayList<>();
    }

    public void toArchiveChannel(){
        archiveThis.upsertPermissionOverride(Setting.FORUM_MEMBER_ROLE()).deny(Permission.VIEW_CHANNEL).queue();
        this.imageList=new ArrayList<>();
        TextChannel archiveChannel = Setting.ARCHIVE_CHANNEL();
        setStatus(archiveChannel.getJDA(), OnlineStatus.DO_NOT_DISTURB,"Kullanılamaz!");
        setMessageCanSend(2000,"\n");
        new java.util.Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        setStatus(archiveChannel.getJDA(), OnlineStatus.ONLINE,"İyi forumlar!");
                    }
                },(this.messageCanSend.size()+this.imageList.size())*2500L);

        archiveChannel.sendMessage("**"+archiveThis.getName()+"** arşivleniyor...   ").queue();
        for(Message.Attachment attachment:this.imageList){
            try {
                File file = new File(Main.path+"oldfiles/lastArchive.png");
                ImageIO.write(Objects.requireNonNull(ForumImage.fromURL(attachment.getUrl())), "png", file);
                archiveChannel.sendFile(file, "lastArchive.png").queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(String send:this.messageCanSend){
            archiveChannel.sendMessage(send).queue();
        }
        archiveChannel.sendMessage("**"+archiveThis.getName()+"** Başarıyla arşivlendi!").queue();
    }

    private void setMessageCanSend(int characterLimit,String var){
        String thisMessage="";
        StringBuilder totalMessage = new StringBuilder();
        for(int i=messageListCount-1;i>=0;i--){
            Message message = this.messageList.get(i);
            if(!message.getAttachments().isEmpty()){
                for(Message.Attachment attachment :message.getAttachments()){
                    if(attachment.isImage()){
                        this.imageList.add(attachment);
                        thisMessage=("**"+message.getAuthor().getName()
                                +"#"
                                +message.getAuthor().getDiscriminator()
                                +":**"
                                +message.getContentDisplay()
                                +"(Resim: "
                                +this.imageList.size()
                                +")"
                                +var);
                    }
                }
            }else{
                thisMessage=("**"+message.getAuthor().getName()
                        +"#"
                        +message.getAuthor().getDiscriminator()+":**" +message.getContentDisplay() +var);
            }
            if(thisMessage.length()>characterLimit){
                this.messageCanSend.add(totalMessage.toString());
                totalMessage.setLength(0);
                this.messageCanSend.add(thisMessage.substring(0,characterLimit/2));
                this.messageCanSend.add(thisMessage.substring(characterLimit/2));
                continue;
            }
            if(totalMessage.length()+thisMessage.length()<characterLimit){
                totalMessage.append(thisMessage);
            }else{
                this.messageCanSend.add(totalMessage.toString());
                totalMessage.setLength(0);
                totalMessage.append(thisMessage);
            }
            if(i==0){
                this.messageCanSend.add(totalMessage.toString());
            }
        }



    }

    public void setTimer(Calendar date){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try{
                    archiveThis.sendMessage("**ARŞİVLENİYOR...** "+Setting.ADMIN_ROLE().getAsMention()).queue();
                    toArchiveChannel();
                }catch (ErrorResponseException error){
                    Setting.CONFIRM_CHANNEL().sendMessage("Arşivlenmek istenen kanal silindiği için istek iptal edildi.").queue();
                }
            }
        };
        this.archiveThis.sendMessage(new EmbedBuilder().setTimestamp(date.toInstant().atZone(ZoneId.systemDefault())).setFooter("Konu arşivlenme zamanı ayarlandı").build()).queue();
        timer.schedule(task,date.getTime());
    }

}
