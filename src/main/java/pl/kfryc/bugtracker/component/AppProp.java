package pl.kfryc.bugtracker.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProp {

    @Value("${default.profile.pic}")
    private String defaultProfilePic;

    public String getDefaultProfilePic() {
        return defaultProfilePic;
    }

    public void setDefaultProfilePic(String defaultProfilePic) {
        this.defaultProfilePic = defaultProfilePic;
    }
}
