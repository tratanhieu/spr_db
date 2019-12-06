package dashboard.services;

import dashboard.entities.post.PostTag;
import dashboard.entities.product.ProductTag;
import dashboard.entities.tag.Tag;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import org.springframework.data.domain.Pageable;


public interface TagService {

    ListEntityResponse<Tag> getAllWithPagination(Pageable pageable);
    void create(Tag tag);
    void createPostTag(PostTag postTag);
    void createProductTag(ProductTag productTag);
    void deletePostTag(Long postTagId);
    void deleteProductTag(Long productTagId);

}
