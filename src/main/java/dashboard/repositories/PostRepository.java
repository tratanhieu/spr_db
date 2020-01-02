package dashboard.repositories;

import dashboard.entities.post.Post;
import dashboard.entities.post.PostType;
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

    @Query("SELECT p FROM Post p " +
            "WHERE p.status != 'DELETED'" +
            "AND (:name = NULL OR p.name LIKE %:name%) " +
            "AND (:status = NULL OR p.status = :status)")
    Page<Post> findWithPageable(
            Pageable pageable,
            @Param("name") String name,
            @Param("status") EntityStatus status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Post p SET p.status = :status WHERE p.postId IN (:listId)")
    int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);


}
