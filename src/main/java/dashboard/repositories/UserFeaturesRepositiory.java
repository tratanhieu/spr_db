package dashboard.repositories;

import dashboard.entities.user.UserFeatures;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeaturesRepositiory extends CrudRepository<UserFeatures, String> {
}
