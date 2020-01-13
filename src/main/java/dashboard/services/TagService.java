package dashboard.services;

import dashboard.entities.post.PostTag;
import dashboard.entities.product.ProductTag;
import dashboard.entities.tag.Tag;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface TagService {
    ListEntityResponse<Tag> getAllWithPagination(Pageable pageable);
    Tag getOne(String slugName);
    void create(Tag tag);
    void createPostTags(Long postId, String[] tags);
    ArrayList<PostTag> getAllPostTag();
    void createProductTag(ProductTag productTag);
    void deletePostTag(Long postTagId);
    void deletePostTagByPostId(Long postId);
    void deleteProductTag(Long productTagId);
}
