package forum.commands;

import forum.setting.Setting;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;

public enum ReactionCommandType {
    THREAD_CONTROL(true,false,null,true,Setting.CATEGORY(),false),
    THREAD_MAIN(false,true,Setting.MAIN_CHANNEL(),false,null,false),
    THREAD_PREVIEW_PAGE(false,false,null,false,null,true),
    THREAD_CONFIRM(true,true, Setting.CONFIRM_CHANNEL(),false,null,false),
    POINT(false,false,null,true,Setting.CATEGORY(),false);

    private final boolean isManagerRequired;
    private final boolean isSpecialChannelRequired;
    private final TextChannel specialChannel;
    private final boolean isSpecialCategoryRequired;
    private final Category specialCategory;
    private final boolean isThreadPageRequired;

    ReactionCommandType(boolean isManagerRequired, boolean isSpecialChannelRequired, TextChannel specialChannel, boolean isSpecialCategoryRequired, Category specialCategory, boolean isThreadPageRequired) {
        this.isManagerRequired = isManagerRequired;
        this.isSpecialChannelRequired = isSpecialChannelRequired;
        this.specialChannel = specialChannel;
        this.isSpecialCategoryRequired = isSpecialCategoryRequired;
        this.specialCategory = specialCategory;
        this.isThreadPageRequired = isThreadPageRequired;
    }

    public boolean isManagerRequired() {
        return isManagerRequired;
    }

    public boolean isSpecialChannelRequired() {
        return isSpecialChannelRequired;
    }

    public TextChannel getSpecialChannel() {
        return specialChannel;
    }

    public boolean isSpecialCategoryRequired() {
        return isSpecialCategoryRequired;
    }

    public Category getSpecialCategory() {
        return specialCategory;
    }

    public boolean isThreadPageRequired() {
        return isThreadPageRequired;
    }
}
