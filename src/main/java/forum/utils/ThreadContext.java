package forum.utils;

import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class ThreadContext {
    private final User user;
    private final List<String> messages;
    private boolean write;
    private String title;
    private final List<String> attachment;
    private String threadCategory;


    public ThreadContext(User user, String title, List<String> messages, List<String> attachments , boolean write, String threadCategory) {
        this.user = user;
        this.messages = messages;
        this.write = write;
        this.title = title;
        this.attachment = attachments;
        this.threadCategory = threadCategory;
    }

    public User getUser(){return this.user;}
    public List<String> getMessages(){return this.messages;}
    public boolean getWrite(){return this.write;}
    public void doneWrite(){this.write =false;}
    public List<String> getAttachments(){return this.attachment;}
    public void clearMessages(){this.messages.clear();}
    public void setTitle(String title){this.title=title;}
    public String getTitle(){return this.title;}
    public String getCategory(){return this.threadCategory;}
    public void changeCategory(String category){this.threadCategory=category;}


}
