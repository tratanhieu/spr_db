package dashboard.services;

import dashboard.entities.Post;
import dashboard.entities.ProductTypeGroup;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService  {

    ListEntityResponse<Post> getAllWithPagination(Pageable pageable);

    Post getOne(Long postId) throws ResourceNotFoundException;

    void create(Post post);

    void update(Post post);

    void delete(Long postId) throws ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException;
}
