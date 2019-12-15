package dashboard.repositories;

import dashboard.entities.post.PostTag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends CrudRepository<PostTag, Long> {

    @Query(value = "DELETE FROM post_tag WHERE post_id = :postId", nativeQuery = true)
    void deleteByPostId(@Param("postId") Long postId);
}
