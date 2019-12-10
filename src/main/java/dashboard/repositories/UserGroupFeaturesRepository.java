package dashboard.repositories;

import dashboard.entities.embedded.UserGroupFeaturesIdentity;
import dashboard.entities.user.UserGroupFeatures;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupFeaturesRepository extends CrudRepository<UserGroupFeatures, Integer> {
}
