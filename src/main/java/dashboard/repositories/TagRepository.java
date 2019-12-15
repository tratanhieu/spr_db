package dashboard.repositories;

import dashboard.entities.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {


    @Query("SELECT tag FROM Tag tag")
    Page<Tag> findWithPageable(Pageable pageable);
    

    @Query("SELECT t FROM Tag t WHERE t.slugName = :slugName")
    Tag findBySlugName(String slugName);
}
