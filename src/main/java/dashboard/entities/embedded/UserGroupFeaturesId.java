package dashboard.entities.embedded;

import dashboard.entities.user.UserFeatures;
import dashboard.entities.user.UserGroup;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class UserGroupFeaturesId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "features_id")
    private UserFeatures userFeatures;
}
