package forum.commands;

import net.dv8tion.jda.api.entities.ChannelType;


public enum CommandType {
    FREE_COMMAND(null, false, false," "),
    ADMIN_COMMAND(null, false, true,"Yönetici Komudu"),
    THREAD_COMMAND(ChannelType.PRIVATE, false, false,"Konu Komudu"),
    FORUM_COMMAND(ChannelType.TEXT, true, false,"Forum Komudu");



    private final ChannelType channelType;
    private final boolean isBotChannelRequired;
    private final boolean isManagerRequired;
    private final String name;

    CommandType(ChannelType channelType, boolean isBotChannelRequired, boolean isManagerRequired, String name) {
        this.channelType = channelType;
        this.isBotChannelRequired = isBotChannelRequired;
        this.isManagerRequired = isManagerRequired;
        this.name = name;
    }

    public String getName(){return this.name;}
    public boolean isManagerRequired(){return this.isManagerRequired;}
    public ChannelType getChannelType(){return this.channelType;}
    public boolean isBotChannelRequired(){return this.isBotChannelRequired;}

}
