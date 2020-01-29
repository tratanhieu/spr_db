package dashboard.repositories;

import dashboard.entities.user.UserGroupFeature;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupFeaturesRepository extends CrudRepository<UserGroupFeature, Integer> {
}
