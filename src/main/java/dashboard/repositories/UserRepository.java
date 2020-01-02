package dashboard.repositories;

import dashboard.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT us " +
            "FROM User us " +
            "WHERE us.status = 'ACTIVE'")
    Page<User> findWithPageable(Pageable pageable);
}
