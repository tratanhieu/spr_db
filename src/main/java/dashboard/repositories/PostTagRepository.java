package dashboard.repositories;

import dashboard.entities.post.PostTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends CrudRepository<PostTag, Long> {
}
