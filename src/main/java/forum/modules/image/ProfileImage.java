package forum.modules.image;

import net.dv8tion.jda.api.entities.User;
import java.awt.image.BufferedImage;


public class ProfileImage {
    private final User user;

    public ProfileImage(User user) {
        this.user=user;
    }

    public BufferedImage getBufferedImage(){
        BufferedImage profileImg;
        try {
            profileImg = ForumImage.fromURL(getUser().getAvatarUrl() != null ? getUser().getAvatarUrl() :getUser().getDefaultAvatarUrl());
        } catch (Exception ignored) {
            profileImg = null;
        }
        return profileImg;

    }

    private User getUser() {
        return user;
    }
}
