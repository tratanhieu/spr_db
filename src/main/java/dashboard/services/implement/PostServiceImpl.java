package dashboard.services.implement;

import dashboard.commons.DataUtils;
import dashboard.entities.embedded.PostTagIdentity;
import dashboard.entities.post.Post;
import dashboard.entities.post.PostTag;
import dashboard.entities.tag.Tag;
import dashboard.enums.EntityStatus;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.generics.ListEntityResponse;
import dashboard.repositories.PostRepository;
import dashboard.repositories.PostTagRepository;
import dashboard.repositories.TagRepository;
import dashboard.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Override
    public ListEntityResponse<Post> getAllWithPagination(Pageable pageable,String name, EntityStatus status) {
        Page<Post>  result = postRepository.findWithPageable(pageable, name, status);

        ListEntityResponse<Post> postRespone = new ListEntityResponse<>();

        postRespone.setPage(result.getNumber() +1);
        postRespone.setPageSize(result.getSize());
        postRespone.setTotalPage(result.getTotalPages());
        postRespone.setListData(result.getContent());

        return postRespone;

    }

    @Override
    public Post getOne(Long postId) throws ResourceNotFoundException {

        Post postRespone = postRepository.findById(postId).orElse(null);

        if (postRespone == null) {
            throw new ResourceNotFoundException();
        }

        return postRespone;
    }

    @Override
    public void create(Post post) {

        // create post
        postRepository.save(post);
        // end
        Tag tag;
        PostTagIdentity postTagIdentity;
        List<PostTag> postTags = new ArrayList<>();
        String[] tags = post.getTags();
        String tagSlugName;

        for(String tagName : tags){
            // create tag
            tagSlugName = DataUtils.makeSlug(tagName);
            tag = new Tag(tagSlugName, tagName);
            tagRepository.save(tag);

            // add dât for postTags
            postTagIdentity = new PostTagIdentity(post, tag);
            PostTag postTag = new PostTag();
            postTag.setPostTagIdentity(postTagIdentity);

            postTags.add(postTag);
        }

        // create PostTag
        postTagRepository.saveAll(postTags);

    }

    @Override
    public void update(Post post) {

        postRepository.save(post);
    }

    @Override
    public void delete(Long postId) throws ResourceNotFoundException {

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException();
        }

        post.setDeleteDate(new Date());
        post.setStatus(EntityStatus.DELETED);
        postRepository.save(post);
    }

    @Override
    public int updateStatusWithMultipleId(List<Long> postId, EntityStatus status) throws ResourceNotFoundException {
        int result =  postRepository.updateStatusByListId(postId, status);
        return result;
    }
}
