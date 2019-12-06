package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.entities.post.Post;
import dashboard.entities.post.PostTag;
import dashboard.entities.product.ProductTag;
import dashboard.entities.tag.Tag;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.PostTagRepository;
import dashboard.repositories.ProductTagRepository;
import dashboard.repositories.TagRepository;
import dashboard.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TagServiceImpl  implements  TagService{

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Autowired
    ProductTagRepository productTagRepository;

    @Override
    public ListEntityResponse<Tag> getAllWithPagination(Pageable pageable) {
        Page<Tag> result = tagRepository.findWithPageable(pageable);

        ListEntityResponse<Tag> tagResponse = new ListEntityResponse<>();

        tagResponse.setPage(result.getNumber()+1);
        tagResponse.setPageSize(result.getSize());
        tagResponse.setTotalPage(result.getTotalPages());
        tagResponse.setListData(result.getContent());

        return tagResponse;
    }

    @Override
    public void create(Tag tag) {

        tagRepository.save(tag);
    }

    @Override
    public void createPostTag(PostTag postTag) {
        postTagRepository.save(postTag);
    }

    @Override
    public void createProductTag(ProductTag productTag) {
        productTagRepository.save(productTag);
    }

    @Override
    public void deletePostTag(Long postTagId) {
        postTagRepository.deleteById(postTagId);
    }

    @Override
    public void deleteProductTag(Long productTagId) {
        productTagRepository.deleteById(productTagId);
    }


}
