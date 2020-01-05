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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.crypto.Data;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Override
    public List getAll() {
        String sqlQueryPost = "SELECT " +
                "p.*, " +
                "pt.name AS postTypeName, " +
                "CONCAT(u.first_name, ' ', u.middle_name, ' ', u.last_name) AS author, " +
                "(SELECT GROUP_CONCAT(t.slug_name, '#', t.name) FROM post_tag pt INNER JOIN tag t ON pt.slug_name = t.slug_name WHERE pt.post_id = p.post_id) AS tags " +
                "FROM post p " +
                "INNER JOIN user u ON p.user_id = u.user_id " +
                "INNER JOIN post_type pt ON p.post_type_id = pt.post_type_id";
        return em.createNativeQuery(sqlQueryPost, "listPostMapping").getResultList();
    }

    @Override
    public Post getOne(Long postId) throws ResourceNotFoundException {
        String sqlQueryPostTag = "SELECT t.* FROM post_tag pt INNER JOIN tag t ON pt.slug_name = t.slug_name WHERE pt.post_id = ?";
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

        // update post
        postRepository.save(post);

        boolean  indexOfTag;
        String tagName;
        String slugName;
        PostTagIdentity postTagIdentity;
        Tag tag;
        List<PostTag> postTagDelete = new ArrayList<>();
        List<String> tags = Arrays.asList(post.getTags());
        List<PostTag> postTags = new ArrayList<>();
        Iterator<Tag> rstTag = postTagRepository.getAllByPostId(post.getPostId()).iterator();
        int i;
        int length = tags.size();

        //filter new and old tag
        while (rstTag.hasNext()) {
            tagName = rstTag.next().getName();
            indexOfTag = tags.contains(tagName);
            if (indexOfTag ) {
                rstTag.remove();
                tags.remove(tags.indexOf(tagName));
            }
        }

        // set value for list postTags
        for(i = 0; i < length; i++){

            //create tag
            slugName = DataUtils.makeSlug(tags.get(i));
            tag = new Tag(slugName,tags.get(i));
            tagRepository.save(tag);

            //create postTag
            postTagIdentity = new PostTagIdentity(post, tag);
            PostTag postTag = new PostTag();
            postTag.setPostTagIdentity(postTagIdentity);

            //set value for list postTags
            postTags.add(postTag);
        }

        // Insert new postTag
        postTagRepository.saveAll(postTags);

        while (rstTag.hasNext()){

            postTagIdentity = new PostTagIdentity(post, rstTag.next());
            PostTag postT  = new PostTag();
            postT.setPostTagIdentity(postTagIdentity);

            postTagDelete.add(postT);

        }

        postTagRepository.deleteAll(postTagDelete);



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
