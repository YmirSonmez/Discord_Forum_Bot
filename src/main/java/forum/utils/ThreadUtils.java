package forum.utils;
import net.dv8tion.jda.api.entities.*;

import java.util.HashMap;



public class ThreadUtils {
    public static final HashMap<User, ThreadContext> threads = new HashMap<>();
    public static final HashMap<String,User> sentThreads = new HashMap<>();


    public static void addForum(User user, ThreadContext context){
        threads.put(user, context);

    }

    public static void addWait(String messageid,User user){
        threads.get(user).doneWrite();
        sentThreads.put(messageid,user);

    }

    public static String getFinalMessage(User user){
        return String.join(" ", ThreadUtils.threads.get(user).getMessages());

    }

    public static void changeTitle(String title,User user){threads.get(user).setTitle(title);}

    public static void changeCategory(String category,User user){ThreadUtils.threads.get(user).changeCategory(category);}


    public static boolean checkContext(User user){
        return threads.containsKey(user);
    }



    public static boolean checkWrite(User user){return threads.get(user).getWrite();}

    public static void addMessage(User user,PrivateChannel channel,Message message){
        if(ThreadUtils.threads.get(user).getWrite()){
            if(!message.getAttachments().isEmpty()){
                for(Message.Attachment attachment: message.getAttachments()){
                    if(attachment.isImage()){
                        ThreadUtils.threads.get(user).getAttachments().add(attachment.getUrl());
                        return;
                    }
                    channel
                            .sendMessage(":warning: Yalnızca resim ekleyebilirsiniz!")
                            .queue();
                    return;
                }
            }

            String stringMessage = message.getContentDisplay();

            if(stringMessage.length() > 1000){
                channel
                        .sendMessage(":warning: Metnin bir parçası 1000 karakterden fazla olamaz! Metninizin uzunluğu: " + stringMessage.length())
                        .queue();
                return;
            }
            ThreadUtils.threads.get(user).getMessages().add(stringMessage);

        }
    }
}
