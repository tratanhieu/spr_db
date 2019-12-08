package dashboard.repositories;

import dashboard.entities.user.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    @Query("SELECT ug " +
            "FROM UserGroup ug " +
            "WHERE ug.status != 'DELETED'")
    Page<UserGroup> findWithPageable(Pageable pageable);

}
