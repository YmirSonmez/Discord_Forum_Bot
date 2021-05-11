package forum.modules.debug;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.concurrent.TimeUnit;

public class Error {
    private final MessageChannel channel;
    private final ErrorCodes errorCode;
    private final EmbedBuilder errorEmbed;

    public Error(MessageChannel channel, ErrorCodes errorCode) {
        this.channel = channel;
        this.errorCode = errorCode;
        this.errorEmbed = new EmbedBuilder().setDescription(this.errorCode.getTitle());
    }


    public void send(){channel.sendMessage(this.errorEmbed.build()).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));}
    public Error defaultEmbed(){errorEmbed
            .setFooter("Bu hata: "+this.errorCode.getDefaultDescription());
        return this;
    }
    public Error set(String description){
        this.errorEmbed.setFooter("Bu hata: "+description);
        return this;
    }


}
