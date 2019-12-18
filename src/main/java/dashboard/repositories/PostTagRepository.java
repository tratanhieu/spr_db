package dashboard.repositories;

import dashboard.entities.embedded.PostTagIdentity;
import dashboard.entities.post.PostTag;
import dashboard.entities.tag.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface PostTagRepository extends CrudRepository<PostTag, PostTagIdentity> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post_tag WHERE post_id = :postId", nativeQuery = true)
    void deleteByPostId(@Param("postId") Long postId);


    @Query("SELECT pt.postTagIdentity.tag FROM PostTag pt WHERE pt.postTagIdentity.post.postId = :postId")
    List<Tag> getAllByPostId(@Param("postId") Long postId);

//    @Transactional
//    @Modifying
//    @Query( value = "DELETE FROM post_tag WHERE slug_name IN #{list}", nativeQuery = true)
//    void deleteALLBySlugName(@Param("listId") List<Tag>listId);
}
