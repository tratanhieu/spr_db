package dashboard.repositories;

import dashboard.entities.user.UserFeatures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeaturesRepository extends CrudRepository<UserFeatures, String> {

    @Query("SELECT uf " +
            "FROM UserFeatures uf ")
    Page<UserFeatures> findWithPageable(Pageable pageable);
}
