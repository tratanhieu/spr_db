package dashboard.services;

import dashboard.dto.post.PostForm;
import dashboard.entities.post.Post;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface PostService  {

    List getAll();

    Post getOne(Long postId) throws ResourceNotFoundException;

    Map getCreate();

    List create(PostForm postForm);

    List update(PostForm postForm);

    List delete(Long postId) throws ResourceNotFoundException;

    List updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException;
}
