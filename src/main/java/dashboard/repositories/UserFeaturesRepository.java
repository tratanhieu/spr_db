package dashboard.repositories;

import dashboard.entities.user.UserFeature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeaturesRepository extends CrudRepository<UserFeature, String> {

    @Query("SELECT uf " +
            "FROM UserFeature uf " +
            "WHERE uf.status != 'DELETED'")
    Page<UserFeature> findWithPageable(Pageable pageable);
}
