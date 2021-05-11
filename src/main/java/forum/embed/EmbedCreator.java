package forum.embed;

import forum.setting.Setting;
import forum.utils.ThreadContext;
import forum.utils.ThreadUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EmbedCreator {
    private static final List<String> punctuation = Arrays.asList(" ", ".", "?", ",", "!", ":", ";");
    private static final HashMap<String, EmbedPages> multiEmbeds = new HashMap<>();

    public static boolean checkMultiEmbed(String messageID){return multiEmbeds.containsKey(messageID);}
    public static EmbedPages getEmbedPages(String messageID){return multiEmbeds.get(messageID);}
    public static void addMultiEmbed(String messageID,EmbedPages pages){ multiEmbeds.put(messageID,pages);}
    public static void removeMultiEmbed(String messageID){multiEmbeds.remove(messageID);}


    public static EmbedBuilder createForumEmbed(User user){
        ThreadContext context = ThreadUtils.threads.get(user);
        String full = String.join(" ", context.getMessages());
        String finalMessage = getMessageList(full,!context.getAttachments().isEmpty()).get(0);
        EmbedBuilder send = new EmbedBuilder();
        int numberOfPages = getNumberOfPages(finalMessage,!context.getAttachments().isEmpty());
        if(finalMessage.length() > 1000){
            String check;
            for(int a=1000;a<finalMessage.length();){
                for(int i =1000;i>=500;i--){
                    if(i==500){
                        send.addField("",finalMessage.substring(0,995),true);
                        finalMessage =finalMessage.substring(995);
                        break;
                    }else {
                        check = String.valueOf(finalMessage.charAt(i));
                        if (punctuation.contains(check)) {
                            send.addField("", finalMessage.substring(0, i), true);
                            finalMessage = finalMessage.substring(i);
                        }
                    }
                    if(finalMessage.length()<1000){
                        send.addField("",finalMessage,true);
                        break;
                    }
                }
            }
        }else{
            send.addField("",finalMessage,false);
        }
        send.setTitle("Sayfa 1")
                .setDescription("**Konu sahibi: **" + user.getAsMention()+"\n**Konu Başlığı: **"+context.getTitle()+"\n**Kategori: **"+context.getCategory()+"\n**Sayfa Sayısı: **"+numberOfPages);
        send.setColor(0xFFBD00);
        send.setAuthor(Setting.GUILD().getName(),Setting.INVITE_LINK(),user.getAvatarUrl());
        send.setFooter("ID: "+user.getId());
        return send;

    }

    public static EmbedBuilder changeEmbed(EmbedPages page,boolean next){
        ThreadContext context = page.getContext();
        String finalMessage = ThreadUtils.getFinalMessage(context.getUser());
        boolean attachmentsBoolean = !context.getAttachments().isEmpty();
        List<String> messageList = getMessageList(finalMessage,attachmentsBoolean);
        int numberOfPages=getNumberOfPages(finalMessage,attachmentsBoolean);
        int currentPageNumber = page.getCurrentPageNumber();
        String newEmbedContent;
        if(numberOfPages<=1){
            return null;
        }

        if(next){
            if(numberOfPages<=currentPageNumber){
                newEmbedContent=messageList.get(0);
                page.setCurrentPageNumber(1);
                currentPageNumber=1;
            }else{
                if(currentPageNumber+1==numberOfPages){
                    newEmbedContent=null;
                }else{
                    newEmbedContent=messageList.get(currentPageNumber);
                }
                page.setCurrentPageNumber(++currentPageNumber);
            }
        }else{
            if(currentPageNumber-1<=0){
                newEmbedContent=messageList.get(0);
                page.setCurrentPageNumber(1);
                currentPageNumber=1;
            }else{
                newEmbedContent=messageList.get(currentPageNumber-2);
                page.setCurrentPageNumber(--currentPageNumber);
            }
        }
        EmbedBuilder newPage = new EmbedBuilder();
        if(newEmbedContent==null){
            List<String> attachments = context.getAttachments();

            if(!context.getAttachments().isEmpty()){
                String urlList = String.join("\n",attachments);
                if(urlList.length()>1024){
                    List<String> urls = new ArrayList<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    String add;
                    for (String url : attachments){
                        add = url+"\n";
                        if(1000 > add.length() + stringBuilder.length()){
                            stringBuilder.append(add);
                        }else{
                            urls.add(stringBuilder.toString());
                            stringBuilder.setLength(0);
                            stringBuilder.append(add);
                        }
                    }
                    for(String string:urls){
                        newPage.addField("Resimler:",string,false);
                    }
                }else {
                    newPage.addField("Resimler:", urlList,false);
                }
            }

        }else if(newEmbedContent.length() > 1000){
            String check;
            for(int a=950;a<newEmbedContent.length();){
                for(int i =950;i>=500;i--){
                    if(i==500){
                        newPage.addField("",newEmbedContent.substring(0,990),true);
                        newEmbedContent =newEmbedContent.substring(990);
                    }else{
                        check= String.valueOf(newEmbedContent.charAt(i));
                        if(punctuation.contains(check)) {
                            newPage.addField("", newEmbedContent.substring(0, i), true);
                            newEmbedContent = newEmbedContent.substring(i);
                        }
                    }
                    if(newEmbedContent.length()<950){
                        newPage.addField("",newEmbedContent,true);
                        break;
                    }
                }
            }
        }else{
            newPage.addField("",newEmbedContent,false);
        }
        newPage.setTitle("Sayfa "+currentPageNumber)
                .setDescription("**Konu sahibi: **" + context.getUser().getAsMention()+"\n**Konu Başlığı: **"+context.getTitle()+"\n**Kategori: **"+context.getCategory()+"\n**Sayfa Sayısı: **"+numberOfPages)
                .setColor(0xFFBD00)
                .setAuthor(Setting.GUILD().getName(),Setting.INVITE_LINK(),context.getUser().getAvatarUrl())
                .setFooter("ID: "+context.getUser().getId());

        return newPage;

    }

    public static int getNumberOfPages(String finalMessage,boolean attachments) {
        int numberOfPages = finalMessage.length() / 5000;
        if (finalMessage.length() > 5000){
            if (finalMessage.length() % 5000 != 0) {
                numberOfPages += 1;
            }
        }else {
            numberOfPages = 1;
        }
        if(attachments){
            ++numberOfPages;
        }

        return numberOfPages;
    }

    private static List<String> getMessageList(String finalMessage,boolean att){
        List<String> messageList=new ArrayList<>();
        if(finalMessage.length()>5000){
            int numberOfPages = getNumberOfPages(finalMessage,att);
            for(int i=1;i<=numberOfPages;i++){
                if(finalMessage.length()<5000){
                    messageList.add(finalMessage);
                    break;
                }
                messageList.add(finalMessage.substring(0,5000));
                finalMessage = finalMessage.substring(5000);
            }
        }else{
            messageList.add(finalMessage);
        }
        return messageList;
    }






}
