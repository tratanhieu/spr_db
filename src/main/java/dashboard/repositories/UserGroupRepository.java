package dashboard.repositories;

import dashboard.entities.user.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    @Query("SELECT ug " +
            "FROM UserGroup ug " +
            "WHERE ug.status != 'DELETED'")
    Page<UserGroup> findWithPageable(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_group_features WHERE user_group_id = :userGroupId", nativeQuery = true)
    void deleteUserGroupFeatures(@Param("userGroupId") Long userGroupId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user set user_group_id = -1 where user_group_id = :userGroupId", nativeQuery = true)
    void removeUserGroupFromUser(@Param("userGroupId") Long userGroupId);
}
