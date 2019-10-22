package dashboard.repositories;

import dashboard.entities.Post;
import dashboard.enums.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("SELECT p " +
            "FROM Post p JOIN p.postType pt " +
            "WHERE p.status != 'DELETED'")
    Page<Post> findWithPageable(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Post p SET p.status = :status WHERE p.postId IN (:listId)")
    int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);


}
