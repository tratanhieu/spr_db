package dashboard.services;

import dashboard.entities.post.Post;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService  {

    List getAll();

    Post getOne(Long postId) throws ResourceNotFoundException;

    void create(Post post);

    void update(Post post);

    void delete(Long postId) throws ResourceNotFoundException;

    int updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException;
}
