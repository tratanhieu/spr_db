package dashboard.services.implement;

import dashboard.entities.embedded.PostTagIdentity;
import dashboard.entities.post.PostTag;
import dashboard.entities.product.ProductTag;
import dashboard.entities.tag.Tag;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.PostTagRepository;
import dashboard.repositories.ProductTagRepository;
import dashboard.repositories.TagRepository;
import dashboard.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl  implements  TagService{

    @PersistenceContext
    EntityManager em;

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
    public Tag getOne(String slugName) {

        return tagRepository.findById(slugName).orElse(null);
    }

    @Override
    public void create(Tag tag) {

        tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void createPostTags(Long postId, String[] tags) {
        if (postId == null || tags == null) {
            return;
        }
        int batchSize = 100;
        int tagLength = tags.length;
        try {
            PostTag postTag;
            PostTagIdentity postTagIdentity;
            for (int i = 0; i < tagLength; i++) {
                postTag = new PostTag();
                postTagIdentity = new PostTagIdentity(postId, tags[i]);
                postTag.setPostTagIdentity(postTagIdentity);
                em.persist(postTag);
                if(i % batchSize == 0) {
                    em.flush();
                    em.clear();
                }
            }
            em.flush();
            em.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public ArrayList<PostTag> getAllPostTag(){
        return (ArrayList<PostTag>) postTagRepository.findAll();
    }

    @Override
    public void createProductTag(ProductTag productTag) {
        productTagRepository.save(productTag);
    }

    @Override
    public void deletePostTag(Long postTagId) {
        postTagRepository.deleteByPostId(postTagId);
    }

    @Override
    public void deleteProductTag(Long productTagId) {
        productTagRepository.deleteById(productTagId);
    }

    @Override
    public void deletePostTagByPostId(Long postId) {

    }
}
