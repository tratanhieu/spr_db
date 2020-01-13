package dashboard.services;

import dashboard.dto.post.FormPost;
import dashboard.entities.post.Post;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService  {

    List getAll();

    Post getOne(Long postId) throws ResourceNotFoundException;

    List create(FormPost formPost);

    List update(FormPost formPost);

    List delete(Long postId) throws ResourceNotFoundException;

    List updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException;
}
