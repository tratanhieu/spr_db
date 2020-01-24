package dashboard.repositories;

import dashboard.dto.post.PostTypeDto;
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

import java.util.Date;
import java.util.List;

@Repository
public interface PostTypeRepository extends CrudRepository<PostType, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE PostType pt SET pt.status = :status WHERE pt.postTypeId IN (:listId)")
    int updateStatusByListId(@Param("listId") List<Long> listId, @Param("status") EntityStatus status);

    @Query(value = "SELECT pt.postTypeId, pt.name FROM PostType AS pt")
    List<PostType> findAllActivePostType();
}