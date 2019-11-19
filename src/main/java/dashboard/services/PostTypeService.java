package dashboard.services;

import dashboard.entities.post.PostType;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostTypeService {

    ListEntityResponse<PostType> getAllWithPagination(Pageable pageable);

    PostType getOne(Long postTypeId) throws ResourceNotFoundException;

    int create(PostType postType);

    int update(PostType postType);

    int delete(Long postTypeId) throws ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> ListId, EntityStatus status) throws ResourceNotFoundException;
}